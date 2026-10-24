package com.example.goetheb1worte.presentation.grammar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goetheb1worte.data.grammar.guide.GrammarVerbListsData
import com.example.goetheb1worte.data.grammar.guide.IrregularVerb
import com.example.goetheb1worte.data.grammar.guide.VerbWithCase
import com.example.goetheb1worte.data.grammar.guide.VerbWithPreposition

enum class VerbFilterType {
    ALL,
    IRREGULAR,
    PREPOSITION,
    DATIVE,
    ACCUSATIVE
}

@Composable
fun VerbListsView(
    onPlayAudio: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(VerbFilterType.ALL) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()

    val irregularVerbs = remember { GrammarVerbListsData.irregularVerbs }
    val prepVerbs = remember { GrammarVerbListsData.verbsWithPrepositions }
    val dativeVerbs = remember { GrammarVerbListsData.verbsWithDative }
    val accusativeVerbs = remember { GrammarVerbListsData.verbsWithAccusative }

    val filteredIrregular = remember(searchQuery, selectedFilter) {
        if (selectedFilter != VerbFilterType.ALL && selectedFilter != VerbFilterType.IRREGULAR) {
            emptyList()
        } else {
            val q = searchQuery.trim().lowercase()
            if (q.isEmpty()) irregularVerbs else irregularVerbs.filter {
                it.infinitive.lowercase().contains(q) ||
                        it.presentThirdPerson.lowercase().contains(q) ||
                        it.preteriteThirdPerson.lowercase().contains(q) ||
                        it.perfectThirdPerson.lowercase().contains(q) ||
                        it.meaningEn.lowercase().contains(q) ||
                        it.exampleSentence.lowercase().contains(q)
            }
        }
    }

    val filteredPrep = remember(searchQuery, selectedFilter) {
        if (selectedFilter != VerbFilterType.ALL && selectedFilter != VerbFilterType.PREPOSITION) {
            emptyList()
        } else {
            val q = searchQuery.trim().lowercase()
            if (q.isEmpty()) prepVerbs else prepVerbs.filter {
                it.verb.lowercase().contains(q) ||
                        it.preposition.lowercase().contains(q) ||
                        it.requiredCase.lowercase().contains(q) ||
                        it.meaningEn.lowercase().contains(q) ||
                        it.exampleSentence.lowercase().contains(q)
            }
        }
    }

    val filteredDative = remember(searchQuery, selectedFilter) {
        if (selectedFilter != VerbFilterType.ALL && selectedFilter != VerbFilterType.DATIVE) {
            emptyList()
        } else {
            val q = searchQuery.trim().lowercase()
            if (q.isEmpty()) dativeVerbs else dativeVerbs.filter {
                it.verb.lowercase().contains(q) ||
                        it.meaningEn.lowercase().contains(q) ||
                        it.exampleSentence.lowercase().contains(q)
            }
        }
    }

    val filteredAccusative = remember(searchQuery, selectedFilter) {
        if (selectedFilter != VerbFilterType.ALL && selectedFilter != VerbFilterType.ACCUSATIVE) {
            emptyList()
        } else {
            val q = searchQuery.trim().lowercase()
            if (q.isEmpty()) accusativeVerbs else accusativeVerbs.filter {
                it.verb.lowercase().contains(q) ||
                        it.meaningEn.lowercase().contains(q) ||
                        it.exampleSentence.lowercase().contains(q)
            }
        }
    }

    val totalCount = filteredIrregular.size + filteredPrep.size + filteredDative.size + filteredAccusative.size

    Column(modifier = modifier.fillMaxSize()) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            placeholder = {
                Text(
                    text = "Search verbs, prepositions or translations...",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search"
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() })
        )

        // Filter chips row
        val chipScrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(chipScrollState)
                .padding(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = selectedFilter == VerbFilterType.ALL,
                onClick = { selectedFilter = VerbFilterType.ALL },
                label = { Text("Alle (${irregularVerbs.size + prepVerbs.size + dativeVerbs.size + accusativeVerbs.size})") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            FilterChip(
                selected = selectedFilter == VerbFilterType.IRREGULAR,
                onClick = { selectedFilter = VerbFilterType.IRREGULAR },
                label = { Text("Unregelmäßig (${irregularVerbs.size})") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFE8F5E9),
                    selectedLabelColor = Color(0xFF1B5E20)
                ),
                border = if (selectedFilter == VerbFilterType.IRREGULAR) BorderStroke(1.dp, Color(0xFFA5D6A7)) else null
            )
            FilterChip(
                selected = selectedFilter == VerbFilterType.PREPOSITION,
                onClick = { selectedFilter = VerbFilterType.PREPOSITION },
                label = { Text("Mit Präposition (${prepVerbs.size})") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFE3F2FD),
                    selectedLabelColor = Color(0xFF0D47A1)
                ),
                border = if (selectedFilter == VerbFilterType.PREPOSITION) BorderStroke(1.dp, Color(0xFF90CAF9)) else null
            )
            FilterChip(
                selected = selectedFilter == VerbFilterType.DATIVE,
                onClick = { selectedFilter = VerbFilterType.DATIVE },
                label = { Text("Dativ (${dativeVerbs.size})") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFFFF3E0),
                    selectedLabelColor = Color(0xFFE65100)
                ),
                border = if (selectedFilter == VerbFilterType.DATIVE) BorderStroke(1.dp, Color(0xFFFFCC80)) else null
            )
            FilterChip(
                selected = selectedFilter == VerbFilterType.ACCUSATIVE,
                onClick = { selectedFilter = VerbFilterType.ACCUSATIVE },
                label = { Text("Akkusativ (${accusativeVerbs.size})") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFF3E5F5),
                    selectedLabelColor = Color(0xFF4A148C)
                ),
                border = if (selectedFilter == VerbFilterType.ACCUSATIVE) BorderStroke(1.dp, Color(0xFFCE93D8)) else null
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Verb list
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Irregular Verbs section
            if (filteredIrregular.isNotEmpty()) {
                if (selectedFilter == VerbFilterType.ALL) {
                    item(key = "hdr_irregular") {
                        SectionHeader(
                            title = "Unregelmäßige Verben",
                            count = filteredIrregular.size,
                            containerColor = Color(0xFFE8F5E9),
                            contentColor = Color(0xFF1B5E20)
                        )
                    }
                }
                items(filteredIrregular, key = { "irr_${it.infinitive}" }) { verb ->
                    IrregularVerbCard(verb = verb, onPlayAudio = onPlayAudio)
                }
            }

            // Preposition Verbs section
            if (filteredPrep.isNotEmpty()) {
                if (selectedFilter == VerbFilterType.ALL) {
                    item(key = "hdr_prep") {
                        SectionHeader(
                            title = "Verben mit Präpositionen",
                            count = filteredPrep.size,
                            containerColor = Color(0xFFE3F2FD),
                            contentColor = Color(0xFF0D47A1)
                        )
                    }
                }
                items(filteredPrep, key = { "prep_${it.verb}_${it.preposition}" }) { verb ->
                    PrepVerbCard(verb = verb, onPlayAudio = onPlayAudio)
                }
            }

            // Dative Verbs section
            if (filteredDative.isNotEmpty()) {
                if (selectedFilter == VerbFilterType.ALL) {
                    item(key = "hdr_dat") {
                        SectionHeader(
                            title = "Verben mit Dativ",
                            count = filteredDative.size,
                            containerColor = Color(0xFFFFF3E0),
                            contentColor = Color(0xFFE65100)
                        )
                    }
                }
                items(filteredDative, key = { "dat_${it.verb}" }) { verb ->
                    CaseVerbCard(verb = verb, isDative = true, onPlayAudio = onPlayAudio)
                }
            }

            // Accusative Verbs section
            if (filteredAccusative.isNotEmpty()) {
                if (selectedFilter == VerbFilterType.ALL) {
                    item(key = "hdr_akk") {
                        SectionHeader(
                            title = "Verben mit Akkusativ",
                            count = filteredAccusative.size,
                            containerColor = Color(0xFFF3E5F5),
                            contentColor = Color(0xFF4A148C)
                        )
                    }
                }
                items(filteredAccusative, key = { "akk_${it.verb}" }) { verb ->
                    CaseVerbCard(verb = verb, isDative = false, onPlayAudio = onPlayAudio)
                }
            }

            if (totalCount == 0) {
                item(key = "empty_state") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No verbs found matching \"$searchQuery\"",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    count: Int,
    containerColor: Color,
    contentColor: Color
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = containerColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            Surface(
                shape = CircleShape,
                color = contentColor.copy(alpha = 0.15f)
            ) {
                Text(
                    text = "$count",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            }
        }
    }
}

@Composable
private fun IrregularVerbCard(
    verb: IrregularVerb,
    onPlayAudio: (String) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header row: Verb + Translation + Audio
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = verb.infinitive,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (verb.meaningEn.isNotBlank()) {
                        Text(
                            text = "• ${verb.meaningEn}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.outline,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                IconButton(
                    onClick = { onPlayAudio(verb.infinitive) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Pronounce ${verb.infinitive}",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Conjugation row: Präsens, Präteritum, Perfekt
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ConjugationPill(
                    label = "Präsens",
                    value = verb.presentThirdPerson,
                    modifier = Modifier.weight(1f)
                )
                ConjugationPill(
                    label = "Präteritum",
                    value = verb.preteriteThirdPerson,
                    modifier = Modifier.weight(1f)
                )
                val perfectDisplay = if (verb.perfectThirdPerson.startsWith("hat ") || verb.perfectThirdPerson.startsWith("ist ")) {
                    verb.perfectThirdPerson
                } else {
                    "${verb.auxiliary} ${verb.perfectThirdPerson}"
                }
                ConjugationPill(
                    label = "Perfekt",
                    value = perfectDisplay,
                    highlightWord = verb.auxiliary,
                    modifier = Modifier.weight(1.2f)
                )
            }

            // Example sentence (if present)
            if (verb.exampleSentence.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "„${verb.exampleSentence}“",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { onPlayAudio(verb.exampleSentence) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Pronounce example",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PrepVerbCard(
    verb: VerbWithPreposition,
    onPlayAudio: (String) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header row: Verb + Preposition + Case badge + Audio
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "${verb.verb} ${verb.preposition}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    // Case badge
                    val isAkk = verb.requiredCase.contains("Akkusativ", ignoreCase = true)
                    val badgeContainer = if (isAkk) Color(0xFFE1F5FE) else Color(0xFFFFF3E0)
                    val badgeContent = if (isAkk) Color(0xFF0277BD) else Color(0xFFE65100)
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = badgeContainer
                    ) {
                        Text(
                            text = "+ ${verb.requiredCase}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = badgeContent
                        )
                    }
                }
                IconButton(
                    onClick = { onPlayAudio("${verb.verb} ${verb.preposition}") },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Pronounce",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            if (verb.meaningEn.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = verb.meaningEn,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            if (verb.exampleSentence.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "„${verb.exampleSentence}“",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { onPlayAudio(verb.exampleSentence) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Pronounce example",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CaseVerbCard(
    verb: VerbWithCase,
    isDative: Boolean,
    onPlayAudio: (String) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header row: Verb + Case badge + Audio
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = verb.verb,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isDative) Color(0xFFFFF3E0) else Color(0xFFF3E5F5)
                    ) {
                        Text(
                            text = "+ ${if (isDative) "Dativ" else "Akkusativ"}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isDative) Color(0xFFE65100) else Color(0xFF4A148C)
                        )
                    }
                }
                IconButton(
                    onClick = { onPlayAudio(verb.verb) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Pronounce",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            if (verb.meaningEn.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = verb.meaningEn,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            if (verb.exampleSentence.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "„${verb.exampleSentence}“",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { onPlayAudio(verb.exampleSentence) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Pronounce example",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ConjugationPill(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    highlightWord: String? = null
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
