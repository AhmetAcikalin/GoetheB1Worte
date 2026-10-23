package com.example.goetheb1worte.presentation.quiz.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.goetheb1worte.audio.TtsTarget
import com.example.goetheb1worte.data.repository.GameType
import com.example.goetheb1worte.localization.AppStrings
import com.example.goetheb1worte.presentation.quiz.QuizUiState
import com.example.goetheb1worte.presentation.quiz.components.OptionButton

@Composable
fun GrammarQuizCard(
    state: QuizUiState.Question,
    strings: AppStrings,
    onOptionSelected: (String) -> Unit,
    onToggleTranslation: () -> Unit,
    onPlayAudio: () -> Unit,
    onOpenGrammarGuide: ((String) -> Unit)? = null
) {
    val (badgeText, badgeBg, badgeFg) = when (state.gameType) {
        GameType.KASUS_TRAINER -> Triple(
            "⚖️ ${state.currentWord.category.ifBlank { strings.gameKasusTitle }}",
            Color(0xFFE8F5E9),
            Color(0xFF1B5E20)
        )
        GameType.SATZBAU_TRAINER -> Triple(
            "🧩 ${state.currentWord.category.ifBlank { strings.gameSatzbauTitle }}",
            Color(0xFFFFF3E0),
            Color(0xFFE65100)
        )
        GameType.VERB_TENSE_TRAINER -> Triple(
            "⏳ ${state.currentWord.category.ifBlank { strings.gameTensesTitle }}",
            Color(0xFFEDE7F6),
            Color(0xFF4A148C)
        )
        else -> Triple(
            state.currentWord.category,
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer
        )
    }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Category / Topic Badge
            Surface(
                shape = RoundedCornerShape(percent = 50),
                color = badgeBg
            ) {
                Text(
                    text = badgeText,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = badgeFg
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Grammar Question Prompt
            Text(
                text = state.prompt,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Target Sentence with Blank Slot
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "„${state.displayWord}“",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )

                        val isSpeaking = state.currentlySpeaking == TtsTarget.WORD
                        FilledTonalIconButton(
                            onClick = onPlayAudio,
                            modifier = Modifier.size(38.dp),
                            colors = IconButtonDefaults.filledTonalIconButtonColors(
                                containerColor = if (isSpeaking) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = strings.listenSentence,
                                tint = if (isSpeaking) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    if (!state.subText.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        FilledTonalButton(
                            onClick = onToggleTranslation,
                            shape = RoundedCornerShape(percent = 50),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(
                                imageVector = if (state.isTranslationVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (state.isTranslationVisible) strings.hideTranslation else strings.hintTranslation,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        AnimatedVisibility(
                            visible = state.isTranslationVisible,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            Column {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = state.subText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Grammatical Choices
            state.options.forEach { option ->
                val isCorrect = state.isAnswerSubmitted && option.trim().equals(state.correctOption.trim(), ignoreCase = true)
                OptionButton(
                    optionText = option,
                    isSelected = state.selectedOption == option,
                    isCorrect = isCorrect,
                    isSubmitted = state.isAnswerSubmitted,
                    onClick = { onOptionSelected(option) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Pedagogical Rule Explanation Card (Revealed on Answer Submission)
            AnimatedVisibility(
                visible = state.isAnswerSubmitted,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = strings.grammarRuleHeader,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Full completed sentence
                        Text(
                            text = strings.grammarFullSentenceHeader,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "„${state.currentWord.word}“",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = onPlayAudio,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = strings.listenSentence,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        if (!state.explanation.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                thickness = 1.dp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = state.explanation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (onOpenGrammarGuide != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            val targetTopicId = when {
                                state.currentWord.category.contains("Passiv", ignoreCase = true) -> "topic_05_passive"
                                state.currentWord.category.contains("Konjunktiv", ignoreCase = true) -> "topic_06_konjunktiv2"
                                state.currentWord.category.contains("Infinitiv", ignoreCase = true) || state.currentWord.category.contains("um...zu", ignoreCase = true) -> "topic_08_infinitives"
                                state.currentWord.category.contains("Relativ", ignoreCase = true) -> "topic_09_relatives"
                                state.currentWord.category.contains("Präposition", ignoreCase = true) || state.currentWord.category.contains("Rektion", ignoreCase = true) -> "topic_02_rektion"
                                state.currentWord.category.contains("Adjektiv", ignoreCase = true) -> "topic_03_adjectives"
                                state.currentWord.category.contains("Pronomen", ignoreCase = true) -> "topic_10_pronouns"
                                state.gameType == GameType.KASUS_TRAINER -> "topic_01_cases"
                                state.gameType == GameType.SATZBAU_TRAINER -> "topic_07_sentence_structure"
                                state.gameType == GameType.VERB_TENSE_TRAINER -> "topic_04_tenses"
                                else -> "topic_01_cases"
                            }
                            FilledTonalButton(
                                onClick = { onOpenGrammarGuide(targetTopicId) },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = strings.viewInGrammarGuide,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
