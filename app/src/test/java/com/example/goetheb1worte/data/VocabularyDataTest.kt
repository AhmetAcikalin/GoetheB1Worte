package com.example.goetheb1worte.data

import com.example.goetheb1worte.data.model.VocabularyRoot
import com.example.goetheb1worte.data.model.toWordItem
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class VocabularyDataTest {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun testMasterVocabularyJsonLoadsAndParsesCorrectly() {
        val assetFile = File("src/main/assets/goethe_b1_full_pages_016_102.json")
        assertTrue("Asset file must exist at " + assetFile.absolutePath, assetFile.exists())

        val root = json.decodeFromString<VocabularyRoot>(assetFile.readText(Charsets.UTF_8))
        assertNotNull(root.vocabulary)

        val vocab = root.vocabulary
        assertTrue("Nouns should be > 1300", vocab.nouns.size > 1300)
        assertTrue("Verbs should be > 600", vocab.verbs.size > 600)
        assertTrue("Adjectives should be > 400", vocab.adjectives.size > 400)

        val total = vocab.nouns.size +
                vocab.verbs.size +
                vocab.adjectives.size +
                vocab.adverbs.size +
                vocab.prepositions.size +
                vocab.conjunctions.size +
                vocab.modalVerbs.size +
                vocab.prefixes.size +
                vocab.other.size

        assertEquals("Total vocabulary words count must match 2865", 2865, total)
    }

    @Test
    fun testWordItemMappingAndOptionUniqueness() {
        val assetFile = File("src/main/assets/goethe_b1_full_pages_016_102.json")
        val root = json.decodeFromString<VocabularyRoot>(assetFile.readText(Charsets.UTF_8))

        val nouns = root.vocabulary.nouns.mapIndexed { idx, item -> item.toWordItem("nouns", idx + 1) }
        assertTrue(nouns.isNotEmpty())

        val firstNoun = nouns.first()
        assertTrue("First noun German word cannot be blank", firstNoun.word.isNotBlank())
        assertTrue("First noun English translation cannot be blank", firstNoun.translation.isNotBlank())

        // Test distractor sampling simulation
        val target = nouns[0]
        val otherNouns = nouns.drop(1).filter { it.translation != target.translation }
        val distractors = otherNouns.shuffled().take(2).map { it.translation }
        val options = (distractors + target.translation).shuffled()

        assertEquals("Should have exactly 3 options", 3, options.size)
        assertEquals("Options should be distinct", 3, options.toSet().size)
        assertTrue("Options must contain the correct answer", options.contains(target.translation))
    }
}
