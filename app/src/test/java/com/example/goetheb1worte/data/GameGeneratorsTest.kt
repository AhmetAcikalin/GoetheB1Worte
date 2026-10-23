package com.example.goetheb1worte.data

import com.example.goetheb1worte.data.repository.GameType
import com.example.goetheb1worte.data.repository.VocabularyRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

class GameGeneratorsTest {

    private lateinit var repository: VocabularyRepositoryImpl

    @Before
    fun setUp() {
        val assetFile = File("src/main/assets/goethe_b1_full_pages_016_102.json")
        repository = VocabularyRepositoryImpl(
            ioDispatcher = Dispatchers.Unconfined,
            assetFile = assetFile
        )
    }

    @Test
    fun testDerDieDasQuizSequence() = runBlocking {
        val questions = repository.getDerDieDasQuizSequence(15)
        assertEquals(15, questions.size)

        for (q in questions) {
            assertEquals(GameType.DER_DIE_DAS, q.gameType)
            assertEquals(3, q.options.size)
            assertTrue("Options must contain der, die, das", q.options.containsAll(listOf("der", "die", "das")))
            assertTrue("Correct option must be in {der, die, das}", q.correctOption in listOf("der", "die", "das"))
            // Display word must be bare noun (no leading article)
            assertFalse("Display word should not start with der", q.displayWord.startsWith("der "))
            assertFalse("Display word should not start with die", q.displayWord.startsWith("die "))
            assertFalse("Display word should not start with das", q.displayWord.startsWith("das "))
            assertTrue("Prompt should mention article", q.prompt.contains("article", ignoreCase = true))
        }
    }

    @Test
    fun testSynonymAntonymQuizSequence() = runBlocking {
        val questions = repository.getSynonymAntonymQuizSequence(15)
        assertEquals(15, questions.size)

        for (q in questions) {
            assertEquals(GameType.SYNONYMS_ANTONYMS, q.gameType)
            assertEquals("Must have 3 options", 3, q.options.size)
            assertEquals("Options must be unique", 3, q.options.toSet().size)
            assertTrue("Options must contain the correct answer", q.options.contains(q.correctOption))
            assertTrue(
                "Prompt must ask for synonym or opposite",
                q.prompt.contains("synonym", ignoreCase = true) || q.prompt.contains("opposite", ignoreCase = true) || q.prompt.contains("antonym", ignoreCase = true)
            )
        }
    }

    @Test
    fun testSentenceClozeQuizSequence() = runBlocking {
        val questions = repository.getSentenceClozeQuizSequence(15)
        assertEquals(15, questions.size)

        for (q in questions) {
            assertEquals(GameType.SENTENCE_CLOZE, q.gameType)
            assertEquals("Must have 3 options", 3, q.options.size)
            assertEquals("Options must be unique", 3, q.options.toSet().size)
            assertTrue("Options must contain the correct answer", q.options.contains(q.correctOption))
            assertTrue("Display sentence must contain gap [ _____ ]", q.displayWord.contains("[ _____ ]"))
        }
    }

    @Test
    fun testPronunciationQuizSequence() = runBlocking {
        val questions = repository.getPronunciationQuizSequence(15)
        assertEquals(15, questions.size)

        for (q in questions) {
            assertEquals(GameType.PRONUNCIATION_TRAINER, q.gameType)
            assertTrue("Display word must not be blank", q.displayWord.isNotBlank())
            assertTrue("Correct option should match target word", q.correctOption.isNotBlank())
            assertTrue("Prompt must indicate pronunciation training", q.prompt.contains("pronounce", ignoreCase = true) || q.prompt.contains("pronunciation", ignoreCase = true))
        }
    }

    @Test
    fun testKasusQuizSequence() = runBlocking {
        val questions = repository.getKasusQuizSequence(15)
        assertEquals(15, questions.size)

        for (q in questions) {
            assertEquals(GameType.KASUS_TRAINER, q.gameType)
            assertTrue("Options must contain at least 3 choices", q.options.size >= 3)
            assertTrue("Options must contain the correct answer", q.options.contains(q.correctOption))
            assertTrue("Display sentence must contain blank slot", q.displayWord.contains("_____"))
            assertTrue("Rule explanation must contain lightbulb icon", q.explanation?.contains("💡") == true)
            assertTrue("Subtext translation must be present", !q.subText.isNullOrBlank())
            assertTrue("Full sentence must not be blank", q.word.word.isNotBlank())
        }
    }

    @Test
    fun testSatzbauQuizSequence() = runBlocking {
        val questions = repository.getSatzbauQuizSequence(15)
        assertEquals(15, questions.size)

        for (q in questions) {
            assertEquals(GameType.SATZBAU_TRAINER, q.gameType)
            assertTrue("Options must contain at least 3 choices", q.options.size >= 3)
            assertTrue("Options must contain the correct answer", q.options.contains(q.correctOption))
            assertTrue("Display sentence must contain blank slot", q.displayWord.contains("_____"))
            assertTrue("Rule explanation must contain lightbulb icon", q.explanation?.contains("💡") == true)
            assertTrue("Subtext translation must be present", !q.subText.isNullOrBlank())
        }
    }

    @Test
    fun testVerbTensesQuizSequence() = runBlocking {
        val questions = repository.getVerbTensesQuizSequence(15)
        assertEquals(15, questions.size)

        for (q in questions) {
            assertEquals(GameType.VERB_TENSE_TRAINER, q.gameType)
            assertTrue("Options must contain at least 3 choices", q.options.size >= 3)
            assertTrue("Options must contain the correct answer", q.options.contains(q.correctOption))
            assertTrue("Display sentence must contain blank slot", q.displayWord.contains("_____"))
            assertTrue("Rule explanation must contain lightbulb icon", q.explanation?.contains("💡") == true)
            assertTrue("Subtext translation must be present", !q.subText.isNullOrBlank())
        }
    }

    @Test
    fun testGrammarQuestionCountScaling() = runBlocking {
        // Test requesting counts larger than base bank (e.g. 50)
        val kasus50 = repository.getKasusQuizSequence(50)
        assertEquals(50, kasus50.size)

        val satzbau30 = repository.getSatzbauQuizSequence(30)
        assertEquals(30, satzbau30.size)

        val zeiten25 = repository.getVerbTensesQuizSequence(25)
        assertEquals(25, zeiten25.size)
    }
}
