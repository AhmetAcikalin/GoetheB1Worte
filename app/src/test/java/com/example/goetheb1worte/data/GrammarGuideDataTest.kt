package com.example.goetheb1worte.data

import com.example.goetheb1worte.data.grammar.guide.CefrLevel
import com.example.goetheb1worte.data.grammar.guide.GrammarGuideData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GrammarGuideDataTest {

    @Test
    fun testAllCurriculumTopicsExist() {
        val topics = GrammarGuideData.topics
        assertEquals("Must have 39 total curriculum topics (13 A1 + 15 A2 + 11 B1)", 39, topics.size)

        val a1Topics = GrammarGuideData.getTopicsByLevel(CefrLevel.A1)
        val a2Topics = GrammarGuideData.getTopicsByLevel(CefrLevel.A2)
        val b1Topics = GrammarGuideData.getTopicsByLevel(CefrLevel.B1)

        assertEquals("Must have 13 A1 topics", 13, a1Topics.size)
        assertEquals("Must have 15 A2 topics", 15, a2Topics.size)
        assertEquals("Must have 11 B1 topics", 11, b1Topics.size)

        // Verify B1 IDs
        val expectedB1Ids = setOf(
            "topic_01_cases",
            "topic_02_rektion",
            "topic_03_adjectives",
            "topic_04_tenses",
            "topic_05_passive",
            "topic_06_konjunktiv2",
            "topic_07_sentence_structure",
            "topic_08_infinitives",
            "topic_09_relatives",
            "topic_10_pronouns",
            "topic_11_exam_strategy"
        )
        val actualB1Ids = b1Topics.map { it.id }.toSet()
        assertEquals("All expected B1 topic IDs must match", expectedB1Ids, actualB1Ids)

        // Verify key A1 IDs
        assertTrue(a1Topics.any { it.id == "topic_a1_01_pronouns" })
        assertTrue(a1Topics.any { it.id == "topic_a1_05_negation" })
        assertTrue(a1Topics.any { it.id == "topic_a1_10_separable" })

        // Verify key A2 IDs
        assertTrue(a2Topics.any { it.id == "topic_a2_01_dativ" })
        assertTrue(a2Topics.any { it.id == "topic_a2_02_wechselpraep" })
        assertTrue(a2Topics.any { it.id == "topic_a2_05_praeteritum" })
    }

    @Test
    fun testTopicsIntegrityAndFields() {
        for (topic in GrammarGuideData.topics) {
            assertTrue("Topic ID cannot be blank", topic.id.isNotBlank())
            assertTrue("Topic title cannot be blank for ${topic.id}", topic.title.isNotBlank())
            assertTrue("Topic subtitle cannot be blank for ${topic.id}", topic.subtitle.isNotBlank())
            assertTrue("Topic category cannot be blank for ${topic.id}", topic.category.isNotBlank())
            assertTrue("Topic icon emoji cannot be blank for ${topic.id}", topic.iconEmoji.isNotBlank())
            assertTrue("Topic summary cannot be blank for ${topic.id}", topic.summary.isNotBlank())
            assertTrue("Topic must have subtopics: ${topic.id}", topic.subtopics.isNotEmpty())

            if (topic.id != "topic_11_exam_strategy") {
                assertTrue("Topic must have exam traps: ${topic.id}", topic.examTraps.isNotEmpty())
            }
        }
    }

    @Test
    fun testSubtopicsAndExamplesIntegrity() {
        for (topic in GrammarGuideData.topics) {
            val minExamples = if (topic.id == "topic_11_exam_strategy") 1 else 2
            for (subtopic in topic.subtopics) {
                assertTrue("Subtopic title cannot be blank in ${topic.id}", subtopic.title.isNotBlank())
                assertTrue("Subtopic explanation cannot be blank in ${topic.id}: ${subtopic.title}", subtopic.explanation.isNotBlank())
                assertTrue(
                    "Subtopic must have at least $minExamples examples in ${topic.id}: ${subtopic.title}",
                    subtopic.examples.size >= minExamples
                )

                for (example in subtopic.examples) {
                    assertTrue("German text cannot be blank in ${topic.id}", example.german.isNotBlank())
                    assertTrue("English translation cannot be blank in ${topic.id}", example.english.isNotBlank())
                    assertTrue("Highlight word cannot be blank in ${topic.id}", example.highlightWord.isNotBlank())
                }
            }
        }
    }

    @Test
    fun testGrammarTablesConsistency() {
        for (topic in GrammarGuideData.topics) {
            for (subtopic in topic.subtopics) {
                val table = subtopic.table ?: continue
                val headerCount = table.headers.size
                assertTrue("Table must have headers in ${topic.id}: ${subtopic.title}", headerCount > 0)
                assertTrue("Table must have rows in ${topic.id}: ${subtopic.title}", table.rows.isNotEmpty())

                for ((rowIndex, row) in table.rows.withIndex()) {
                    assertEquals(
                        "Row $rowIndex in ${topic.id} - ${subtopic.title} must have $headerCount columns to match headers",
                        headerCount,
                        row.size
                    )
                    for (cell in row) {
                        assertTrue("Table cell cannot be blank", cell.isNotBlank())
                    }
                }
            }
        }
    }

    @Test
    fun testSearchFunctionality() {
        // Search blank returns all 39
        val all = GrammarGuideData.search("")
        assertEquals(39, all.size)

        // Search Passiv (B1)
        val passiv = GrammarGuideData.search("Passiv")
        assertTrue(passiv.any { it.id == "topic_05_passive" })

        // Search Genitiv
        val genitiv = GrammarGuideData.search("Genitiv")
        assertTrue(genitiv.any { it.id == "topic_01_cases" })
        assertTrue(genitiv.any { it.id == "topic_a2_11_genitiv_intro" })

        // Search separable verbs (A1)
        val separable = GrammarGuideData.search("aufstehen")
        assertTrue(separable.any { it.id == "topic_a1_10_separable" })

        // Search with level filter
        val a1Results = GrammarGuideData.search("Dativ", level = CefrLevel.A1)
        val a2Results = GrammarGuideData.search("Dativ", level = CefrLevel.A2)
        assertTrue(a2Results.any { it.id == "topic_a2_01_dativ" })

        // Search non-existent
        val nonExistent = GrammarGuideData.search("xyznonexistentterm123")
        assertTrue("Non existent query must return empty list", nonExistent.isEmpty())
    }

    @Test
    fun testGetTopicById() {
        val a1Topic = GrammarGuideData.getTopicById("topic_a1_01_pronouns")
        assertNotNull(a1Topic)
        assertEquals(CefrLevel.A1, a1Topic?.level)

        val a2Topic = GrammarGuideData.getTopicById("topic_a2_01_dativ")
        assertNotNull(a2Topic)
        assertEquals(CefrLevel.A2, a2Topic?.level)

        val b1Topic = GrammarGuideData.getTopicById("topic_01_cases")
        assertNotNull(b1Topic)
        assertEquals(CefrLevel.B1, b1Topic?.level)

        val nonExistent = GrammarGuideData.getTopicById("non_existent")
        assertNull(nonExistent)
    }
}
