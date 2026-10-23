package com.example.goetheb1worte.presentation.quiz.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.goetheb1worte.audio.TtsTarget
import com.example.goetheb1worte.data.repository.GameType
import com.example.goetheb1worte.localization.AppStrings
import com.example.goetheb1worte.presentation.quiz.QuizUiState
import com.example.goetheb1worte.presentation.quiz.SpeechState
import com.example.goetheb1worte.presentation.quiz.components.OptionButton
import com.example.goetheb1worte.presentation.quiz.components.SpeechFeedbackIndicator

@Composable
fun VocabQuizCard(
    state: QuizUiState.Question,
    strings: AppStrings,
    onOptionSelected: (String) -> Unit,
    onPlayWordAudio: () -> Unit,
    onPlayExampleAudio: () -> Unit,
    onToggleTranslation: () -> Unit,
    onMicClicked: () -> Unit
) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(percent = 50),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = state.currentWord.category.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                if (state.currentWord.gender != null) {
                    val (genderBg, genderFg) = when (state.currentWord.gender.lowercase()) {
                        "der" -> Color(0xFFE8EAF6) to Color(0xFF1A237E)
                        "die" -> Color(0xFFFCE4EC) to Color(0xFF880E4F)
                        "das" -> Color(0xFFE0F2F1) to Color(0xFF004D40)
                        else -> Color(0xFFFFF8E1) to Color(0xFFE65100)
                    }
                    Surface(
                        shape = RoundedCornerShape(percent = 50),
                        color = genderBg
                    ) {
                        Text(
                            text = state.currentWord.gender,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = genderFg
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.currentWord.word,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(10.dp))

                val isWordSpeaking = state.currentlySpeaking == TtsTarget.WORD
                FilledTonalIconButton(
                    onClick = onPlayWordAudio,
                    modifier = Modifier.size(44.dp),
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = if (isWordSpeaking) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = strings.listenWord,
                        tint = if (isWordSpeaking) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            val inflectionDetails = buildString {
                if (!state.currentWord.plural.isNullOrBlank() && state.currentWord.plural != "None") {
                    append("Pl.: ${state.currentWord.plural}  ")
                }
                if (!state.currentWord.present3rdPerson.isNullOrBlank()) {
                    append("${state.currentWord.present3rdPerson}, ")
                }
                if (!state.currentWord.imperfect.isNullOrBlank()) {
                    append("${state.currentWord.imperfect}, ")
                }
                if (!state.currentWord.perfect.isNullOrBlank()) {
                    append(state.currentWord.perfect)
                }
                if (!state.currentWord.comparative.isNullOrBlank()) {
                    append("${state.currentWord.comparative}, ${state.currentWord.superlative}")
                }
            }.trim()

            if (inflectionDetails.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = inflectionDetails,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            if (state.currentWord.example.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = strings.exampleSentenceHeader,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            val isExampleSpeaking = state.currentlySpeaking == TtsTarget.EXAMPLE
                            FilledTonalIconButton(
                                onClick = onPlayExampleAudio,
                                modifier = Modifier.size(36.dp),
                                colors = IconButtonDefaults.filledTonalIconButtonColors(
                                    containerColor = if (isExampleSpeaking) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHigh
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = strings.listenSentence,
                                    tint = if (isExampleSpeaking) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "„${state.currentWord.example}“",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        if (state.currentWord.exampleTranslation.isNotBlank()) {
                            FilledTonalButton(
                                onClick = onToggleTranslation,
                                shape = RoundedCornerShape(percent = 50),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Icon(
                                    imageVector = if (state.isTranslationVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (state.isTranslationVisible) strings.hideTranslation else strings.showTranslation,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            AnimatedVisibility(
                                visible = state.isTranslationVisible,
                                enter = expandVertically() + fadeIn(),
                                exit = shrinkVertically() + fadeOut()
                            ) {
                                Column {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.surfaceContainerLowest
                                    ) {
                                        Text(
                                            text = state.currentWord.exampleTranslation,
                                            modifier = Modifier.padding(10.dp),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val isListening = state.speechState is SpeechState.Listening
            FilledTonalButton(
                onClick = onMicClicked,
                shape = RoundedCornerShape(percent = 50),
                modifier = Modifier.height(44.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (isListening) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = if (isListening) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Icon(
                    imageVector = if (isListening) Icons.Default.MicOff else Icons.Default.Mic,
                    contentDescription = strings.testPronunciation,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isListening) strings.stopListening else strings.testPronunciation,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            SpeechFeedbackIndicator(speechState = state.speechState, strings = strings)

            Spacer(modifier = Modifier.height(16.dp))

            val promptText = if (state.gameType == GameType.WEAK_WORDS) strings.weakWordsPrompt else strings.vocabQuizPrompt
            Text(
                text = promptText,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            state.options.forEach { option ->
                OptionButton(
                    optionText = option,
                    isSelected = state.selectedOption == option,
                    isCorrect = state.isAnswerSubmitted && option == state.currentWord.translation,
                    isSubmitted = state.isAnswerSubmitted,
                    onClick = { onOptionSelected(option) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
