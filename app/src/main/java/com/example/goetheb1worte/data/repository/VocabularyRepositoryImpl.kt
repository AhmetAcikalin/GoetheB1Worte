package com.example.goetheb1worte.data.repository

import android.content.Context
import com.example.goetheb1worte.data.grammar.GrammarData
import com.example.goetheb1worte.data.grammar.GrammarExercise
import com.example.goetheb1worte.data.model.VocabularyRoot
import com.example.goetheb1worte.data.model.WordItem
import com.example.goetheb1worte.data.model.toWordItem
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStreamReader
import kotlin.random.Random

class VocabularyRepositoryImpl(
    private val context: Context? = null,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val assetFileName: String = "goethe_b1_full_pages_016_102.json",
    private val assetFile: java.io.File? = null
) : VocabularyRepository {

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val mutex = Mutex()
    private var cachedWords: List<WordItem>? = null
    private val wordsByCategory = mutableMapOf<String, MutableList<WordItem>>()

    private suspend fun loadWordsIfNecessary(): List<WordItem> {
        cachedWords?.let { return it }
        return mutex.withLock {
            cachedWords?.let { return it }
            withContext(ioDispatcher) {
                val wordsList = mutableListOf<WordItem>()
                val stream = assetFile?.inputStream() ?: context!!.assets.open(assetFileName)
                stream.use { inputStream ->
                    InputStreamReader(inputStream, Charsets.UTF_8).use { reader ->
                        val jsonString = reader.readText()
                        val root = jsonParser.decodeFromString<VocabularyRoot>(jsonString)
                        val vocab = root.vocabulary

                        var indexCounter = 0
                        fun addAll(categoryName: String, items: List<com.example.goetheb1worte.data.model.VocabularyItem>) {
                            val catList = mutableListOf<WordItem>()
                            items.forEach { item ->
                                val wordItem = item.toWordItem(categoryName, ++indexCounter)
                                wordsList.add(wordItem)
                                catList.add(wordItem)
                            }
                            wordsByCategory[categoryName] = catList
                        }

                        addAll("nouns", vocab.nouns)
                        addAll("verbs", vocab.verbs)
                        addAll("adjectives", vocab.adjectives)
                        addAll("adverbs", vocab.adverbs)
                        addAll("prepositions", vocab.prepositions)
                        addAll("conjunctions", vocab.conjunctions)
                        addAll("modal_verbs", vocab.modalVerbs)
                        addAll("prefixes", vocab.prefixes)
                        addAll("other", vocab.other)
                    }
                }
                cachedWords = wordsList
                wordsList
            }
        }
    }

    override suspend fun getAllWords(): List<WordItem> {
        return loadWordsIfNecessary()
    }

    override suspend fun getWordCount(): Int {
        return loadWordsIfNecessary().size
    }

    override suspend fun getWordsByIds(ids: Set<String>): List<WordItem> {
        val words = loadWordsIfNecessary()
        return words.filter { it.id in ids }
    }

    override suspend fun getQuestion(targetIndex: Int?): QuizQuestion {
        val words = loadWordsIfNecessary()
        require(words.size >= 3) { "Vocabulary must contain at least 3 words to generate quiz options." }

        val targetWord = if (targetIndex != null && targetIndex in words.indices) {
            words[targetIndex]
        } else {
            words[Random.nextInt(words.size)]
        }

        val distractors = sampleDistractors(targetWord, words)
        val options = (distractors + targetWord.translation).shuffled().toImmutableList()

        return QuizQuestion(
            word = targetWord,
            options = options,
            correctOption = targetWord.translation,
            gameType = GameType.VOCAB_QUIZ,
            prompt = "What does this word mean in English?",
            displayWord = targetWord.word,
            subText = targetWord.exampleTranslation
        )
    }

    override suspend fun getQuizSequence(count: Int): List<QuizQuestion> {
        val words = loadWordsIfNecessary()
        val total = count.coerceAtMost(words.size)
        val selectedWords = words.shuffled().take(total)

        return selectedWords.map { targetWord ->
            val distractors = sampleDistractors(targetWord, words)
            val options = (distractors + targetWord.translation).shuffled().toImmutableList()
            QuizQuestion(
                word = targetWord,
                options = options,
                correctOption = targetWord.translation,
                gameType = GameType.VOCAB_QUIZ,
                prompt = "What does this word mean in English?",
                displayWord = targetWord.word,
                subText = targetWord.exampleTranslation
            )
        }
    }

    override suspend fun getFailedWordsQuizSequence(failedWordIds: List<String>, count: Int): List<QuizQuestion> {
        val words = loadWordsIfNecessary()
        val idMap = words.associateBy { it.id }
        val targetWords = failedWordIds.mapNotNull { idMap[it] }.distinctBy { it.id }.take(count)

        if (targetWords.isEmpty()) {
            return getQuizSequence(count)
        }

        return targetWords.shuffled().map { targetWord ->
            val distractors = sampleDistractors(targetWord, words)
            val options = (distractors + targetWord.translation).shuffled().toImmutableList()
            QuizQuestion(
                word = targetWord,
                options = options,
                correctOption = targetWord.translation,
                gameType = GameType.WEAK_WORDS,
                prompt = "Mistake Practice: What does this word mean?",
                displayWord = targetWord.word,
                subText = targetWord.exampleTranslation
            )
        }
    }

    override suspend fun getDerDieDasQuizSequence(count: Int): List<QuizQuestion> {
        val words = loadWordsIfNecessary()
        val nouns = words.filter {
            it.category == "nouns" &&
                    (it.word.startsWith("der ") || it.word.startsWith("die ") || it.word.startsWith("das "))
        }

        val total = count.coerceAtMost(nouns.size)
        val selected = nouns.shuffled().take(total)

        return selected.map { noun ->
            val article = noun.word.substring(0, 3)
            val baseWord = noun.word.substring(4).trim()
            QuizQuestion(
                word = noun,
                options = persistentListOf("der", "die", "das"),
                correctOption = article,
                gameType = GameType.DER_DIE_DAS,
                prompt = "Which article belongs to this noun?",
                displayWord = baseWord,
                subText = noun.translation,
                explanation = "${noun.word} (${noun.translation})"
            )
        }
    }

    override suspend fun getSynonymAntonymQuizSequence(count: Int): List<QuizQuestion> {
        val words = loadWordsIfNecessary()
        val eligible = words.filter { it.synonyms.isNotEmpty() || it.antonyms.isNotEmpty() }
        val total = count.coerceAtMost(eligible.size)
        val selected = eligible.shuffled().take(total)

        return selected.map { target ->
            val testSynonym = when {
                target.synonyms.isNotEmpty() && target.antonyms.isNotEmpty() -> Random.nextBoolean()
                target.synonyms.isNotEmpty() -> true
                else -> false
            }

            val (correctOption, prompt, explanation) = if (testSynonym) {
                val syn = target.synonyms.random()
                Triple(syn, "What is a synonym for “${target.word}”?", "Synonym: ${target.word} ≈ $syn")
            } else {
                val ant = target.antonyms.random()
                Triple(ant, "What is the opposite (antonym) of “${target.word}”?", "Opposite: ${target.word} ⇋ $ant")
            }

            // Sample distractors from same category
            val pool = wordsByCategory[target.category] ?: words
            val distractors = pool
                .filter { it.word != target.word && it.word != correctOption && it.translation != correctOption }
                .map { it.word }
                .distinct()
                .shuffled()
                .take(2)

            val options = (distractors + correctOption).shuffled().toImmutableList()

            QuizQuestion(
                word = target,
                options = options,
                correctOption = correctOption,
                gameType = GameType.SYNONYMS_ANTONYMS,
                prompt = prompt,
                displayWord = target.word,
                subText = target.translation,
                explanation = explanation
            )
        }
    }

    override suspend fun getSentenceClozeQuizSequence(count: Int): List<QuizQuestion> {
        val words = loadWordsIfNecessary()
        val eligibleWithCloze = words.mapNotNull { target ->
            val ex = target.example.trim()
            if (ex.length < 15) return@mapNotNull null

            // Clean word stem
            var clean = target.word
            val prefixes = listOf("der ", "die ", "das ", "(sich) ", "sich ")
            for (p in prefixes) {
                if (clean.startsWith(p)) clean = clean.removePrefix(p)
            }
            clean = clean.split("/")[0].trim()

            val regex = Regex("\\b" + Regex.escape(clean), RegexOption.IGNORE_CASE)
            if (regex.containsMatchIn(ex)) {
                val clozeSentence = regex.replace(ex, "[ _____ ]")
                Triple(target, clean, clozeSentence)
            } else {
                null
            }
        }

        val total = count.coerceAtMost(eligibleWithCloze.size)
        val selected = eligibleWithCloze.shuffled().take(total)

        return selected.map { (target, cleanWord, clozeSentence) ->
            val pool = wordsByCategory[target.category] ?: words
            val distractors = pool
                .filter { it.id != target.id && it.word != target.word }
                .map {
                    var d = it.word
                    for (p in listOf("der ", "die ", "das ", "(sich) ", "sich ")) {
                        if (d.startsWith(p)) d = d.removePrefix(p)
                    }
                    d.split("/")[0].trim()
                }
                .filter { it != cleanWord && it.isNotBlank() }
                .distinct()
                .shuffled()
                .take(2)

            val options = (distractors + cleanWord).shuffled().toImmutableList()

            QuizQuestion(
                word = target,
                options = options,
                correctOption = cleanWord,
                gameType = GameType.SENTENCE_CLOZE,
                prompt = "Fill in the missing word in the sentence:",
                displayWord = clozeSentence,
                subText = target.exampleTranslation,
                explanation = "Complete sentence: „${target.example}“"
            )
        }
    }

    override suspend fun getPronunciationQuizSequence(count: Int): List<QuizQuestion> {
        val words = loadWordsIfNecessary()
        val total = count.coerceAtMost(words.size)
        val selectedWords = words.shuffled().take(total)

        return selectedWords.map { targetWord ->
            QuizQuestion(
                word = targetWord,
                options = persistentListOf(),
                correctOption = targetWord.word,
                gameType = GameType.PRONUNCIATION_TRAINER,
                prompt = "Pronunciation Trainer: Speak the word aloud",
                displayWord = targetWord.word,
                subText = targetWord.translation,
                explanation = targetWord.example
            )
        }
    }

    override suspend fun getKasusQuizSequence(count: Int): List<QuizQuestion> {
        return sampleGrammarSequence(GrammarData.kasusExercises, count)
    }

    override suspend fun getSatzbauQuizSequence(count: Int): List<QuizQuestion> {
        return sampleGrammarSequence(GrammarData.satzbauExercises, count)
    }

    override suspend fun getVerbTensesQuizSequence(count: Int): List<QuizQuestion> {
        return sampleGrammarSequence(GrammarData.zeitenExercises, count)
    }

    private fun sampleGrammarSequence(exercises: List<GrammarExercise>, count: Int): List<QuizQuestion> {
        if (exercises.isEmpty()) return emptyList()
        val selected = if (count <= exercises.size) {
            exercises.shuffled().take(count)
        } else {
            val list = mutableListOf<GrammarExercise>()
            while (list.size < count) {
                list.addAll(exercises.shuffled())
            }
            list.take(count)
        }
        return selected.map { exercise ->
            val dummyWord = WordItem(
                id = exercise.id,
                word = exercise.fullSentence,
                translation = exercise.translation,
                example = exercise.fullSentence,
                exampleTranslation = exercise.translation,
                category = exercise.topicBadge
            )
            QuizQuestion(
                word = dummyWord,
                options = exercise.options.toImmutableList(),
                correctOption = exercise.correctOption,
                gameType = exercise.gameType,
                prompt = exercise.prompt,
                displayWord = exercise.sentenceWithBlank,
                subText = exercise.translation,
                explanation = "${exercise.ruleExplanationDe}\n\n${exercise.ruleExplanationEn}"
            )
        }
    }

    private fun sampleDistractors(targetWord: WordItem, allWords: List<WordItem>): List<String> {
        val targetTranslation = targetWord.translation.trim()

        val categoryPool = wordsByCategory[targetWord.category]
            ?.filter { it.translation.trim().isNotBlank() && it.translation.trim() != targetTranslation }
            ?.map { it.translation.trim() }
            ?.distinct()
            ?: emptyList()

        val chosenDistractors = mutableSetOf<String>()

        if (categoryPool.size >= 2) {
            val shuffledCat = categoryPool.shuffled()
            for (distractor in shuffledCat) {
                chosenDistractors.add(distractor)
                if (chosenDistractors.size == 2) break
            }
        }

        if (chosenDistractors.size < 2) {
            val generalPool = allWords
                .filter { it.translation.trim().isNotBlank() && it.translation.trim() != targetTranslation && it.translation.trim() !in chosenDistractors }
                .map { it.translation.trim() }
                .distinct()
                .shuffled()

            for (distractor in generalPool) {
                chosenDistractors.add(distractor)
                if (chosenDistractors.size == 2) break
            }
        }

        return chosenDistractors.toList()
    }
}
