package com.example.goetheb1worte.navigation

import androidx.navigation3.runtime.NavKey
import com.example.goetheb1worte.data.grammar.guide.CefrLevel
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavKey : NavKey {
    @Serializable
    data object Quiz : AppNavKey

    @Serializable
    data class GrammarGuide(
        val initialTopicId: String? = null,
        val initialLevel: CefrLevel? = null
    ) : AppNavKey

    @Serializable
    data class GrammarTopicDetail(
        val topicId: String
    ) : AppNavKey

    @Serializable
    data class InteractiveGrammarQuiz(
        val level: CefrLevel = CefrLevel.A1,
        val topicId: String? = null
    ) : AppNavKey
}
