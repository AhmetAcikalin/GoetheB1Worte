package com.example.goetheb1worte.data.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
data class WordProgress(
    val wordId: String,
    val timesSeen: Int = 0,
    val timesCorrect: Int = 0,
    val timesFailed: Int = 0,
    val lastAttemptTimestamp: Long = 0L
)

@Serializable
data class UserProgress(
    val totalAnswered: Int = 0,
    val totalCorrect: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val words: Map<String, WordProgress> = emptyMap()
)

@Immutable
data class FailedWordItem(
    val word: WordItem,
    val failureCount: Int,
    val timesSeen: Int,
    val successRate: Float
)

@Immutable
data class ProgressStats(
    val totalAnswered: Int,
    val totalCorrect: Int,
    val accuracyPercentage: Int,
    val currentStreak: Int,
    val bestStreak: Int,
    val masteredWordsCount: Int,
    val needsReviewCount: Int
)
