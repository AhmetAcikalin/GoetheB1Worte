package com.example.goetheb1worte.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

data class SimilarityResult(
    val isMatch: Boolean,
    val similarityScore: Float,
    val normalizedTarget: String,
    val normalizedSpoken: String
)

class FuzzyMatcher(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val threshold: Float = 0.85f
) {

    suspend fun evaluatePronunciation(
        targetWord: String,
        spokenText: String
    ): SimilarityResult = withContext(defaultDispatcher) {
        val normTarget = normalizeGerman(targetWord)
        val normSpoken = normalizeGerman(spokenText)

        if (normTarget.isBlank() || normSpoken.isBlank()) {
            return@withContext SimilarityResult(
                isMatch = false,
                similarityScore = 0f,
                normalizedTarget = normTarget,
                normalizedSpoken = normSpoken
            )
        }

        // Exact match check
        if (normTarget == normSpoken) {
            return@withContext SimilarityResult(
                isMatch = true,
                similarityScore = 1.0f,
                normalizedTarget = normTarget,
                normalizedSpoken = normSpoken
            )
        }

        // Token containment check (e.g. spoken sentence containing target word)
        val spokenTokens = normSpoken.split("\\s+".toRegex())
        val targetTokens = normTarget.split("\\s+".toRegex())
        if (spokenTokens.contains(normTarget) || targetTokens.all { spokenTokens.contains(it) }) {
            return@withContext SimilarityResult(
                isMatch = true,
                similarityScore = 0.95f,
                normalizedTarget = normTarget,
                normalizedSpoken = normSpoken
            )
        }

        // Calculate Levenshtein distance
        val distance = calculateLevenshtein(normTarget, normSpoken)
        val maxLen = max(normTarget.length, normSpoken.length)
        val similarity = if (maxLen > 0) (maxLen - distance).toFloat() / maxLen else 0f

        // Also check if any single spoken word has >= threshold similarity with target
        var bestWordSimilarity = similarity
        for (token in spokenTokens) {
            if (token.length >= 3) {
                val tokenDist = calculateLevenshtein(normTarget, token)
                val tokenMax = max(normTarget.length, token.length)
                val tokenSim = if (tokenMax > 0) (tokenMax - tokenDist).toFloat() / tokenMax else 0f
                if (tokenSim > bestWordSimilarity) {
                    bestWordSimilarity = tokenSim
                }
            }
        }

        val finalScore = max(similarity, bestWordSimilarity)
        val match = finalScore >= threshold

        SimilarityResult(
            isMatch = match,
            similarityScore = finalScore,
            normalizedTarget = normTarget,
            normalizedSpoken = normSpoken
        )
    }

    private fun normalizeGerman(input: String): String {
        var text = input.lowercase(Locale.GERMAN).trim()

        // Remove parenthesized info like (sich), (D, A), etc.
        text = text.replace(Regex("\\([^)]*\\)"), "")

        // Remove German leading articles and reflexive pronouns
        val prefixes = listOf("der ", "die ", "das ", "den ", "dem ", "des ", "sich ")
        for (prefix in prefixes) {
            if (text.startsWith(prefix)) {
                text = text.removePrefix(prefix)
            }
        }

        // Keep only letters, digits, and whitespace
        val cleaned = text.map { ch ->
            if (ch.isLetterOrDigit() || ch.isWhitespace()) ch else ' '
        }.joinToString("")

        // Collapse multiple whitespaces
        return cleaned.replace(Regex("\\s+"), " ").trim()
    }

    private fun calculateLevenshtein(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }

        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j

        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = min(
                    dp[i - 1][j] + 1, // deletion
                    min(
                        dp[i][j - 1] + 1, // insertion
                        dp[i - 1][j - 1] + cost // substitution
                    )
                )
            }
        }

        return dp[s1.length][s2.length]
    }
}
