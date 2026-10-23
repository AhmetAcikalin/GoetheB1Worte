package com.example.goetheb1worte.presentation.grammar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goetheb1worte.audio.SoundEffectManager
import com.example.goetheb1worte.audio.TtsManager
import com.example.goetheb1worte.data.grammar.guide.CefrLevel
import com.example.goetheb1worte.data.grammar.guide.GrammarQuizType
import com.example.goetheb1worte.data.grammar.guide.InteractiveGrammarQuestion
import com.example.goetheb1worte.data.grammar.quiz.InteractiveGrammarQuizData
import com.example.goetheb1worte.localization.AppStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun InteractiveGrammarQuizScreen(
    level: CefrLevel,
    topicId: String? = null,
    strings: AppStrings,
    soundEffectManager: SoundEffectManager,
    ttsManager: TtsManager,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedLevel by remember { mutableStateOf(level) }

    val rawQuestions = remember(selectedLevel, topicId) {
        if (topicId != null) {
            val list = InteractiveGrammarQuizData.getQuestionsByTopic(topicId)
            if (list.isNotEmpty()) list else InteractiveGrammarQuizData.getQuestionsByLevel(selectedLevel)
        } else {
            InteractiveGrammarQuizData.getQuestionsByLevel(selectedLevel)
        }
    }

    var questions by remember(rawQuestions) {
        mutableStateOf(rawQuestions.shuffled().ifEmpty { InteractiveGrammarQuizData.getQuestionsByLevel(selectedLevel) })
    }

    var currentIndex by remember(questions) { mutableIntStateOf(0) }
    var score by remember(questions) { mutableIntStateOf(0) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var selectedChoice by remember { mutableStateOf<String?>(null) }
    var isQuizCompleted by remember { mutableStateOf(false) }

    // Word order user assembly
    val currentSelectedTokens = remember { mutableStateListOf<String>() }

    val currentQuestion: InteractiveGrammarQuestion? = questions.getOrNull(currentIndex)

    LaunchedEffect(currentIndex, currentQuestion) {
        isAnswerSubmitted = false
        isCorrect = false
        selectedChoice = null
        currentSelectedTokens.clear()
        currentQuestion?.let {
            if (it.type == GrammarQuizType.WORD_ORDER) {
                // Pre-clear assembly
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Grammar Quiz",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Surface(
                                shape = RoundedCornerShape(percent = 50),
                                color = when (selectedLevel) {
                                    CefrLevel.A1 -> Color(0xFFE8F5E9)
                                    CefrLevel.A2 -> Color(0xFFE3F2FD)
                                    CefrLevel.B1 -> Color(0xFFEDE7F6)
                                }
                            ) {
                                Text(
                                    text = selectedLevel.code,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = when (selectedLevel) {
                                        CefrLevel.A1 -> Color(0xFF1B5E20)
                                        CefrLevel.A2 -> Color(0xFF0D47A1)
                                        CefrLevel.B1 -> Color(0xFF4A148C)
                                    }
                                )
                            }
                        }
                        if (currentQuestion != null && !isQuizCompleted) {
                            Text(
                                text = currentQuestion.topicTitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "$score / ${questions.size}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isQuizCompleted || currentQuestion == null) {
                // Completed Screen
                QuizSummaryContent(
                    score = score,
                    totalQuestions = questions.size,
                    level = selectedLevel,
                    onRetry = {
                        questions = questions.shuffled()
                        currentIndex = 0
                        score = 0
                        isQuizCompleted = false
                    },
                    onLevelChange = { newLevel ->
                        selectedLevel = newLevel
                        questions = InteractiveGrammarQuizData.getQuestionsByLevel(newLevel).shuffled()
                        currentIndex = 0
                        score = 0
                        isQuizCompleted = false
                    },
                    onBack = onBack
                )
            } else {
                val progress = (currentIndex.toFloat()) / questions.size.coerceAtLeast(1)
                val animatedProgress by animateFloatAsState(targetValue = progress, label = "QuizProgress")

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Level selector bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CefrLevel.entries.forEach { lvl ->
                            FilterChip(
                                selected = selectedLevel == lvl,
                                onClick = {
                                    if (selectedLevel != lvl) {
                                        selectedLevel = lvl
                                        questions = InteractiveGrammarQuizData.getQuestionsByLevel(lvl).shuffled()
                                        currentIndex = 0
                                        score = 0
                                    }
                                },
                                label = { Text(lvl.code, fontWeight = FontWeight.Bold) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }

                    // Progress bar
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Question ${currentIndex + 1} of ${questions.size}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "${currentQuestion.type.icon} ${currentQuestion.type.label}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        LinearProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                        )
                    }

                    // Question Card
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Prompt
                            Text(
                                text = currentQuestion.prompt,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            // Sentence with blank or full sentence
                            if (!currentQuestion.sentenceWithBlank.isNullOrBlank()) {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = currentQuestion.sentenceWithBlank,
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = currentQuestion.translationEn,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontStyle = FontStyle.Italic,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = currentQuestion.translationEn,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            // Interactive Area based on type
                            when (currentQuestion.type) {
                                GrammarQuizType.SINGLE_CHOICE, GrammarQuizType.CLOZE -> {
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        currentQuestion.options.forEach { option ->
                                            val isSelected = selectedChoice == option
                                            val isThisCorrect = option == currentQuestion.correctChoice

                                            val containerColor by animateColorAsState(
                                                targetValue = when {
                                                    isAnswerSubmitted && isThisCorrect -> Color(0xFFD0F0C0)
                                                    isAnswerSubmitted && isSelected && !isThisCorrect -> Color(0xFFFFCDD2)
                                                    else -> MaterialTheme.colorScheme.surface
                                                },
                                                label = "OptionBg"
                                            )

                                            val borderColor = when {
                                                isAnswerSubmitted && isThisCorrect -> Color(0xFF2E7D32)
                                                isAnswerSubmitted && isSelected && !isThisCorrect -> Color(0xFFD32F2F)
                                                else -> MaterialTheme.colorScheme.outlineVariant
                                            }

                                            OutlinedButton(
                                                onClick = {
                                                    if (!isAnswerSubmitted) {
                                                        selectedChoice = option
                                                        isAnswerSubmitted = true
                                                        if (isThisCorrect) {
                                                            isCorrect = true
                                                            score += 1
                                                            soundEffectManager.playCorrect()
                                                        } else {
                                                            isCorrect = false
                                                            soundEffectManager.playWrong()
                                                        }
                                                    }
                                                },
                                                modifier = Modifier.fillMaxWidth(),
                                                shape = RoundedCornerShape(16.dp),
                                                border = BorderStroke(1.5.dp, borderColor),
                                                colors = ButtonDefaults.outlinedButtonColors(containerColor = containerColor),
                                                enabled = !isAnswerSubmitted
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(vertical = 8.dp),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = option,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        fontWeight = FontWeight.SemiBold,
                                                        color = MaterialTheme.colorScheme.onSurface
                                                    )
                                                    if (isAnswerSubmitted && isThisCorrect) {
                                                        Icon(
                                                            imageVector = Icons.Default.Check,
                                                            contentDescription = "Correct",
                                                            tint = Color(0xFF1B5E20)
                                                        )
                                                    } else if (isAnswerSubmitted && isSelected && !isThisCorrect) {
                                                        Icon(
                                                            imageVector = Icons.Default.Close,
                                                            contentDescription = "Wrong",
                                                            tint = Color(0xFFB71C1C)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                GrammarQuizType.WORD_ORDER -> {
                                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                                        // Assembly Zone
                                        Surface(
                                            modifier = Modifier.fillMaxWidth(),
                                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                                            shape = RoundedCornerShape(16.dp),
                                            border = BorderStroke(
                                                1.5.dp,
                                                when {
                                                    isAnswerSubmitted && isCorrect -> Color(0xFF2E7D32)
                                                    isAnswerSubmitted && !isCorrect -> Color(0xFFD32F2F)
                                                    else -> MaterialTheme.colorScheme.outlineVariant
                                                }
                                            )
                                        ) {
                                            Column(modifier = Modifier.padding(14.dp)) {
                                                Text(
                                                    text = "Assemble sentence (tap words in order):",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = MaterialTheme.colorScheme.outline
                                                )
                                                Spacer(modifier = Modifier.height(8.dp))
                                                if (currentSelectedTokens.isEmpty()) {
                                                    Text(
                                                        text = "Tap tokens below to build the sentence...",
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        fontStyle = FontStyle.Italic,
                                                        color = MaterialTheme.colorScheme.outlineVariant
                                                    )
                                                } else {
                                                    FlowRow(
                                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                                    ) {
                                                        currentSelectedTokens.forEachIndexed { tokenIdx, token ->
                                                            Surface(
                                                                shape = RoundedCornerShape(12.dp),
                                                                color = if (isAnswerSubmitted) {
                                                                    if (isCorrect) Color(0xFFD0F0C0) else Color(0xFFFFCDD2)
                                                                } else {
                                                                    MaterialTheme.colorScheme.primaryContainer
                                                                },
                                                                modifier = Modifier.clickable(enabled = !isAnswerSubmitted) {
                                                                    currentSelectedTokens.removeAt(tokenIdx)
                                                                }
                                                            ) {
                                                                Text(
                                                                    text = token,
                                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                                                    style = MaterialTheme.typography.bodyMedium,
                                                                    fontWeight = FontWeight.Bold,
                                                                    color = if (isAnswerSubmitted) {
                                                                        if (isCorrect) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                                                                    } else {
                                                                        MaterialTheme.colorScheme.onPrimaryContainer
                                                                    }
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        // Available tokens
                                        val availableTokens = remember(currentQuestion, currentSelectedTokens.size) {
                                            val used = currentSelectedTokens.toMutableList()
                                            currentQuestion.options.filter { token ->
                                                val removed = used.remove(token)
                                                !removed
                                            }
                                        }

                                        FlowRow(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            availableTokens.forEach { token ->
                                                Surface(
                                                    shape = RoundedCornerShape(12.dp),
                                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                                                    modifier = Modifier.clickable(enabled = !isAnswerSubmitted) {
                                                        currentSelectedTokens.add(token)
                                                    }
                                                ) {
                                                    Text(
                                                        text = token,
                                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        fontWeight = FontWeight.Medium,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                            }
                                        }

                                        // Action buttons for word order
                                        if (!isAnswerSubmitted) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                OutlinedButton(
                                                    onClick = { currentSelectedTokens.clear() },
                                                    enabled = currentSelectedTokens.isNotEmpty()
                                                ) {
                                                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text("Reset")
                                                }
                                                Button(
                                                    onClick = {
                                                        isAnswerSubmitted = true
                                                        val expected = currentQuestion.correctTokens ?: emptyList()
                                                        val matched = currentSelectedTokens.toList() == expected
                                                        if (matched) {
                                                            isCorrect = true
                                                            score += 1
                                                            soundEffectManager.playCorrect()
                                                        } else {
                                                            isCorrect = false
                                                            soundEffectManager.playWrong()
                                                        }
                                                    },
                                                    enabled = currentSelectedTokens.size == currentQuestion.options.size
                                                ) {
                                                    Text("Check Sentence")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Explanation Card on Submission
                    AnimatedVisibility(
                        visible = isAnswerSubmitted,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = if (isCorrect) Color(0xFFF1F8E9) else Color(0xFFFFF3E0)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                                            contentDescription = null,
                                            tint = if (isCorrect) Color(0xFF2E7D32) else Color(0xFFE65100),
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Text(
                                            text = if (isCorrect) "Richtig! (Correct)" else "Nicht ganz! (Review rule)",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isCorrect) Color(0xFF1B5E20) else Color(0xFFE65100)
                                        )
                                    }

                                    FilledTonalIconButton(
                                        onClick = { ttsManager.speakExample(currentQuestion.fullSentenceDe) },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = "Speak sentence",
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = currentQuestion.fullSentenceDe,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                                Text(
                                    text = currentQuestion.ruleExplanationDe,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = currentQuestion.ruleExplanationEn,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                currentQuestion.tip?.let { tipText ->
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Lightbulb,
                                            contentDescription = null,
                                            tint = Color(0xFFF57F17),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = tipText,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF795548)
                                        )
                                    }
                                }

                                currentQuestion.exceptionNote?.let { excText ->
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.WarningAmber,
                                            contentDescription = null,
                                            tint = Color(0xFFD84315),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = excText,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFFBF360C)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Button(
                                    onClick = {
                                        if (currentIndex + 1 < questions.size) {
                                            currentIndex += 1
                                        } else {
                                            isQuizCompleted = true
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Text(
                                        text = if (currentIndex + 1 < questions.size) "Next Question" else "See Results",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun QuizSummaryContent(
    score: Int,
    totalQuestions: Int,
    level: CefrLevel,
    onRetry: () -> Unit,
    onLevelChange: (CefrLevel) -> Unit,
    onBack: () -> Unit
) {
    val percentage = if (totalQuestions > 0) (score * 100) / totalQuestions else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = when {
                percentage >= 80 -> Color(0xFFE8F5E9)
                percentage >= 60 -> Color(0xFFFFF3E0)
                else -> Color(0xFFFFEBEE)
            },
            modifier = Modifier.size(100.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    modifier = Modifier.size(54.dp),
                    tint = when {
                        percentage >= 80 -> Color(0xFF2E7D32)
                        percentage >= 60 -> Color(0xFFEF6C00)
                        else -> Color(0xFFC62828)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = when {
                percentage >= 80 -> "Ausgezeichnet! 🌟"
                percentage >= 60 -> "Sehr gut! 👍"
                else -> "Weiter üben! 💪"
            },
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "You scored $score out of $totalQuestions ($percentage%) in ${level.title}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onRetry,
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Try Again")
            }

            Button(
                onClick = onBack,
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Back to Grammar Hub")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Switch CEFR Level to Practice:",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CefrLevel.entries.forEach { lvl ->
                if (lvl != level) {
                    FilledTonalButton(
                        onClick = { onLevelChange(lvl) },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Practice ${lvl.code}")
                    }
                }
            }
        }
    }
}
