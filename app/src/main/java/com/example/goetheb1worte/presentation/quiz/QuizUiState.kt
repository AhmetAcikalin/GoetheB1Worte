package com.example.goetheb1worte.presentation.quiz

import androidx.compose.runtime.Immutable
import com.example.goetheb1worte.audio.TtsTarget
import com.example.goetheb1worte.data.model.FailedWordItem
import com.example.goetheb1worte.data.model.ProgressStats
import com.example.goetheb1worte.data.model.WordItem
import com.example.goetheb1worte.data.repository.GameType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class QuestionCount(val value: Int?, val label: String) {
    TEN(10, "10"),
    TWENTY(20, "20"),
    THIRTY(30, "30"),
    FIFTY(50, "50"),
    HUNDRED(100, "100"),
    UNLIMITED(null, "∞ Endless")
}

@Immutable
sealed interface QuizUiState {
    data object Loading : QuizUiState

    data class Question(
        val currentWord: WordItem,
        val options: ImmutableList<String>,
        val selectedOption: String? = null,
        val isAnswerSubmitted: Boolean = false,
        val speechState: SpeechState = SpeechState.Idle,
        val currentlySpeaking: TtsTarget? = null,
        val isTranslationVisible: Boolean = false,
        val questionNumber: Int = 1,
        val totalQuestions: Int = 20,
        val score: Int = 0,
        val gameType: GameType = GameType.VOCAB_QUIZ,
        val prompt: String = "What does this word mean in English?",
        val displayWord: String = currentWord.word,
        val subText: String? = null,
        val explanation: String? = null,
        val correctOption: String = "",
        val isUnlimited: Boolean = false,
        val hasUserRecording: Boolean = false,
        val isUserRecordingPlaying: Boolean = false
    ) : QuizUiState

    data class Finished(
        val finalScore: Int,
        val totalQuestions: Int,
        val gameType: GameType = GameType.VOCAB_QUIZ,
        val isUnlimited: Boolean = false
    ) : QuizUiState
}

@Immutable
data class ProgressSheetState(
    val isVisible: Boolean = false,
    val stats: ProgressStats? = null,
    val failedWords: ImmutableList<FailedWordItem> = persistentListOf(),
    val isLoading: Boolean = false
)

@Immutable
data class GameHubSheetState(
    val isVisible: Boolean = false,
    val selectedQuestionCount: QuestionCount = QuestionCount.TWENTY
)

@Immutable
data class GrammarGuideState(
    val isVisible: Boolean = false,
    val selectedTopicId: String? = null
)

@Immutable
sealed interface SpeechState {
    data object Idle : SpeechState
    data object Listening : SpeechState
    data class Success(val similarity: Float = 1.0f, val transcribedText: String = "") : SpeechState
    data class Failure(val transcribedText: String, val similarity: Float = 0f) : SpeechState
}

sealed interface QuizUiEvent {
    data class SelectOption(val option: String) : QuizUiEvent
    data object NextQuestion : QuizUiEvent
    data object PlayWordAudio : QuizUiEvent
    data object PlayExampleAudio : QuizUiEvent
    data object PlayUserRecording : QuizUiEvent
    data object StopUserRecording : QuizUiEvent
    data object ToggleTranslationVisibility : QuizUiEvent
    data object StartListening : QuizUiEvent
    data object StopListening : QuizUiEvent
    data class SpeechResult(val text: String) : QuizUiEvent
    data class SpeechError(val code: Int) : QuizUiEvent
    data object RestartQuiz : QuizUiEvent
    data object OpenProgressSheet : QuizUiEvent
    data object CloseProgressSheet : QuizUiEvent
    data object StartWeakWordsPractice : QuizUiEvent
    data object ResetProgress : QuizUiEvent
    data object OpenGameHub : QuizUiEvent
    data object CloseGameHub : QuizUiEvent
    data class SelectGameMode(val gameType: GameType) : QuizUiEvent
    data class SetQuestionCount(val count: QuestionCount) : QuizUiEvent
    data object FinishEarly : QuizUiEvent
    data class SetLanguage(val language: com.example.goetheb1worte.localization.AppLanguage) : QuizUiEvent
    data object RetryPronunciation : QuizUiEvent
    data object SkipQuestion : QuizUiEvent
    data class OpenGrammarGuide(val topicId: String? = null) : QuizUiEvent
    data object CloseGrammarGuide : QuizUiEvent
}
