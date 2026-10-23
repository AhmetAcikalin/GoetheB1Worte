package com.example.goetheb1worte.presentation.quiz.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.goetheb1worte.localization.AppStrings
import com.example.goetheb1worte.presentation.quiz.QuizUiState
import com.example.goetheb1worte.presentation.quiz.components.OptionButton

@Composable
fun SynonymAntonymCard(
    state: QuizUiState.Question,
    strings: AppStrings,
    onOptionSelected: (String) -> Unit,
    onPlayWordAudio: () -> Unit
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
            Surface(
                shape = RoundedCornerShape(percent = 50),
                color = Color(0xFFE0F7FA)
            ) {
                Text(
                    text = "🔄 ${strings.gameSynTitle}",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF006064)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val dynamicPrompt = if (state.prompt.contains("Synonym", ignoreCase = true)) {
                strings.synonymPromptFormat.format(state.displayWord)
            } else {
                strings.antonymPromptFormat.format(state.displayWord)
            }

            Text(
                text = dynamicPrompt,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.displayWord,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onPlayWordAudio) {
                    Icon(
                        Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = strings.listenWord,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (!state.subText.isNullOrBlank()) {
                Text(
                    text = strings.meaningLabel.format(state.subText),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            if (state.isAnswerSubmitted && state.explanation != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFE8F5E9)
                ) {
                    Text(
                        text = state.explanation,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            state.options.forEach { option ->
                OptionButton(
                    optionText = option,
                    isSelected = state.selectedOption == option,
                    isCorrect = state.isAnswerSubmitted && option == state.options.firstOrNull { it == option } && (option in state.currentWord.synonyms || option in state.currentWord.antonyms),
                    isSubmitted = state.isAnswerSubmitted,
                    onClick = { onOptionSelected(option) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
