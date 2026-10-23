package com.example.goetheb1worte.data.grammar.guide

import com.example.goetheb1worte.data.repository.GameType

enum class ExamPriority {
    HIGH_FREQUENCY,
    CORE_B1,
    REVIEW_A2,
    BORDERLINE_B2
}

enum class CefrLevel(val code: String, val title: String) {
    A1("A1", "A1 Foundations"),
    A2("A2", "A2 Expansion"),
    B1("B1", "B1 Mastery")
}

data class GrammarExample(
    val german: String,
    val highlightWord: String,
    val english: String,
    val note: String? = null
)

data class GrammarTable(
    val headers: List<String>,
    val rows: List<List<String>>
)

data class GrammarGuideSubtopic(
    val id: String,
    val title: String,
    val explanation: String,
    val table: GrammarTable? = null,
    val examples: List<GrammarExample>,
    val tip: String? = null
)

data class GrammarGuideTopic(
    val id: String,
    val number: Int,
    val level: CefrLevel = CefrLevel.B1,
    val title: String,
    val germanTitle: String,
    val subtitle: String,
    val category: String,
    val iconEmoji: String,
    val priority: ExamPriority,
    val summary: String,
    val subtopics: List<GrammarGuideSubtopic>,
    val examTraps: List<String>,
    val targetGameType: GameType? = null
)
