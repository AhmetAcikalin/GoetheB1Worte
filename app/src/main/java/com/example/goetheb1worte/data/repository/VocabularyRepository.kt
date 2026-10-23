package com.example.goetheb1worte.data.repository

import androidx.compose.runtime.Immutable
import com.example.goetheb1worte.data.model.WordItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class GameType {
    VOCAB_QUIZ,
    DER_DIE_DAS,
    SYNONYMS_ANTONYMS,
    SENTENCE_CLOZE,
    WEAK_WORDS,
    PRONUNCIATION_TRAINER,
    KASUS_TRAINER,
    SATZBAU_TRAINER,
    VERB_TENSE_TRAINER
}

@Immutable
data class QuizQuestion(
    val word: WordItem,
    val options: ImmutableList<String>,
    val correctOption: String,
    val gameType: GameType = GameType.VOCAB_QUIZ,
    val prompt: String = "What does this word mean in English?",
    val displayWord: String = word.word,
    val subText: String? = null,
    val explanation: String? = null
)

interface VocabularyRepository {
    suspend fun getAllWords(): List<WordItem>
    suspend fun getWordCount(): Int
    suspend fun getWordsByIds(ids: Set<String>): List<WordItem>
    suspend fun getQuestion(targetIndex: Int? = null): QuizQuestion
    suspend fun getQuizSequence(count: Int = 20): List<QuizQuestion>
    suspend fun getFailedWordsQuizSequence(failedWordIds: List<String>, count: Int = 15): List<QuizQuestion>
    suspend fun getDerDieDasQuizSequence(count: Int = 15): List<QuizQuestion>
    suspend fun getSynonymAntonymQuizSequence(count: Int = 15): List<QuizQuestion>
    suspend fun getSentenceClozeQuizSequence(count: Int = 15): List<QuizQuestion>
    suspend fun getPronunciationQuizSequence(count: Int = 20): List<QuizQuestion>
    suspend fun getKasusQuizSequence(count: Int = 15): List<QuizQuestion>
    suspend fun getSatzbauQuizSequence(count: Int = 15): List<QuizQuestion>
    suspend fun getVerbTensesQuizSequence(count: Int = 15): List<QuizQuestion>
}
