package com.example.goetheb1worte.data.grammar.guide

import androidx.compose.runtime.Immutable

/**
 * Irregular German verb with primary principal parts for A1-B1.
 * Sourced directly from "Grammatiktraining Deutsch für A1-B1" (Milena Angioni, Ines Hälbig).
 */
@Immutable
data class IrregularVerb(
    val infinitive: String,
    val presentThirdPerson: String,
    val preteriteThirdPerson: String,
    val perfectThirdPerson: String,
    val auxiliary: String, // "hat" or "ist"
    val meaningEn: String = "",
    val exampleSentence: String = ""
)

/**
 * German verb requiring a preposition and specific case (Akkusativ or Dativ).
 * Sourced directly from "Grammatiktraining Deutsch für A1-B1".
 */
@Immutable
data class VerbWithPreposition(
    val verb: String,
    val preposition: String,
    val requiredCase: String, // "Akkusativ", "Dativ", "Wechsel"
    val exampleSentence: String,
    val meaningEn: String = ""
)

/**
 * German verb that takes a pure direct Akkusativ or Dativ object.
 * Sourced directly from "Grammatiktraining Deutsch für A1-B1".
 */
@Immutable
data class VerbWithCase(
    val verb: String,
    val case: String, // "Dativ" or "Akkusativ"
    val exampleSentence: String,
    val meaningEn: String = ""
)
