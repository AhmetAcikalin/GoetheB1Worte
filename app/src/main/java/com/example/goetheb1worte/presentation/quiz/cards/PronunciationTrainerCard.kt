package com.example.goetheb1worte.presentation.quiz.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.goetheb1worte.localization.AppStrings
import com.example.goetheb1worte.presentation.quiz.QuizUiState
import com.example.goetheb1worte.presentation.quiz.SpeechState

@Composable
fun PronunciationTrainerCard(
    state: QuizUiState.Question,
    strings: AppStrings,
    onPlayWordAudio: () -> Unit,
    onPlayExampleAudio: () -> Unit,
    onToggleTranslation: () -> Unit,
    onMicClicked: () -> Unit,
    onRetryPronunciation: () -> Unit,
    onSkipQuestion: () -> Unit,
    onPlayUserRecording: () -> Unit = {},
    isUserRecordingPlaying: Boolean = false
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
            // Badges Row
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

                Surface(
                    shape = RoundedCornerShape(percent = 50),
                    color = Color(0xFFF3E5F5)
                ) {
                    Text(
                        text = strings.gamePronounceBadge,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF6A1B9A)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Word
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = state.displayWord,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(10.dp))
                FilledIconButton(
                    onClick = onPlayWordAudio,
                    modifier = Modifier.size(44.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = strings.listenWord,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            if (!state.currentWord.plural.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Pl.: ${state.currentWord.plural}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            if (!state.subText.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = state.subText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center
                )
            }

            // Example Sentence Card
            if (state.currentWord.example.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = strings.exampleSentenceHeader,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                            IconButton(
                                onClick = onPlayExampleAudio,
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = strings.listenSentence,
                                    modifier = Modifier.size(18.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "„${state.currentWord.example}“",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (state.currentWord.exampleTranslation.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            FilledTonalButton(
                                onClick = onToggleTranslation,
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.defaultMinSize(minHeight = 32.dp)
                            ) {
                                Icon(
                                    imageVector = if (state.isTranslationVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (state.isTranslationVisible) strings.hideTranslation else strings.showTranslation,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                            AnimatedVisibility(
                                visible = state.isTranslationVisible,
                                enter = fadeIn() + expandVertically(),
                                exit = fadeOut() + shrinkVertically()
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
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

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(18.dp))

            // Speech Prompt
            Text(
                text = strings.pronouncePrompt,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            val isListening = state.speechState is SpeechState.Listening

            // Microphone Interactive Station
            if (!state.isAnswerSubmitted) {
                val infiniteTransition = rememberInfiniteTransition(label = "micPulse")
                val pulseScale by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = if (isListening) 1.25f else 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "pulseScale"
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    if (isListening) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.25f),
                            modifier = Modifier
                                .size(96.dp)
                                .scale(pulseScale)
                        ) {}
                    }

                    FilledIconButton(
                        onClick = onMicClicked,
                        modifier = Modifier.size(80.dp),
                        shape = CircleShape,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = if (isListening) MaterialTheme.colorScheme.error else Color(0xFF6A1B9A),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = if (isListening) Icons.Default.MicOff else Icons.Default.Mic,
                            contentDescription = if (isListening) strings.stopListening else strings.pronounceTapToSpeak,
                            modifier = Modifier.size(38.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (isListening) strings.pronounceListening else strings.pronounceTapToSpeak,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isListening) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onSkipQuestion,
                    modifier = Modifier.defaultMinSize(minHeight = 36.dp)
                ) {
                    Text(
                        text = strings.pronounceSkipButton,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                // Evaluation Feedback Card
                when (val speech = state.speechState) {
                    is SpeechState.Success -> {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFE8F5E9),
                            border = BorderStroke(1.dp, Color(0xFF81C784)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF2E7D32),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = strings.pronounceExcellent,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1B5E20)
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = Color(0xFFC8E6C9)
                                ) {
                                    Text(
                                        text = strings.pronounceMatchPercent.format((speech.similarity * 100).toInt()),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF1B5E20),
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                    )
                                }

                                if (speech.transcribedText.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = strings.pronounceHeardLabel.format(speech.transcribedText),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF2E7D32)
                                    )
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = if (state.hasUserRecording) onPlayUserRecording else onPlayWordAudio,
                                        modifier = Modifier
                                            .weight(1f)
                                            .defaultMinSize(minHeight = 42.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                        border = BorderStroke(1.dp, Color(0xFF2E7D32)),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF1B5E20)),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            if (state.hasUserRecording && isUserRecordingPlaying) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (state.hasUserRecording) {
                                                if (isUserRecordingPlaying) strings.pronounceStopRecordingPlayback else strings.pronounceListenRecording
                                            } else {
                                                strings.pronounceListenTarget
                                            },
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    FilledTonalButton(
                                        onClick = onRetryPronunciation,
                                        modifier = Modifier
                                            .weight(1f)
                                            .defaultMinSize(minHeight = 42.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                        colors = ButtonDefaults.filledTonalButtonColors(
                                            containerColor = Color(0xFF2E7D32),
                                            contentColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(strings.pronounceTryAgainButton, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, maxLines = 1)
                                    }
                                }
                            }
                        }
                    }

                    is SpeechState.Failure -> {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFFFF3E0),
                            border = BorderStroke(1.dp, Color(0xFFFFB74D)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = null,
                                        tint = Color(0xFFE65100),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = strings.pronounceNeedsWork,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFBF360C)
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = Color(0xFFFFE0B2)
                                ) {
                                    Text(
                                        text = strings.pronounceMatchPercent.format((speech.similarity * 100).toInt()),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFFBF360C),
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = strings.pronounceHeardLabel.format(speech.transcribedText),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFD84315)
                                )
                                Text(
                                    text = strings.pronounceExpectedLabel.format(state.displayWord),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF3E2723)
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = if (state.hasUserRecording) onPlayUserRecording else onPlayWordAudio,
                                        modifier = Modifier
                                            .weight(1f)
                                            .defaultMinSize(minHeight = 42.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                        border = BorderStroke(1.dp, Color(0xFFD84315)),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFBF360C)),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            if (state.hasUserRecording && isUserRecordingPlaying) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (state.hasUserRecording) {
                                                if (isUserRecordingPlaying) strings.pronounceStopRecordingPlayback else strings.pronounceListenRecording
                                            } else {
                                                strings.pronounceListenTarget
                                            },
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    FilledTonalButton(
                                        onClick = onRetryPronunciation,
                                        modifier = Modifier
                                            .weight(1f)
                                            .defaultMinSize(minHeight = 42.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                        colors = ButtonDefaults.filledTonalButtonColors(
                                            containerColor = Color(0xFFD84315),
                                            contentColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(strings.pronounceTryAgainButton, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, maxLines = 1)
                                    }
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}
