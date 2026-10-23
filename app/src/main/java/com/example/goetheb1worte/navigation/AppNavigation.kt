package com.example.goetheb1worte.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.goetheb1worte.audio.SoundEffectManager
import com.example.goetheb1worte.audio.TtsManager
import com.example.goetheb1worte.data.grammar.guide.CefrLevel
import com.example.goetheb1worte.localization.LocalAppStrings
import com.example.goetheb1worte.localization.getAppStrings
import com.example.goetheb1worte.localization.getLocalizedContext
import com.example.goetheb1worte.presentation.grammar.GrammarGuideScreen
import com.example.goetheb1worte.presentation.grammar.InteractiveGrammarQuizScreen
import com.example.goetheb1worte.presentation.quiz.QuizScreen
import com.example.goetheb1worte.presentation.quiz.QuizUiEvent
import com.example.goetheb1worte.presentation.quiz.QuizViewModel

@Composable
fun AppNavigation(
    quizViewModel: QuizViewModel,
    ttsManager: TtsManager,
    soundEffectManager: SoundEffectManager,
    modifier: Modifier = Modifier
) {
    val currentLanguage by quizViewModel.currentLanguage.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val localizedContext = remember(context, currentLanguage) {
        getLocalizedContext(context, currentLanguage)
    }
    val strings = remember(localizedContext) {
        getAppStrings(localizedContext)
    }

    val backStack = rememberNavBackStack(AppNavKey.Quiz)

    fun navigateTo(key: AppNavKey) {
        backStack.add(key)
    }

    fun popBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.size - 1)
        }
    }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalConfiguration provides localizedContext.resources.configuration,
        LocalAppStrings provides strings
    ) {
        NavDisplay(
            backStack = backStack,
            modifier = modifier,
            onBack = { popBack() },
            entryProvider = entryProvider {
                entry<AppNavKey.Quiz> {
                    QuizScreen(
                        viewModel = quizViewModel,
                        onNavigateToGrammarGuide = { topicId, level ->
                            navigateTo(AppNavKey.GrammarGuide(initialTopicId = topicId, initialLevel = level))
                        },
                        onNavigateToInteractiveQuiz = { level, topicId ->
                            navigateTo(AppNavKey.InteractiveGrammarQuiz(level = level, topicId = topicId))
                        }
                    )
                }

                entry<AppNavKey.GrammarGuide> { key ->
                    GrammarGuideScreen(
                        initialTopicId = key.initialTopicId,
                        initialLevel = key.initialLevel,
                        strings = strings,
                        onClose = { popBack() },
                        onPracticeTopic = { gameType ->
                            quizViewModel.onEvent(QuizUiEvent.SelectGameMode(gameType))
                            popBack()
                        },
                        onStartInteractiveQuiz = { level, topicId ->
                            navigateTo(AppNavKey.InteractiveGrammarQuiz(level = level, topicId = topicId))
                        },
                        onPlayAudio = { sentence ->
                            quizViewModel.playSentenceAudio(sentence)
                        }
                    )
                }

                entry<AppNavKey.InteractiveGrammarQuiz> { key ->
                    InteractiveGrammarQuizScreen(
                        level = key.level,
                        topicId = key.topicId,
                        strings = strings,
                        soundEffectManager = soundEffectManager,
                        ttsManager = ttsManager,
                        onBack = { popBack() }
                    )
                }
            }
        )
    }
}
