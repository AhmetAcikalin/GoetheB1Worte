package com.example.goetheb1worte.data.repository

import android.content.Context
import com.example.goetheb1worte.data.model.ProgressStats
import com.example.goetheb1worte.data.model.UserProgress
import com.example.goetheb1worte.data.model.WordProgress
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.math.max

interface UserProgressRepository {
    val progressFlow: StateFlow<UserProgress>
    suspend fun recordAttempt(wordId: String, isCorrect: Boolean)
    suspend fun getMostFailedWordIds(limit: Int = 30): List<String>
    suspend fun getProgressStats(): ProgressStats
    suspend fun resetProgress()
}

class UserProgressRepositoryImpl(
    private val context: Context? = null,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    fileName: String = "user_progress.json",
    storageDir: File? = null
) : UserProgressRepository {

    private val file = File(storageDir ?: context?.filesDir ?: File("."), fileName)
    private val mutex = Mutex()
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val _progressFlow = MutableStateFlow(loadInitialProgress())
    override val progressFlow: StateFlow<UserProgress> = _progressFlow.asStateFlow()

    private fun loadInitialProgress(): UserProgress {
        return try {
            if (file.exists()) {
                val text = file.readText(Charsets.UTF_8)
                json.decodeFromString<UserProgress>(text)
            } else {
                UserProgress()
            }
        } catch (e: Exception) {
            UserProgress()
        }
    }

    override suspend fun recordAttempt(wordId: String, isCorrect: Boolean) = withContext(ioDispatcher) {
        mutex.withLock {
            val current = _progressFlow.value
            val existingWordProgress = current.words[wordId] ?: WordProgress(wordId = wordId)

            val updatedWordProgress = existingWordProgress.copy(
                timesSeen = existingWordProgress.timesSeen + 1,
                timesCorrect = existingWordProgress.timesCorrect + (if (isCorrect) 1 else 0),
                timesFailed = existingWordProgress.timesFailed + (if (isCorrect) 0 else 1),
                lastAttemptTimestamp = System.currentTimeMillis()
            )

            val newCurrentStreak = if (isCorrect) current.currentStreak + 1 else 0
            val newBestStreak = max(current.bestStreak, newCurrentStreak)

            val updatedMap = current.words.toMutableMap().apply {
                put(wordId, updatedWordProgress)
            }

            val updatedProgress = current.copy(
                totalAnswered = current.totalAnswered + 1,
                totalCorrect = current.totalCorrect + (if (isCorrect) 1 else 0),
                currentStreak = newCurrentStreak,
                bestStreak = newBestStreak,
                words = updatedMap
            )

            try {
                file.writeText(json.encodeToString(UserProgress.serializer(), updatedProgress), Charsets.UTF_8)
            } catch (_: Exception) {
            }

            _progressFlow.value = updatedProgress
        }
    }

    override suspend fun getMostFailedWordIds(limit: Int): List<String> = withContext(ioDispatcher) {
        val current = _progressFlow.value
        current.words.values
            .filter { it.timesFailed > 0 }
            .sortedWith(
                compareByDescending<WordProgress> { it.timesFailed }
                    .thenByDescending { it.timesFailed.toFloat() / it.timesSeen.toFloat() }
            )
            .take(limit)
            .map { it.wordId }
    }

    override suspend fun getProgressStats(): ProgressStats = withContext(ioDispatcher) {
        val current = _progressFlow.value
        val total = current.totalAnswered
        val correct = current.totalCorrect
        val accuracy = if (total > 0) ((correct.toFloat() / total.toFloat()) * 100).toInt() else 0

        val mastered = current.words.values.count { it.timesCorrect >= 2 && it.timesFailed == 0 }
        val needsReview = current.words.values.count { it.timesFailed > 0 }

        ProgressStats(
            totalAnswered = total,
            totalCorrect = correct,
            accuracyPercentage = accuracy,
            currentStreak = current.currentStreak,
            bestStreak = current.bestStreak,
            masteredWordsCount = mastered,
            needsReviewCount = needsReview
        )
    }

    override suspend fun resetProgress() = withContext(ioDispatcher) {
        mutex.withLock {
            val empty = UserProgress()
            try {
                if (file.exists()) {
                    file.delete()
                }
            } catch (_: Exception) {
            }
            _progressFlow.value = empty
        }
    }
}
