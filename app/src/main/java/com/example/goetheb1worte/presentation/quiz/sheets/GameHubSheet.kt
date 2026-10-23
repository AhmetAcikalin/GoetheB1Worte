package com.example.goetheb1worte.presentation.quiz.sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goetheb1worte.data.repository.GameType
import com.example.goetheb1worte.localization.AppStrings
import com.example.goetheb1worte.presentation.quiz.QuestionCount

@Composable
fun GameHubSheetContent(
    selectedCount: QuestionCount,
    strings: AppStrings,
    onSelectCount: (QuestionCount) -> Unit,
    onSelectGameMode: (GameType) -> Unit,
    onOpenGrammarGuide: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
            .padding(horizontal = 20.dp)
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = strings.gameHubTitle,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = strings.gameHubSubtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(12.dp))

        // B1 Grammar Guide Banner Card
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOpenGrammarGuide() }
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "📚", fontSize = 20.sp)
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = strings.learnGrammarBannerTitle,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = strings.learnGrammarBannerDesc,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = strings.questionsPerRoundHeader,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            QuestionCount.entries.forEach { countOption ->
                val isSelected = selectedCount == countOption
                val label = if (countOption == QuestionCount.UNLIMITED) strings.countUnlimited else countOption.label
                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectCount(countOption) },
                    label = {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    shape = RoundedCornerShape(percent = 50),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                GameModeCard(
                    title = strings.gameVocabTitle,
                    subtitle = strings.gameVocabSubtitle,
                    description = strings.gameVocabDesc,
                    badge = strings.gameVocabBadge,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    onColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    icon = Icons.Default.AutoAwesome,
                    onClick = { onSelectGameMode(GameType.VOCAB_QUIZ) }
                )
            }
            item {
                GameModeCard(
                    title = strings.gameKasusTitle,
                    subtitle = strings.gameKasusSubtitle,
                    description = strings.gameKasusDesc,
                    badge = strings.gameKasusBadge,
                    color = Color(0xFFE8F5E9),
                    onColor = Color(0xFF1B5E20),
                    icon = Icons.Default.Balance,
                    onClick = { onSelectGameMode(GameType.KASUS_TRAINER) }
                )
            }
            item {
                GameModeCard(
                    title = strings.gameSatzbauTitle,
                    subtitle = strings.gameSatzbauSubtitle,
                    description = strings.gameSatzbauDesc,
                    badge = strings.gameSatzbauBadge,
                    color = Color(0xFFFFF3E0),
                    onColor = Color(0xFFE65100),
                    icon = Icons.Default.AccountTree,
                    onClick = { onSelectGameMode(GameType.SATZBAU_TRAINER) }
                )
            }
            item {
                GameModeCard(
                    title = strings.gameTensesTitle,
                    subtitle = strings.gameTensesSubtitle,
                    description = strings.gameTensesDesc,
                    badge = strings.gameTensesBadge,
                    color = Color(0xFFEDE7F6),
                    onColor = Color(0xFF4A148C),
                    icon = Icons.Default.Schedule,
                    onClick = { onSelectGameMode(GameType.VERB_TENSE_TRAINER) }
                )
            }
            item {
                GameModeCard(
                    title = strings.gameDddTitle,
                    subtitle = strings.gameDddSubtitle,
                    description = strings.gameDddDesc,
                    badge = strings.gameDddBadge,
                    color = Color(0xFFEDE7F6),
                    onColor = Color(0xFF4A148C),
                    icon = Icons.Default.Casino,
                    onClick = { onSelectGameMode(GameType.DER_DIE_DAS) }
                )
            }
            item {
                GameModeCard(
                    title = strings.gameSynTitle,
                    subtitle = strings.gameSynSubtitle,
                    description = strings.gameSynDesc,
                    badge = strings.gameSynBadge,
                    color = Color(0xFFE0F7FA),
                    onColor = Color(0xFF006064),
                    icon = Icons.Default.SyncAlt,
                    onClick = { onSelectGameMode(GameType.SYNONYMS_ANTONYMS) }
                )
            }
            item {
                GameModeCard(
                    title = strings.gameClozeTitle,
                    subtitle = strings.gameClozeSubtitle,
                    description = strings.gameClozeDesc,
                    badge = strings.gameClozeBadge,
                    color = Color(0xFFFFF3E0),
                    onColor = Color(0xFFE65100),
                    icon = Icons.Default.EditNote,
                    onClick = { onSelectGameMode(GameType.SENTENCE_CLOZE) }
                )
            }
            item {
                GameModeCard(
                    title = strings.gamePronounceTitle,
                    subtitle = strings.gamePronounceSubtitle,
                    description = strings.gamePronounceDesc,
                    badge = strings.gamePronounceBadge,
                    color = Color(0xFFF3E5F5),
                    onColor = Color(0xFF6A1B9A),
                    icon = Icons.Default.Mic,
                    onClick = { onSelectGameMode(GameType.PRONUNCIATION_TRAINER) }
                )
            }
            item {
                GameModeCard(
                    title = strings.gameWeakTitle,
                    subtitle = strings.gameWeakSubtitle,
                    description = strings.gameWeakDesc,
                    badge = strings.gameWeakBadge,
                    color = MaterialTheme.colorScheme.errorContainer,
                    onColor = MaterialTheme.colorScheme.onErrorContainer,
                    icon = Icons.Default.School,
                    onClick = { onSelectGameMode(GameType.WEAK_WORDS) }
                )
            }
        }
    }
}

@Composable
fun GameModeCard(
    title: String,
    subtitle: String,
    description: String,
    badge: String,
    color: Color,
    onColor: Color,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = color
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = onColor.copy(alpha = 0.15f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = onColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = onColor
                    )
                    Surface(
                        shape = RoundedCornerShape(percent = 50),
                        color = onColor.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = badge,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = onColor
                        )
                    }
                }
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Medium,
                    color = onColor.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = onColor.copy(alpha = 0.75f)
                )
            }
        }
    }
}
