package com.example.goetheb1worte.data

import com.example.goetheb1worte.data.grammar.guide.CefrLevel
import com.example.goetheb1worte.data.grammar.guide.GrammarQuizType
import com.example.goetheb1worte.data.grammar.guide.GrammarReferenceRegistry
import com.example.goetheb1worte.data.grammar.quiz.InteractiveGrammarQuizData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class InteractiveGrammarQuizDataTest {

    @Test
    fun testAllLevelsHaveQuestions() {
        val a1 = InteractiveGrammarQuizData.getQuestionsByLevel(CefrLevel.A1)
        val a2 = InteractiveGrammarQuizData.getQuestionsByLevel(CefrLevel.A2)
        val b1 = InteractiveGrammarQuizData.getQuestionsByLevel(CefrLevel.B1)

        assertTrue("A1 should have questions", a1.isNotEmpty())
        assertTrue("A2 should have questions", a2.isNotEmpty())
        assertTrue("B1 should have questions", b1.isNotEmpty())
        assertEquals(InteractiveGrammarQuizData.allQuestions.size, a1.size + a2.size + b1.size)
    }

    @Test
    fun testQuestionsIntegrity() {
        InteractiveGrammarQuizData.allQuestions.forEach { q ->
            assertFalse("ID must not be blank in ${q.id}", q.id.isBlank())
            assertFalse("Prompt must not be blank in ${q.id}", q.prompt.isBlank())
            assertFalse("Full sentence must not be blank in ${q.id}", q.fullSentenceDe.isBlank())
            assertFalse("Translation must not be blank in ${q.id}", q.translationEn.isBlank())
            assertFalse("German rule explanation must not be blank in ${q.id}", q.ruleExplanationDe.isBlank())
            assertFalse("English rule explanation must not be blank in ${q.id}", q.ruleExplanationEn.isBlank())
            assertTrue("Options must not be empty in ${q.id}", q.options.isNotEmpty())

            when (q.type) {
                GrammarQuizType.SINGLE_CHOICE, GrammarQuizType.CLOZE -> {
                    assertNotNull("Correct choice must be present in ${q.id}", q.correctChoice)
                    assertTrue(
                        "Correct choice '${q.correctChoice}' must be one of the options in ${q.id}",
                        q.options.contains(q.correctChoice)
                    )
                }
                GrammarQuizType.WORD_ORDER -> {
                    assertNotNull("Correct tokens must be present in ${q.id}", q.correctTokens)
                    assertEquals(
                        "Token count must match options count in ${q.id}",
                        q.options.size,
                        q.correctTokens?.size
                    )
                }
            }
        }
    }

    @Test
    fun testGrammarReferenceRegistryCoverage() {
        val testTopics = listOf(
            "topic_a1_01_pronouns",
            "topic_a1_02_present_verbs",
            "topic_a1_03_word_order",
            "topic_a2_01_dativ_case",
            "topic_a2_02_two_way_preps",
            "topic_a2_07_subordinate_clauses",
            "topic_01_cases",
            "topic_05_passive",
            "topic_07_sentence_structure"
        )

        testTopics.forEach { topicId ->
            val rules = GrammarReferenceRegistry.getRulesForTopic(topicId)
            val tips = GrammarReferenceRegistry.getTipsForTopic(topicId)
            val exceptions = GrammarReferenceRegistry.getExceptionsForTopic(topicId)

            assertTrue("Topic $topicId should have rules", rules.isNotEmpty())
            assertTrue("Topic $topicId should have tips", tips.isNotEmpty())
            assertTrue("Topic $topicId should have exceptions", exceptions.isNotEmpty())
        }
    }
}
