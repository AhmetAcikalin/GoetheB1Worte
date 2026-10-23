package com.example.goetheb1worte.localization

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.util.Locale
import javax.xml.parsers.DocumentBuilderFactory

class LanguageResolutionTest {

    @Test
    fun testExplicitLanguagesResolveCorrectly() {
        assertEquals("en", AppLanguage.ENGLISH.resolve())
        assertEquals("de", AppLanguage.GERMAN.resolve())
    }

    @Test
    fun testSystemLanguageResolution() {
        val originalLocale = Locale.getDefault()
        try {
            Locale.setDefault(Locale.GERMANY)
            assertEquals("de", AppLanguage.SYSTEM.resolve())

            Locale.setDefault(Locale.US)
            assertEquals("en", AppLanguage.SYSTEM.resolve())

            Locale.setDefault(Locale.FRANCE)
            assertEquals("en", AppLanguage.SYSTEM.resolve())
        } finally {
            Locale.setDefault(originalLocale)
        }
    }

    @Test
    fun testStringsXmlFilesIntegrityAndParity() {
        // Find res directory relative to current working dir or project path
        val candidates = listOf(
            File("src/main/res"),
            File("app/src/main/res"),
            File("C:/Users/tkbs3/AndroidStudioProjects/GoetheB1Worte/app/src/main/res")
        )
        val resDir = candidates.firstOrNull { it.exists() }
        assertNotNull("Resource directory should exist", resDir)

        val defaultXml = File(resDir, "values/strings.xml")
        val enXml = File(resDir, "values-en/strings.xml")
        val deXml = File(resDir, "values-de/strings.xml")

        assertTrue("values/strings.xml must exist", defaultXml.exists())
        assertTrue("values-en/strings.xml must exist", enXml.exists())
        assertTrue("values-de/strings.xml must exist", deXml.exists())

        fun parseStrings(file: File): Map<String, String> {
            val dbf = DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            val doc = db.parse(file)
            val nodes = doc.getElementsByTagName("string")
            val map = mutableMapOf<String, String>()
            for (i in 0 until nodes.length) {
                val node = nodes.item(i)
                val name = node.attributes.getNamedItem("name")?.nodeValue
                val text = node.textContent
                if (name != null) {
                    map[name] = text
                }
            }
            return map
        }

        val defaultStrings = parseStrings(defaultXml)
        val enStrings = parseStrings(enXml)
        val deStrings = parseStrings(deXml)

        assertTrue("Default strings should have at least 50 items", defaultStrings.size >= 50)
        assertEquals("Default and EN strings should have identical size", defaultStrings.size, enStrings.size)
        assertEquals("Default and DE strings should have identical size", defaultStrings.size, deStrings.size)

        // Verify every key in default exists in DE
        for (key in defaultStrings.keys) {
            assertTrue("DE strings should contain key '$key'", deStrings.containsKey(key))
            assertTrue("EN strings should contain key '$key'", enStrings.containsKey(key))
        }

        // Test format string capability on actual XML contents
        val deQuestionOf = deStrings["question_of"]
        assertNotNull(deQuestionOf)
        // Note: XML string might have escaped single quotes or literal tokens
        val formattedDe = String.format(deQuestionOf!!.replace("\\'", "'"), 3, 20)
        assertEquals("Frage 3 von 20", formattedDe)

        val enQuestionOf = enStrings["question_of"]
        assertNotNull(enQuestionOf)
        val formattedEn = String.format(enQuestionOf!!.replace("\\'", "'"), 3, 20)
        assertEquals("Question 3 of 20", formattedEn)

        val deScore = deStrings["your_score_format"]
        assertNotNull(deScore)
        val formattedScore = String.format(deScore!!, 8, 10, 80)
        assertEquals("Ergebnis: 8 von 10 richtig (80%)", formattedScore)
    }
}
