package com.example.goetheb1worte.data

import com.example.goetheb1worte.data.repository.UserProgressRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

class UserProgressRepositoryTest {

    private lateinit var tempDir: File
    private lateinit var repository: UserProgressRepositoryImpl

    @Before
    fun setUp() {
        tempDir = File(System.getProperty("java.io.tmpdir"), "progress_test_" + System.currentTimeMillis())
        tempDir.mkdirs()
        repository = UserProgressRepositoryImpl(
            ioDispatcher = Dispatchers.Unconfined,
            fileName = "test_progress.json",
            storageDir = tempDir
        )
    }

    @After
    fun tearDown() {
        tempDir.deleteRecursively()
    }

    @Test
    fun testInitialProgressIsEmpty() = runBlocking {
        val stats = repository.getProgressStats()
        assertEquals(0, stats.totalAnswered)
        assertEquals(0, stats.totalCorrect)
        assertEquals(0, stats.currentStreak)
        assertEquals(0, stats.accuracyPercentage)
    }

    @Test
    fun testRecordCorrectAnswerUpdatesStreakAndStats() = runBlocking {
        repository.recordAttempt("word_1", isCorrect = true)
        repository.recordAttempt("word_2", isCorrect = true)

        val stats = repository.getProgressStats()
        assertEquals(2, stats.totalAnswered)
        assertEquals(2, stats.totalCorrect)
        assertEquals(2, stats.currentStreak)
        assertEquals(2, stats.bestStreak)
        assertEquals(100, stats.accuracyPercentage)
    }

    @Test
    fun testIncorrectAnswerResetsCurrentStreakPreservesBestStreak() = runBlocking {
        repository.recordAttempt("word_1", isCorrect = true)
        repository.recordAttempt("word_2", isCorrect = true)
        repository.recordAttempt("word_3", isCorrect = false)

        val stats = repository.getProgressStats()
        assertEquals(3, stats.totalAnswered)
        assertEquals(2, stats.totalCorrect)
        assertEquals(0, stats.currentStreak)
        assertEquals(2, stats.bestStreak)
        assertEquals(66, stats.accuracyPercentage)
    }

    @Test
    fun testMostFailedWordsOrdering() = runBlocking {
        // word_A failed 3 times
        repository.recordAttempt("word_A", isCorrect = false)
        repository.recordAttempt("word_A", isCorrect = false)
        repository.recordAttempt("word_A", isCorrect = false)

        // word_B failed 1 time
        repository.recordAttempt("word_B", isCorrect = false)
        repository.recordAttempt("word_B", isCorrect = true)

        // word_C never failed
        repository.recordAttempt("word_C", isCorrect = true)
        repository.recordAttempt("word_C", isCorrect = true)

        val failedIds = repository.getMostFailedWordIds(10)
        assertEquals(2, failedIds.size)
        assertEquals("word_A", failedIds[0])
        assertEquals("word_B", failedIds[1])

        val stats = repository.getProgressStats()
        assertEquals(1, stats.masteredWordsCount) // word_C has >= 2 correct, 0 failed
        assertEquals(2, stats.needsReviewCount)   // word_A and word_B
    }

    @Test
    fun testResetProgressClearsData() = runBlocking {
        repository.recordAttempt("word_1", isCorrect = false)
        repository.recordAttempt("word_2", isCorrect = true)

        repository.resetProgress()

        val stats = repository.getProgressStats()
        assertEquals(0, stats.totalAnswered)
        assertEquals(0, stats.totalCorrect)
        val failed = repository.getMostFailedWordIds(10)
        assertTrue(failed.isEmpty())
    }
}
