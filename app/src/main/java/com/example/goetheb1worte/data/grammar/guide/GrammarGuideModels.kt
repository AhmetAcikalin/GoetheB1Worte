package com.example.goetheb1worte.data.grammar.guide

import androidx.compose.runtime.Immutable
import com.example.goetheb1worte.data.repository.GameType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Serializable
enum class ExamPriority {
    HIGH_FREQUENCY,
    CORE_B1,
    REVIEW_A2,
    BORDERLINE_B2
}

@Serializable
enum class CefrLevel(val code: String, val title: String) {
    A1("A1", "A1 Foundations"),
    A2("A2", "A2 Expansion"),
    B1("B1", "B1 Mastery")
}

@Serializable
enum class GrammarQuizType(val label: String, val icon: String) {
    SINGLE_CHOICE("Multiple Choice", "🎯"),
    CLOZE("Fill in the Blank", "📝"),
    WORD_ORDER("Sentence Order", "🧩")
}

@Immutable
@Serializable
data class GrammarExample(
    val german: String,
    val highlightWord: String,
    val english: String,
    val note: String? = null
)

@Immutable
@Serializable
data class GrammarTable(
    val headers: List<String>,
    val rows: List<List<String>>
)

@Immutable
@Serializable
data class GrammarGuideSubtopic(
    val id: String,
    val title: String,
    val explanation: String,
    val table: GrammarTable? = null,
    val examples: List<GrammarExample>,
    val tip: String? = null,
    val rules: List<String> = emptyList(),
    val exceptions: List<String> = emptyList()
)

@Immutable
@Serializable
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
    val rules: List<String> = emptyList(),
    val tips: List<String> = emptyList(),
    val exceptions: List<String> = emptyList(),
    val subtopics: List<GrammarGuideSubtopic>,
    val examTraps: List<String>,
    val targetGameType: GameType? = null
)

@Immutable
@Serializable
data class InteractiveGrammarQuestion(
    val id: String,
    val level: CefrLevel,
    val topicId: String,
    val topicTitle: String,
    val type: GrammarQuizType,
    val prompt: String,
    val fullSentenceDe: String,
    val sentenceWithBlank: String? = null,
    val translationEn: String,
    val options: List<String>,
    val correctChoice: String? = null,
    val correctTokens: List<String>? = null,
    val ruleExplanationDe: String,
    val ruleExplanationEn: String,
    val tip: String? = null,
    val exceptionNote: String? = null
)
