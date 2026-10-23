package com.example.goetheb1worte.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.goetheb1worte.audio.NoOpSoundEffectManager
import com.example.goetheb1worte.audio.NoOpUserVoiceRecorder
import com.example.goetheb1worte.audio.SoundEffectManager
import com.example.goetheb1worte.audio.TtsManager
import com.example.goetheb1worte.audio.UserVoiceRecorder
import com.example.goetheb1worte.data.model.FailedWordItem
import com.example.goetheb1worte.data.repository.GameType
import com.example.goetheb1worte.data.repository.QuizQuestion
import com.example.goetheb1worte.data.repository.UserProgressRepository
import com.example.goetheb1worte.data.repository.VocabularyRepository
import com.example.goetheb1worte.domain.FuzzyMatcher
import com.example.goetheb1worte.localization.AppLanguage
import com.example.goetheb1worte.localization.LanguageManager
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: VocabularyRepository,
    private val progressRepository: UserProgressRepository,
    private val ttsManager: TtsManager,
    private val soundEffectManager: SoundEffectManager = NoOpSoundEffectManager(),
    private val languageManager: LanguageManager = LanguageManager(),
    private val fuzzyMatcher: FuzzyMatcher = FuzzyMatcher(),
    private val userVoiceRecorder: UserVoiceRecorder = NoOpUserVoiceRecorder()
) : ViewModel() {

    val currentLanguage: StateFlow<AppLanguage> = languageManager.language

    private val _quizQuestions = mutableListOf<QuizQuestion>()
    private var currentIndex = 0
    private var currentScore = 0
    private var activeGameType: GameType = GameType.VOCAB_QUIZ
    private var activeQuestionCount: QuestionCount = QuestionCount.TWENTY

    private val _internalState = MutableStateFlow<QuizUiState>(QuizUiState.Loading)
    private val _progressSheetState = MutableStateFlow(ProgressSheetState())
    private val _gameHubSheetState = MutableStateFlow(GameHubSheetState(selectedQuestionCount = activeQuestionCount))
    private val _grammarGuideState = MutableStateFlow(GrammarGuideState())

    val progressSheetState: StateFlow<ProgressSheetState> = _progressSheetState.asStateFlow()
    val gameHubSheetState: StateFlow<GameHubSheetState> = _gameHubSheetState.asStateFlow()
    val grammarGuideState: StateFlow<GrammarGuideState> = _grammarGuideState.asStateFlow()

    val uiState: StateFlow<QuizUiState> = combine(
        _internalState,
        ttsManager.currentlySpeaking
    ) { state, speakingTarget ->
        if (state is QuizUiState.Question) {
            state.copy(currentlySpeaking = speakingTarget)
        } else {
            state
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = QuizUiState.Loading
    )

    init {
        loadQuiz()
    }

    fun onEvent(event: QuizUiEvent) {
        when (event) {
            is QuizUiEvent.SelectOption -> handleSelectOption(event.option)
            QuizUiEvent.NextQuestion -> handleNextQuestion()
            QuizUiEvent.PlayWordAudio -> handlePlayWordAudio()
            QuizUiEvent.PlayExampleAudio -> handlePlayExampleAudio()
            QuizUiEvent.PlayUserRecording -> handlePlayUserRecording()
            QuizUiEvent.StopUserRecording -> handleStopUserRecording()
            QuizUiEvent.ToggleTranslationVisibility -> handleToggleTranslation()
            QuizUiEvent.StartListening -> handleStartListening()
            QuizUiEvent.StopListening -> handleStopListening()
            is QuizUiEvent.SpeechResult -> handleSpeechResult(event.text)
            is QuizUiEvent.SpeechError -> handleSpeechError(event.code)
            QuizUiEvent.RestartQuiz -> loadQuiz()
            QuizUiEvent.OpenProgressSheet -> handleOpenProgressSheet()
            QuizUiEvent.CloseProgressSheet -> handleCloseProgressSheet()
            QuizUiEvent.StartWeakWordsPractice -> handleStartWeakWordsPractice()
            QuizUiEvent.ResetProgress -> handleResetProgress()
            QuizUiEvent.OpenGameHub -> _gameHubSheetState.value = GameHubSheetState(isVisible = true, selectedQuestionCount = activeQuestionCount)
            QuizUiEvent.CloseGameHub -> _gameHubSheetState.value = _gameHubSheetState.value.copy(isVisible = false)
            is QuizUiEvent.SelectGameMode -> handleSelectGameMode(event.gameType)
            is QuizUiEvent.SetQuestionCount -> handleSetQuestionCount(event.count)
            QuizUiEvent.FinishEarly -> handleFinishEarly()
            is QuizUiEvent.SetLanguage -> languageManager.setLanguage(event.language)
            QuizUiEvent.RetryPronunciation -> handleRetryPronunciation()
            QuizUiEvent.SkipQuestion -> handleSkipQuestion()
            is QuizUiEvent.OpenGrammarGuide -> {
                _grammarGuideState.value = GrammarGuideState(isVisible = true, selectedTopicId = event.topicId)
                _gameHubSheetState.value = _gameHubSheetState.value.copy(isVisible = false)
            }
            QuizUiEvent.CloseGrammarGuide -> {
                _grammarGuideState.value = _grammarGuideState.value.copy(isVisible = false)
            }
        }
    }

    private fun handleSetQuestionCount(count: QuestionCount) {
        activeQuestionCount = count
        _gameHubSheetState.value = _gameHubSheetState.value.copy(selectedQuestionCount = count)
    }

    private fun handleFinishEarly() {
        ttsManager.stop()
        val totalSoFar = (currentIndex + 1).coerceAtLeast(1)
        _internalState.value = QuizUiState.Finished(
            finalScore = currentScore,
            totalQuestions = totalSoFar,
            gameType = activeGameType,
            isUnlimited = activeQuestionCount == QuestionCount.UNLIMITED
        )
    }

    private fun loadQuiz() {
        ttsManager.stop()
        userVoiceRecorder.deleteRecording()
        viewModelScope.launch {
            _internalState.value = QuizUiState.Loading
            currentIndex = 0
            currentScore = 0
            _quizQuestions.clear()

            val count = activeQuestionCount.value ?: Int.MAX_VALUE

            val sequence = when (activeGameType) {
                GameType.VOCAB_QUIZ -> repository.getQuizSequence(count)
                GameType.DER_DIE_DAS -> repository.getDerDieDasQuizSequence(count)
                GameType.SYNONYMS_ANTONYMS -> repository.getSynonymAntonymQuizSequence(count)
                GameType.SENTENCE_CLOZE -> repository.getSentenceClozeQuizSequence(count)
                GameType.WEAK_WORDS -> {
                    val failedWordIds = progressRepository.getMostFailedWordIds(count.coerceAtMost(50))
                    repository.getFailedWordsQuizSequence(failedWordIds, count)
                }
                GameType.PRONUNCIATION_TRAINER -> repository.getPronunciationQuizSequence(count)
                GameType.KASUS_TRAINER -> repository.getKasusQuizSequence(count)
                GameType.SATZBAU_TRAINER -> repository.getSatzbauQuizSequence(count)
                GameType.VERB_TENSE_TRAINER -> repository.getVerbTensesQuizSequence(count)
            }

            _quizQuestions.addAll(sequence)

            if (_quizQuestions.isNotEmpty()) {
                val firstQ = _quizQuestions[0]
                _internalState.value = buildQuestionState(
                    question = firstQ,
                    questionNumber = 1,
                    totalQuestions = _quizQuestions.size,
                    score = 0,
                    isUnlimited = activeQuestionCount == QuestionCount.UNLIMITED
                )
                autoSpeakWordIfApplicable(firstQ)
            }
        }
    }

    private fun handleSelectGameMode(gameType: GameType) {
        _gameHubSheetState.value = _gameHubSheetState.value.copy(isVisible = false)
        activeGameType = gameType
        loadQuiz()
    }

    private fun handleSelectOption(option: String) {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        if (currentState.isAnswerSubmitted) return

        val currentQ = _quizQuestions.getOrNull(currentIndex) ?: return
        val isCorrect = option.trim().equals(currentQ.correctOption.trim(), ignoreCase = true)
        if (isCorrect) {
            currentScore++
            soundEffectManager.playCorrect()
        } else {
            soundEffectManager.playWrong()
        }

        viewModelScope.launch {
            progressRepository.recordAttempt(currentQ.word.id, isCorrect)
        }

        _internalState.value = currentState.copy(
            selectedOption = option,
            isAnswerSubmitted = true,
            score = currentScore
        )

        if (currentState.gameType == GameType.DER_DIE_DAS) {
            ttsManager.speakWord(currentQ.word.word)
        }
    }

    private fun handleNextQuestion() {
        ttsManager.stop()
        userVoiceRecorder.deleteRecording()
        if (currentIndex + 1 < _quizQuestions.size) {
            currentIndex++
            val nextQ = _quizQuestions[currentIndex]
            _internalState.value = buildQuestionState(
                question = nextQ,
                questionNumber = currentIndex + 1,
                totalQuestions = _quizQuestions.size,
                score = currentScore,
                isUnlimited = activeQuestionCount == QuestionCount.UNLIMITED
            )
            autoSpeakWordIfApplicable(nextQ)
        } else if (activeQuestionCount == QuestionCount.UNLIMITED && _quizQuestions.isNotEmpty()) {
            // Endless mode: reshuffle and continue
            _quizQuestions.shuffle()
            currentIndex = 0
            val nextQ = _quizQuestions[0]
            val currentQNum = (_internalState.value as? QuizUiState.Question)?.questionNumber ?: 1
            _internalState.value = buildQuestionState(
                question = nextQ,
                questionNumber = currentQNum + 1,
                totalQuestions = _quizQuestions.size,
                score = currentScore,
                isUnlimited = true
            )
            autoSpeakWordIfApplicable(nextQ)
        } else {
            _internalState.value = QuizUiState.Finished(
                finalScore = currentScore,
                totalQuestions = _quizQuestions.size,
                gameType = activeGameType,
                isUnlimited = activeQuestionCount == QuestionCount.UNLIMITED
            )
        }
    }

    private fun handlePlayWordAudio() {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        userVoiceRecorder.stopPlayback()
        _internalState.value = currentState.copy(isUserRecordingPlaying = false)
        ttsManager.speakWord(currentState.currentWord.word)
    }

    fun playWordAudioExplicit(word: String) {
        userVoiceRecorder.stopPlayback()
        val currentState = _internalState.value as? QuizUiState.Question
        if (currentState != null && currentState.isUserRecordingPlaying) {
            _internalState.value = currentState.copy(isUserRecordingPlaying = false)
        }
        ttsManager.speakWord(word)
    }

    fun playSentenceAudio(sentence: String) {
        userVoiceRecorder.stopPlayback()
        val currentState = _internalState.value as? QuizUiState.Question
        if (currentState != null && currentState.isUserRecordingPlaying) {
            _internalState.value = currentState.copy(isUserRecordingPlaying = false)
        }
        ttsManager.speakExample(sentence)
    }

    private fun handlePlayExampleAudio() {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        userVoiceRecorder.stopPlayback()
        _internalState.value = currentState.copy(isUserRecordingPlaying = false)
        ttsManager.speakExample(currentState.currentWord.example)
    }

    private fun handlePlayUserRecording() {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        if (currentState.isUserRecordingPlaying) {
            handleStopUserRecording()
            return
        }
        ttsManager.stop()
        val started = userVoiceRecorder.playRecording {
            val s = _internalState.value as? QuizUiState.Question
            if (s != null && s.isUserRecordingPlaying) {
                _internalState.value = s.copy(isUserRecordingPlaying = false)
            }
        }
        if (started) {
            _internalState.value = currentState.copy(isUserRecordingPlaying = true)
        }
    }

    private fun handleStopUserRecording() {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        userVoiceRecorder.stopPlayback()
        _internalState.value = currentState.copy(isUserRecordingPlaying = false)
    }

    private fun handleToggleTranslation() {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        _internalState.value = currentState.copy(
            isTranslationVisible = !currentState.isTranslationVisible
        )
    }

    private fun handleStartListening() {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        ttsManager.stop()
        userVoiceRecorder.stopPlayback()
        _internalState.value = currentState.copy(
            speechState = SpeechState.Listening,
            isAnswerSubmitted = if (currentState.gameType == GameType.PRONUNCIATION_TRAINER) false else currentState.isAnswerSubmitted,
            hasUserRecording = false,
            isUserRecordingPlaying = false
        )
    }

    private fun handleStopListening() {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        if (currentState.speechState is SpeechState.Listening) {
            _internalState.value = currentState.copy(speechState = SpeechState.Idle)
        }
    }

    private fun handleSpeechResult(recognizedText: String) {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        viewModelScope.launch {
            val result = fuzzyMatcher.evaluatePronunciation(
                targetWord = currentState.currentWord.word,
                spokenText = recognizedText
            )
            if (currentState.gameType == GameType.PRONUNCIATION_TRAINER) {
                progressRepository.recordAttempt(currentState.currentWord.id, isCorrect = result.isMatch)
                if (result.isMatch) {
                    currentScore++
                    soundEffectManager.playCorrect()
                    _internalState.value = currentState.copy(
                        speechState = SpeechState.Success(
                            similarity = result.similarityScore,
                            transcribedText = recognizedText
                        ),
                        isAnswerSubmitted = true,
                        score = currentScore,
                        hasUserRecording = false,
                        isUserRecordingPlaying = false
                    )
                } else {
                    soundEffectManager.playWrong()
                    _internalState.value = currentState.copy(
                        speechState = SpeechState.Failure(
                            transcribedText = recognizedText,
                            similarity = result.similarityScore
                        ),
                        isAnswerSubmitted = true,
                        hasUserRecording = false,
                        isUserRecordingPlaying = false
                    )
                }
            } else {
                if (result.isMatch) {
                    soundEffectManager.playCorrect()
                } else {
                    soundEffectManager.playWrong()
                }
                _internalState.value = if (result.isMatch) {
                    currentState.copy(speechState = SpeechState.Success(result.similarityScore, recognizedText))
                } else {
                    currentState.copy(speechState = SpeechState.Failure(recognizedText, result.similarityScore))
                }
            }
        }
    }

    private fun handleSpeechError(code: Int) {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        val isNoMatch = code == 7 || code == 6 // SpeechRecognizer.ERROR_NO_MATCH, ERROR_SPEECH_TIMEOUT
        val desc = if (isNoMatch) "No speech detected" else "Audio error (code: $code)"
        if (currentState.gameType == GameType.PRONUNCIATION_TRAINER) {
            soundEffectManager.playWrong()
            _internalState.value = currentState.copy(
                speechState = SpeechState.Failure(desc, 0f),
                isAnswerSubmitted = true,
                hasUserRecording = false,
                isUserRecordingPlaying = false
            )
        } else {
            _internalState.value = currentState.copy(
                speechState = SpeechState.Failure(desc, 0f)
            )
        }
    }

    private fun handleRetryPronunciation() {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        ttsManager.stop()
        userVoiceRecorder.deleteRecording()
        _internalState.value = currentState.copy(
            speechState = SpeechState.Idle,
            isAnswerSubmitted = false,
            hasUserRecording = false,
            isUserRecordingPlaying = false
        )
    }

    private fun handleSkipQuestion() {
        val currentState = _internalState.value as? QuizUiState.Question ?: return
        ttsManager.stop()
        userVoiceRecorder.deleteRecording()
        viewModelScope.launch {
            progressRepository.recordAttempt(currentState.currentWord.id, isCorrect = false)
            handleNextQuestion()
        }
    }

    private fun handleOpenProgressSheet() {
        _progressSheetState.value = _progressSheetState.value.copy(isVisible = true, isLoading = true)
        viewModelScope.launch {
            val stats = progressRepository.getProgressStats()
            val failedWordIds = progressRepository.getMostFailedWordIds(30)
            val words = repository.getWordsByIds(failedWordIds.toSet())
            val userProgress = progressRepository.progressFlow.value

            val failedItems = words.mapNotNull { word ->
                val progress = userProgress.words[word.id]
                if (progress != null && progress.timesFailed > 0) {
                    val rate = if (progress.timesSeen > 0) {
                        (progress.timesCorrect.toFloat() / progress.timesSeen.toFloat()) * 100f
                    } else 0f
                    FailedWordItem(
                        word = word,
                        failureCount = progress.timesFailed,
                        timesSeen = progress.timesSeen,
                        successRate = rate
                    )
                } else null
            }.sortedByDescending { it.failureCount }

            _progressSheetState.value = ProgressSheetState(
                isVisible = true,
                stats = stats,
                failedWords = failedItems.toImmutableList(),
                isLoading = false
            )
        }
    }

    private fun handleCloseProgressSheet() {
        _progressSheetState.value = _progressSheetState.value.copy(isVisible = false)
    }

    private fun handleStartWeakWordsPractice() {
        handleCloseProgressSheet()
        activeGameType = GameType.WEAK_WORDS
        loadQuiz()
    }

    private fun handleResetProgress() {
        viewModelScope.launch {
            progressRepository.resetProgress()
            handleOpenProgressSheet()
        }
    }

    private fun buildQuestionState(
        question: QuizQuestion,
        questionNumber: Int,
        totalQuestions: Int,
        score: Int,
        isUnlimited: Boolean
    ): QuizUiState.Question {
        return QuizUiState.Question(
            currentWord = question.word,
            options = question.options,
            selectedOption = null,
            isAnswerSubmitted = false,
            speechState = SpeechState.Idle,
            currentlySpeaking = null,
            isTranslationVisible = false,
            questionNumber = questionNumber,
            totalQuestions = totalQuestions,
            score = score,
            gameType = question.gameType,
            prompt = question.prompt,
            displayWord = question.displayWord,
            subText = question.subText,
            explanation = question.explanation,
            correctOption = question.correctOption,
            isUnlimited = isUnlimited
        )
    }

    private fun autoSpeakWordIfApplicable(question: QuizQuestion) {
        if (question.gameType == GameType.VOCAB_QUIZ ||
            question.gameType == GameType.SYNONYMS_ANTONYMS ||
            question.gameType == GameType.PRONUNCIATION_TRAINER
        ) {
            ttsManager.speakWord(question.word.word)
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.stop()
        soundEffectManager.release()
        userVoiceRecorder.deleteRecording()
    }

    companion object {
        fun provideFactory(
            repository: VocabularyRepository,
            progressRepository: UserProgressRepository,
            ttsManager: TtsManager,
            soundEffectManager: SoundEffectManager = NoOpSoundEffectManager(),
            languageManager: LanguageManager = LanguageManager(),
            userVoiceRecorder: UserVoiceRecorder = NoOpUserVoiceRecorder()
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return QuizViewModel(repository, progressRepository, ttsManager, soundEffectManager, languageManager, fuzzyMatcher = FuzzyMatcher(), userVoiceRecorder = userVoiceRecorder) as T
            }
        }
    }
}
