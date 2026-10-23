package com.example.goetheb1worte.presentation.quiz

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.goetheb1worte.audio.SpeechRecognizerHelper
import com.example.goetheb1worte.data.repository.GameType
import com.example.goetheb1worte.localization.AppStrings
import com.example.goetheb1worte.localization.LocalAppStrings
import com.example.goetheb1worte.localization.getAppStrings
import com.example.goetheb1worte.localization.getLocalizedContext
import com.example.goetheb1worte.presentation.quiz.cards.DerDieDasCard
import com.example.goetheb1worte.presentation.quiz.cards.GrammarQuizCard
import com.example.goetheb1worte.presentation.grammar.GrammarGuideScreen
import com.example.goetheb1worte.presentation.quiz.cards.PronunciationTrainerCard
import com.example.goetheb1worte.presentation.quiz.cards.SentenceClozeCard
import com.example.goetheb1worte.presentation.quiz.cards.SynonymAntonymCard
import com.example.goetheb1worte.presentation.quiz.cards.VocabQuizCard
import com.example.goetheb1worte.presentation.quiz.components.FinishedContent
import com.example.goetheb1worte.presentation.quiz.dialogs.LanguageDialog
import com.example.goetheb1worte.presentation.quiz.sheets.GameHubSheetContent
import com.example.goetheb1worte.presentation.quiz.sheets.ProgressSheetContent

import androidx.compose.material.icons.filled.Quiz
import com.example.goetheb1worte.data.grammar.guide.CefrLevel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onNavigateToGrammarGuide: (topicId: String?, level: CefrLevel?) -> Unit = { _, _ -> },
    onNavigateToInteractiveQuiz: (level: CefrLevel, topicId: String?) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val progressSheetState by viewModel.progressSheetState.collectAsStateWithLifecycle()
    val gameHubSheetState by viewModel.gameHubSheetState.collectAsStateWithLifecycle()
    val grammarGuideState by viewModel.grammarGuideState.collectAsStateWithLifecycle()
    val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val localizedContext = remember(context, currentLanguage) {
        getLocalizedContext(context, currentLanguage)
    }
    val strings = remember(localizedContext) {
        getAppStrings(localizedContext)
    }

    var showPermissionRationale by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    val speechHelper = remember {
        SpeechRecognizerHelper(
            context = context,
            onResultsReceived = { text -> viewModel.onEvent(QuizUiEvent.SpeechResult(text)) },
            onErrorReceived = { code -> viewModel.onEvent(QuizUiEvent.SpeechError(code)) },
            onListeningStateChanged = { isListening ->
                if (!isListening) {
                    viewModel.onEvent(QuizUiEvent.StopListening)
                }
            }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            speechHelper.destroy()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onEvent(QuizUiEvent.StartListening)
            speechHelper.startListening()
        } else {
            showPermissionRationale = true
        }
    }

    fun requestMic() {
        val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val isListening = (uiState as? QuizUiState.Question)?.speechState is SpeechState.Listening
            if (isListening) {
                speechHelper.stopListening()
                viewModel.onEvent(QuizUiEvent.StopListening)
            } else {
                viewModel.onEvent(QuizUiEvent.StartListening)
                speechHelper.startListening()
            }
        } else {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
                    TopAppBar(
                        title = {
                            val currentGame = when (val s = uiState) {
                            is QuizUiState.Question -> s.gameType
                            is QuizUiState.Finished -> s.gameType
                            else -> GameType.VOCAB_QUIZ
                        }

                        val (gameName, gameBg, gameFg) = when (currentGame) {
                            GameType.VOCAB_QUIZ -> Triple("🎯 ${strings.gameVocabTitle}", MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.onSecondaryContainer)
                            GameType.KASUS_TRAINER -> Triple("⚖️ ${strings.gameKasusTitle}", Color(0xFFE8F5E9), Color(0xFF1B5E20))
                            GameType.SATZBAU_TRAINER -> Triple("🧩 ${strings.gameSatzbauTitle}", Color(0xFFFFF3E0), Color(0xFFE65100))
                            GameType.VERB_TENSE_TRAINER -> Triple("⏳ ${strings.gameTensesTitle}", Color(0xFFEDE7F6), Color(0xFF4A148C))
                            GameType.DER_DIE_DAS -> Triple("⚡ ${strings.gameDddTitle}", Color(0xFFEDE7F6), Color(0xFF4A148C))
                            GameType.SYNONYMS_ANTONYMS -> Triple("🔄 ${strings.gameSynTitle}", Color(0xFFE0F7FA), Color(0xFF006064))
                            GameType.SENTENCE_CLOZE -> Triple("📝 ${strings.gameClozeTitle}", Color(0xFFFFF3E0), Color(0xFFE65100))
                            GameType.WEAK_WORDS -> Triple("⚠️ ${strings.gameWeakTitle}", MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.onErrorContainer)
                            GameType.PRONUNCIATION_TRAINER -> Triple("🗣️ ${strings.gamePronounceTitle}", Color(0xFFF3E5F5), Color(0xFF6A1B9A))
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = strings.appTitle,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    maxLines = 1
                                )

                                Surface(
                                    shape = RoundedCornerShape(percent = 50),
                                    color = gameBg,
                                    modifier = Modifier.clickable { viewModel.onEvent(QuizUiEvent.OpenGameHub) }
                                ) {
                                    Text(
                                        text = gameName,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = gameFg,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                            Text(
                                text = strings.topBarHint,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    actions = {
                        // Interactive Grammar Quiz Button
                        IconButton(onClick = { onNavigateToInteractiveQuiz(CefrLevel.A1, null) }) {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = "Grammar Quiz",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        // B1 Grammar Guide Button
                        IconButton(onClick = { onNavigateToGrammarGuide(null, null) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = strings.grammarGuideTitle,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Language Switcher Button
                        IconButton(onClick = { showLanguageDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = strings.languageDialogTitle,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Game Hub Button
                        IconButton(onClick = { viewModel.onEvent(QuizUiEvent.OpenGameHub) }) {
                            Icon(
                                imageVector = Icons.Default.GridView,
                                contentDescription = strings.gameHubTitle,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Progress Button
                        IconButton(onClick = { viewModel.onEvent(QuizUiEvent.OpenProgressSheet) }) {
                            Icon(
                                imageVector = Icons.Default.Insights,
                                contentDescription = strings.progressSheetTitle,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
            modifier = modifier
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (val state = uiState) {
                    is QuizUiState.Loading -> {
                        LoadingContent(strings = strings)
                    }

                    is QuizUiState.Question -> {
                        QuestionContent(
                            state = state,
                            strings = strings,
                            onOptionSelected = { option -> viewModel.onEvent(QuizUiEvent.SelectOption(option)) },
                            onNextClicked = { viewModel.onEvent(QuizUiEvent.NextQuestion) },
                            onPlayWordAudio = { viewModel.onEvent(QuizUiEvent.PlayWordAudio) },
                            onPlayExampleAudio = { viewModel.onEvent(QuizUiEvent.PlayExampleAudio) },
                            onToggleTranslation = { viewModel.onEvent(QuizUiEvent.ToggleTranslationVisibility) },
                            onMicClicked = { requestMic() },
                            onFinishEarly = { viewModel.onEvent(QuizUiEvent.FinishEarly) },
                            onRetryPronunciation = {
                                viewModel.onEvent(QuizUiEvent.RetryPronunciation)
                                requestMic()
                            },
                            onSkipQuestion = { viewModel.onEvent(QuizUiEvent.SkipQuestion) },
                            onPlayUserRecording = {
                                if (state.isUserRecordingPlaying) {
                                    viewModel.onEvent(QuizUiEvent.StopUserRecording)
                                } else {
                                    viewModel.onEvent(QuizUiEvent.PlayUserRecording)
                                }
                            },
                            onOpenGrammarGuide = { topicId -> onNavigateToGrammarGuide(topicId, null) }
                        )
                    }

                    is QuizUiState.Finished -> {
                        FinishedContent(
                            state = state,
                            strings = strings,
                            onRestart = { viewModel.onEvent(QuizUiEvent.RestartQuiz) },
                            onOpenProgress = { viewModel.onEvent(QuizUiEvent.OpenProgressSheet) },
                            onOpenGameHub = { viewModel.onEvent(QuizUiEvent.OpenGameHub) }
                        )
                    }
                }
            }

            // Language Selection Dialog
        if (showLanguageDialog) {
            LanguageDialog(
                currentLanguage = currentLanguage,
                strings = strings,
                onDismiss = { showLanguageDialog = false },
                onSelectLanguage = { lang ->
                    viewModel.onEvent(QuizUiEvent.SetLanguage(lang))
                    showLanguageDialog = false
                }
            )
        }

        // Modal Bottom Sheet for Game Modes (Game Hub)
        if (gameHubSheetState.isVisible) {
            val hubSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            ModalBottomSheet(
                onDismissRequest = { viewModel.onEvent(QuizUiEvent.CloseGameHub) },
                sheetState = hubSheetState,
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            ) {
                GameHubSheetContent(
                    selectedCount = gameHubSheetState.selectedQuestionCount,
                    strings = strings,
                    onSelectCount = { count -> viewModel.onEvent(QuizUiEvent.SetQuestionCount(count)) },
                    onSelectGameMode = { mode -> viewModel.onEvent(QuizUiEvent.SelectGameMode(mode)) },
                    onOpenGrammarGuide = {
                        viewModel.onEvent(QuizUiEvent.CloseGameHub)
                        onNavigateToGrammarGuide(null, null)
                    }
                )
            }
        }

        // Modal Bottom Sheet for Progress & Most Failed Words
        if (progressSheetState.isVisible) {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            ModalBottomSheet(
                onDismissRequest = { viewModel.onEvent(QuizUiEvent.CloseProgressSheet) },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            ) {
                ProgressSheetContent(
                    state = progressSheetState,
                    strings = strings,
                    onStartWeakPractice = { viewModel.onEvent(QuizUiEvent.StartWeakWordsPractice) },
                    onPlayWordAudio = { word -> viewModel.playWordAudioExplicit(word) }
                )
            }
        }

        if (showPermissionRationale) {
            AlertDialog(
                onDismissRequest = { showPermissionRationale = false },
                title = { Text(strings.micPermissionTitle) },
                text = { Text(strings.micPermissionDesc) },
                confirmButton = {
                    TextButton(onClick = {
                        showPermissionRationale = false
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }) {
                        Text(strings.grantPermission)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showPermissionRationale = false }) {
                        Text(strings.cancel)
                    }
                }
            )
        }
    }
}

@Composable
private fun LoadingContent(strings: AppStrings) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(52.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = strings.loadingTraining,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun QuestionContent(
    state: QuizUiState.Question,
    strings: AppStrings,
    onOptionSelected: (String) -> Unit,
    onNextClicked: () -> Unit,
    onPlayWordAudio: () -> Unit,
    onPlayExampleAudio: () -> Unit,
    onToggleTranslation: () -> Unit,
    onMicClicked: () -> Unit,
    onFinishEarly: () -> Unit,
    onRetryPronunciation: () -> Unit = {},
    onSkipQuestion: () -> Unit = {},
    onPlayUserRecording: () -> Unit = {},
    onOpenGrammarGuide: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val animatedProgress by animateFloatAsState(
        targetValue = if (state.isUnlimited) 1f else state.questionNumber.toFloat() / state.totalQuestions.toFloat(),
        label = "ProgressAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (state.isUnlimited) strings.questionEndless.format(state.questionNumber) else strings.questionOf.format(state.questionNumber, state.totalQuestions),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f, fill = false),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = strings.pointsFormat.format(state.score),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                if (state.currentWord.page != null) {
                    Text(
                        text = strings.pageAbbrFormat.format(state.currentWord.page),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                IconButton(
                    onClick = onFinishEarly,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = strings.finishRoundTooltip,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (state.isUnlimited) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                color = MaterialTheme.colorScheme.tertiary
            )
        } else {
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        when (state.gameType) {
            GameType.KASUS_TRAINER, GameType.SATZBAU_TRAINER, GameType.VERB_TENSE_TRAINER -> {
                GrammarQuizCard(
                    state = state,
                    strings = strings,
                    onOptionSelected = onOptionSelected,
                    onToggleTranslation = onToggleTranslation,
                    onPlayAudio = onPlayWordAudio,
                    onOpenGrammarGuide = onOpenGrammarGuide
                )
            }
            GameType.DER_DIE_DAS -> {
                DerDieDasCard(
                    state = state,
                    strings = strings,
                    onOptionSelected = onOptionSelected,
                    onPlayAudio = onPlayWordAudio
                )
            }
            GameType.SYNONYMS_ANTONYMS -> {
                SynonymAntonymCard(
                    state = state,
                    strings = strings,
                    onOptionSelected = onOptionSelected,
                    onPlayWordAudio = onPlayWordAudio
                )
            }
            GameType.SENTENCE_CLOZE -> {
                SentenceClozeCard(
                    state = state,
                    strings = strings,
                    onOptionSelected = onOptionSelected,
                    onToggleTranslation = onToggleTranslation,
                    onPlayAudio = onPlayExampleAudio
                )
            }
            GameType.VOCAB_QUIZ, GameType.WEAK_WORDS -> {
                VocabQuizCard(
                    state = state,
                    strings = strings,
                    onOptionSelected = onOptionSelected,
                    onPlayWordAudio = onPlayWordAudio,
                    onPlayExampleAudio = onPlayExampleAudio,
                    onToggleTranslation = onToggleTranslation,
                    onMicClicked = onMicClicked
                )
            }
            GameType.PRONUNCIATION_TRAINER -> {
                PronunciationTrainerCard(
                    state = state,
                    strings = strings,
                    onPlayWordAudio = onPlayWordAudio,
                    onPlayExampleAudio = onPlayExampleAudio,
                    onToggleTranslation = onToggleTranslation,
                    onMicClicked = onMicClicked,
                    onRetryPronunciation = onRetryPronunciation,
                    onSkipQuestion = onSkipQuestion,
                    onPlayUserRecording = onPlayUserRecording,
                    isUserRecordingPlaying = state.isUserRecordingPlaying
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (state.isAnswerSubmitted) {
            Button(
                onClick = onNextClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = if (state.isUnlimited || state.questionNumber < state.totalQuestions) strings.nextQuestion else strings.seeResult,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
