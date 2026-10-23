package com.example.goetheb1worte.presentation.quiz.cards

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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.goetheb1worte.localization.AppStrings
import com.example.goetheb1worte.presentation.quiz.QuizUiState

@Composable
fun DerDieDasCard(
    state: QuizUiState.Question,
    strings: AppStrings,
    onOptionSelected: (String) -> Unit,
    onPlayAudio: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = RoundedCornerShape(percent = 50),
                color = Color(0xFFEDE7F6)
            ) {
                Text(
                    text = strings.derDieDasBadge,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A148C)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = strings.derDieDasPrompt,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = state.displayWord,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (!state.subText.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = state.subText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontStyle = FontStyle.Italic
                )
            }

            if (state.isAnswerSubmitted && state.explanation != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = strings.correctFormat.format(state.explanation),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(onClick = onPlayAudio, modifier = Modifier.size(32.dp)) {
                            Icon(
                                Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = strings.listenWord,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val articles = listOf("der", "die", "das")
                articles.forEach { article ->
                    val isSelected = state.selectedOption == article
                    val isCorrect = state.isAnswerSubmitted && article == state.options.firstOrNull { it == article } && article == state.currentWord.word.substring(0, 3)
                    val isWrong = state.isAnswerSubmitted && isSelected && !isCorrect

                    val (baseBg, baseFg) = when (article) {
                        "der" -> Color(0xFFE8EAF6) to Color(0xFF1A237E)
                        "die" -> Color(0xFFFCE4EC) to Color(0xFF880E4F)
                        else -> Color(0xFFE0F2F1) to Color(0xFF004D40)
                    }

                    val containerColor = when {
                        state.isAnswerSubmitted && isCorrect -> Color(0xFFD0F0C0)
                        state.isAnswerSubmitted && isWrong -> Color(0xFFFFCDD2)
                        else -> baseBg
                    }

                    val contentColor = when {
                        state.isAnswerSubmitted && isCorrect -> Color(0xFF1B5E20)
                        state.isAnswerSubmitted && isWrong -> Color(0xFFB71C1C)
                        else -> baseFg
                    }

                    OutlinedButton(
                        onClick = { onOptionSelected(article) },
                        enabled = !state.isAnswerSubmitted,
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = containerColor,
                            contentColor = contentColor,
                            disabledContainerColor = containerColor,
                            disabledContentColor = contentColor
                        ),
                        border = BorderStroke(if (isSelected || (state.isAnswerSubmitted && isCorrect)) 2.dp else 1.dp, contentColor)
                    ) {
                        Text(
                            text = article,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}
