package com.example.goetheb1worte.data.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VocabularyRoot(
    val level: String? = null,
    val language: String? = null,
    val source: String? = null,
    @SerialName("page_ranges_included")
    val pageRangesIncluded: List<String> = emptyList(),
    val vocabulary: VocabularyCategories = VocabularyCategories()
)

@Serializable
data class VocabularyCategories(
    val nouns: List<VocabularyItem> = emptyList(),
    val verbs: List<VocabularyItem> = emptyList(),
    val adjectives: List<VocabularyItem> = emptyList(),
    val adverbs: List<VocabularyItem> = emptyList(),
    val prepositions: List<VocabularyItem> = emptyList(),
    val conjunctions: List<VocabularyItem> = emptyList(),
    @SerialName("modal_verbs")
    val modalVerbs: List<VocabularyItem> = emptyList(),
    val prefixes: List<VocabularyItem> = emptyList(),
    val other: List<VocabularyItem> = emptyList()
)

@Serializable
data class VocabularyItem(
    val id: String? = null,
    val word: String,
    val translation: String,
    val example: String = "",
    @SerialName("example_translation")
    val exampleTranslation: String = "",
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val page: Int? = null,
    @SerialName("page_range")
    val pageRange: String? = null,
    @SerialName("regional_note")
    val regionalNote: String? = null,
    val gender: String? = null,
    val plural: String? = null,
    val genitive: String? = null,
    @SerialName("present_3rd_person")
    val present3rdPerson: String? = null,
    val imperfect: String? = null,
    val perfect: String? = null,
    val reflexive: Boolean = false,
    @SerialName("reflexive_case")
    val reflexiveCase: String? = null,
    val comparative: String? = null,
    val superlative: String? = null,
    @SerialName("part_of_speech")
    val partOfSpeech: String? = null,
    val case: String? = null
)

@Immutable
data class WordItem(
    val id: String,
    val word: String,
    val translation: String,
    val category: String,
    val example: String,
    val exampleTranslation: String,
    val gender: String? = null,
    val plural: String? = null,
    val genitive: String? = null,
    val present3rdPerson: String? = null,
    val imperfect: String? = null,
    val perfect: String? = null,
    val comparative: String? = null,
    val superlative: String? = null,
    val page: Int? = null,
    val synonyms: ImmutableList<String> = persistentListOf(),
    val antonyms: ImmutableList<String> = persistentListOf(),
    val tags: ImmutableList<String> = persistentListOf()
)

fun VocabularyItem.toWordItem(category: String, fallbackIndex: Int): WordItem {
    return WordItem(
        id = id ?: "word_${category}_$fallbackIndex",
        word = word,
        translation = translation,
        category = category,
        example = example,
        exampleTranslation = exampleTranslation,
        gender = gender,
        plural = plural,
        genitive = genitive,
        present3rdPerson = present3rdPerson,
        imperfect = imperfect,
        perfect = perfect,
        comparative = comparative,
        superlative = superlative,
        page = page,
        synonyms = synonyms.toImmutableList(),
        antonyms = antonyms.toImmutableList(),
        tags = tags.toImmutableList()
    )
}
