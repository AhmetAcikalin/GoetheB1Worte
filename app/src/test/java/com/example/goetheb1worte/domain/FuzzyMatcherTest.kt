package com.example.goetheb1worte.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FuzzyMatcherTest {

    private lateinit var fuzzyMatcher: FuzzyMatcher

    @Before
    fun setUp() {
        fuzzyMatcher = FuzzyMatcher(defaultDispatcher = Dispatchers.Unconfined, threshold = 0.85f)
    }

    @Test
    fun testExactMatch() = runBlocking {
        val result = fuzzyMatcher.evaluatePronunciation("Tisch", "Tisch")
        assertTrue("Exact match should succeed", result.isMatch)
        assertTrue("Similarity score should be >= 0.85", result.similarityScore >= 0.85f)
    }

    @Test
    fun testCaseInsensitivityAndPunctuation() = runBlocking {
        val result = fuzzyMatcher.evaluatePronunciation("Äpfel!", "äpfel")
        assertTrue("Case and punctuation differences should match", result.isMatch)
        assertTrue(result.similarityScore >= 0.85f)
    }

    @Test
    fun testArticleStripping() = runBlocking {
        val derTisch = fuzzyMatcher.evaluatePronunciation("der Tisch", "Tisch")
        assertTrue("Leading 'der' should be stripped", derTisch.isMatch)

        val dieKatze = fuzzyMatcher.evaluatePronunciation("die Katze", "Katze")
        assertTrue("Leading 'die' should be stripped", dieKatze.isMatch)

        val dasBuch = fuzzyMatcher.evaluatePronunciation("das Buch", "Buch")
        assertTrue("Leading 'das' should be stripped", dasBuch.isMatch)
    }

    @Test
    fun testReflexiveParenthesesStripping() = runBlocking {
        val result = fuzzyMatcher.evaluatePronunciation("(sich) freuen", "freuen")
        assertTrue("(sich) prefix in parenthesis should match", result.isMatch)

        val result2 = fuzzyMatcher.evaluatePronunciation("sich freuen", "freuen")
        assertTrue("leading 'sich ' prefix should match", result2.isMatch)
    }

    @Test
    fun testSpokenSentenceContainment() = runBlocking {
        val result = fuzzyMatcher.evaluatePronunciation("Tisch", "Das ist ein schöner Tisch")
        assertTrue("Spoken sentence containing target word should match", result.isMatch)
        assertTrue(result.similarityScore >= 0.85f)
    }

    @Test
    fun testLevenshteinMinorTypos() = runBlocking {
        // 1 typo out of 8 characters = 7/8 = 87.5% similarity >= 85%
        val result = fuzzyMatcher.evaluatePronunciation("Bahnhof", "Bahnof")
        assertTrue("Bahnhof vs Bahnof minor typo should match threshold", result.isMatch)
    }

    @Test
    fun testMismatchedWordsFail() = runBlocking {
        val result = fuzzyMatcher.evaluatePronunciation("Katze", "Hund")
        assertFalse("Completely different words should not match", result.isMatch)
        assertTrue(result.similarityScore < 0.85f)
    }

    @Test
    fun testBlankInputsFail() = runBlocking {
        val result = fuzzyMatcher.evaluatePronunciation("", "Hallo")
        assertFalse("Blank target word should not match", result.isMatch)

        val result2 = fuzzyMatcher.evaluatePronunciation("Hallo", "   ")
        assertFalse("Blank spoken text should not match", result2.isMatch)
    }
}
