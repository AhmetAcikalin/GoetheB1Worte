package com.example.goetheb1worte.presentation.grammar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goetheb1worte.data.grammar.guide.CefrLevel
import com.example.goetheb1worte.data.grammar.guide.ExamPriority
import com.example.goetheb1worte.data.grammar.guide.GrammarExample
import com.example.goetheb1worte.data.grammar.guide.GrammarGuideData
import com.example.goetheb1worte.data.grammar.guide.GrammarGuideSubtopic
import com.example.goetheb1worte.data.grammar.guide.GrammarGuideTopic
import com.example.goetheb1worte.data.grammar.guide.GrammarReferenceRegistry
import com.example.goetheb1worte.data.grammar.guide.GrammarTable
import com.example.goetheb1worte.data.repository.GameType
import com.example.goetheb1worte.localization.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrammarGuideScreen(
    initialTopicId: String? = null,
    initialLevel: CefrLevel? = null,
    strings: AppStrings,
    onClose: () -> Unit,
    onPracticeTopic: (GameType) -> Unit,
    onStartInteractiveQuiz: (CefrLevel, String?) -> Unit = { _, _ -> },
    onPlayAudio: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTopicId by remember { mutableStateOf(initialTopicId) }
    var selectedLevel by remember { mutableStateOf<CefrLevel?>(initialLevel) }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Sync if initialTopicId changes externally
    LaunchedEffect(initialTopicId) {
        if (initialTopicId != null) {
            selectedTopicId = initialTopicId
            val topic = GrammarGuideData.getTopicById(initialTopicId)
            if (topic != null) {
                selectedLevel = topic.level
            }
        }
    }

    // Handle back button: if viewing a specific topic or searching or filtering, step back gracefully
    BackHandler {
        when {
            selectedTopicId != null -> selectedTopicId = null
            searchQuery.isNotBlank() -> searchQuery = ""
            selectedLevel != null -> selectedLevel = null
            else -> onClose()
        }
    }

    val displayedTopics = remember(searchQuery, selectedLevel) {
        GrammarGuideData.search(searchQuery, selectedLevel)
    }

    val topicsInLevel = remember(selectedLevel) {
        GrammarGuideData.getTopicsByLevel(selectedLevel)
    }

    val currentTopic = remember(selectedTopicId) {
        selectedTopicId?.let { id -> GrammarGuideData.getTopicById(id) }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = when {
                                currentTopic != null -> currentTopic.title
                                selectedLevel == CefrLevel.A1 -> strings.levelA1
                                selectedLevel == CefrLevel.A2 -> strings.levelA2
                                selectedLevel == CefrLevel.B1 -> strings.levelB1
                                else -> strings.grammarGuideMainTitle
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (currentTopic != null) currentTopic.subtitle else strings.grammarGuideSubtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (selectedTopicId != null) {
                            selectedTopicId = null
                        } else if (searchQuery.isNotBlank()) {
                            searchQuery = ""
                        } else if (selectedLevel != null) {
                            selectedLevel = null
                        } else {
                            onClose()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onStartInteractiveQuiz(selectedLevel ?: currentTopic?.level ?: CefrLevel.A1, currentTopic?.id)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = "Grammar Quiz",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (currentTopic != null) {
                TopicDetailView(
                    topic = currentTopic,
                    strings = strings,
                    onBackToList = { selectedTopicId = null },
                    onPracticeTopic = onPracticeTopic,
                    onStartInteractiveQuiz = onStartInteractiveQuiz,
                    onPlayAudio = onPlayAudio
                )
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            if (it.isNotBlank()) {
                                selectedTopicId = null
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        placeholder = {
                            Text(
                                text = strings.grammarGuideSearchHint,
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

                    // CEFR Level Filter Row (All, A1, A2, B1) with topic counts
                    val allCount = remember { GrammarGuideData.topics.size }
                    val a1Count = remember { GrammarGuideData.a1Topics.size }
                    val a2Count = remember { GrammarGuideData.a2Topics.size }
                    val b1Count = remember { GrammarGuideData.b1Topics.size }
                    val levelFilterScrollState = rememberScrollState()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(levelFilterScrollState)
                            .padding(horizontal = 16.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilterChip(
                            selected = selectedLevel == null,
                            onClick = {
                                selectedLevel = null
                                selectedTopicId = null
                            },
                            label = {
                                Text(
                                    "${strings.levelAll.substringBefore(' ')} ($allCount)",
                                    fontWeight = if (selectedLevel == null) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                        FilterChip(
                            selected = selectedLevel == CefrLevel.A1,
                            onClick = {
                                selectedLevel = if (selectedLevel == CefrLevel.A1) null else CefrLevel.A1
                                selectedTopicId = null
                            },
                            label = {
                                Text(
                                    "A1 ($a1Count)",
                                    fontWeight = if (selectedLevel == CefrLevel.A1) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFE8F5E9),
                                selectedLabelColor = Color(0xFF1B5E20)
                            ),
                            border = if (selectedLevel == CefrLevel.A1) BorderStroke(1.dp, Color(0xFFA5D6A7)) else null
                        )
                        FilterChip(
                            selected = selectedLevel == CefrLevel.A2,
                            onClick = {
                                selectedLevel = if (selectedLevel == CefrLevel.A2) null else CefrLevel.A2
                                selectedTopicId = null
                            },
                            label = {
                                Text(
                                    "A2 ($a2Count)",
                                    fontWeight = if (selectedLevel == CefrLevel.A2) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFE3F2FD),
                                selectedLabelColor = Color(0xFF0D47A1)
                            ),
                            border = if (selectedLevel == CefrLevel.A2) BorderStroke(1.dp, Color(0xFF90CAF9)) else null
                        )
                        FilterChip(
                            selected = selectedLevel == CefrLevel.B1,
                            onClick = {
                                selectedLevel = if (selectedLevel == CefrLevel.B1) null else CefrLevel.B1
                                selectedTopicId = null
                            },
                            label = {
                                Text(
                                    "B1 ($b1Count)",
                                    fontWeight = if (selectedLevel == CefrLevel.B1) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFEDE7F6),
                                selectedLabelColor = Color(0xFF4A148C)
                            ),
                            border = if (selectedLevel == CefrLevel.B1) BorderStroke(1.dp, Color(0xFFB39DDB)) else null
                        )
                    }

                    // Horizontal Topic Chips Carousel for Current Level
                    val topicChipsScrollState = rememberScrollState()
                    LaunchedEffect(selectedLevel) {
                        topicChipsScrollState.scrollTo(0)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(topicChipsScrollState)
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // "All Topics" Chip
                        FilterChip(
                            selected = selectedTopicId == null,
                            onClick = {
                                selectedTopicId = null
                            },
                            label = { Text(strings.allTopicsChip) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )

                        // Individual Topic Chips
                        topicsInLevel.forEach { topic ->
                            val isSelected = selectedTopicId == topic.id
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedTopicId = if (isSelected) null else topic.id
                                },
                                label = {
                                    Text("${topic.iconEmoji} ${topic.title.substringBefore('&').substringBefore('(').trim()}")
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    TopicListView(
                        topics = displayedTopics,
                        strings = strings,
                        showProgressionBanner = selectedLevel == null && searchQuery.isBlank(),
                        onSelectLevel = { selectedLevel = it },
                        onSelectTopic = { topic -> selectedTopicId = topic.id },
                        onPracticeTopic = onPracticeTopic,
                        onStartInteractiveQuiz = onStartInteractiveQuiz
                    )
                }
            }
        }
    }
}

@Composable
private fun TopicListView(
    topics: List<GrammarGuideTopic>,
    strings: AppStrings,
    showProgressionBanner: Boolean,
    onSelectLevel: (CefrLevel) -> Unit,
    onSelectTopic: (GrammarGuideTopic) -> Unit,
    onPracticeTopic: (GameType) -> Unit,
    onStartInteractiveQuiz: (CefrLevel, String?) -> Unit
) {
    if (topics.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = strings.noGrammarResults,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center
            )
        }
        return
    }

    val listState = rememberLazyListState()

    // Reset scroll to top whenever the topic list or banner visibility changes
    LaunchedEffect(topics.firstOrNull()?.id, showProgressionBanner) {
        listState.scrollToItem(0)
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (showProgressionBanner) {
            item(key = "cefr_progression_banner") {
                CefrProgressionBanner(
                    strings = strings,
                    onSelectLevel = onSelectLevel
                )
            }
        }

        item(key = "interactive_quiz_banner") {
            ElevatedCard(
                onClick = { onStartInteractiveQuiz(topics.firstOrNull()?.level ?: CefrLevel.A1, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("🎯", fontSize = 20.sp)
                            Text(
                                text = "Interactive Grammar Quiz",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Multiple Choice • Fill in the Blank • Sentence Word Ordering (A1, A2, B1)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    FilledTonalButton(
                        onClick = { onStartInteractiveQuiz(topics.firstOrNull()?.level ?: CefrLevel.A1, null) },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Practice", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        items(topics, key = { it.id }) { topic ->
            TopicOverviewCard(
                topic = topic,
                strings = strings,
                onClick = { onSelectTopic(topic) },
                onPractice = topic.targetGameType?.let { mode -> { onPracticeTopic(mode) } }
            )
        }
    }
}

@Composable
private fun CefrProgressionBanner(
    strings: AppStrings,
    onSelectLevel: (CefrLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "📈", fontSize = 22.sp)
                    }
                }
                Column {
                    Text(
                        text = strings.cefrProgressionTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = strings.cefrProgressionDesc,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // A1 Level Card
                Surface(
                    onClick = { onSelectLevel(CefrLevel.A1) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFE8F5E9),
                    border = BorderStroke(1.dp, Color(0xFFA5D6A7))
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "A1",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1B5E20),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Foundations",
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2E7D32),
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // A2 Level Card
                Surface(
                    onClick = { onSelectLevel(CefrLevel.A2) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFE3F2FD),
                    border = BorderStroke(1.dp, Color(0xFF90CAF9))
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "A2",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0D47A1),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Expansion",
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF1565C0),
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // B1 Level Card
                Surface(
                    onClick = { onSelectLevel(CefrLevel.B1) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFEDE7F6),
                    border = BorderStroke(1.dp, Color(0xFFB39DDB))
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "B1",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF4A148C),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Mastery",
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF6A1B9A),
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopicOverviewCard(
    topic: GrammarGuideTopic,
    strings: AppStrings,
    onClick: () -> Unit,
    onPractice: (() -> Unit)?
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Row 1: Top metadata row with Emoji + LevelBadge on Left, PriorityBadge on Right
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = topic.iconEmoji, fontSize = 20.sp)
                        }
                    }
                    LevelBadge(level = topic.level, strings = strings)
                }

                PriorityBadge(priority = topic.priority, strings = strings)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Row 2: Full-width Title and Subtitle
            Text(
                text = topic.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (topic.subtitle.isNotBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = topic.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = topic.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerHigh
                ) {
                    Text(
                        text = "${topic.subtopics.size} modules • ${topic.examTraps.size} exam tips",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (onPractice != null) {
                    FilledTonalButton(
                        onClick = onPractice,
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = strings.practiceTopicButton,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopicDetailView(
    topic: GrammarGuideTopic,
    strings: AppStrings,
    onBackToList: () -> Unit,
    onPracticeTopic: (GameType) -> Unit,
    onStartInteractiveQuiz: (CefrLevel, String?) -> Unit,
    onPlayAudio: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    val rules = remember(topic.id) {
        topic.rules.ifEmpty { GrammarReferenceRegistry.getRulesForTopic(topic.id, topic.summary) }
    }
    val tips = remember(topic.id) {
        topic.tips.ifEmpty { GrammarReferenceRegistry.getTipsForTopic(topic.id) }
    }
    val exceptions = remember(topic.id) {
        topic.exceptions.ifEmpty { GrammarReferenceRegistry.getExceptionsForTopic(topic.id) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp, bottom = 32.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Topic Header Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Top metadata row with Emoji + LevelBadge on Left, PriorityBadge on Right
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = topic.iconEmoji, fontSize = 24.sp)
                            }
                        }
                        LevelBadge(level = topic.level, strings = strings)
                    }

                    PriorityBadge(priority = topic.priority, strings = strings)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = topic.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = topic.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = topic.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (topic.targetGameType != null) {
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTonalButton(
                        onClick = { onPracticeTopic(topic.targetGameType) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = strings.practiceTopicButton,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Interactive Quiz CTA Button
        Button(
            onClick = { onStartInteractiveQuiz(topic.level, topic.id) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Interactive Quiz: ${topic.title}", fontWeight = FontWeight.Bold)
        }

        // Rules Section Card
        RulesCard(rules = rules)

        // Tips Section Card
        TipsCard(tips = tips)

        // Exceptions Section Card
        ExceptionsCard(exceptions = exceptions)

        // Subtopic Cards
        topic.subtopics.forEachIndexed { index, subtopic ->
            SubtopicCard(
                index = index + 1,
                subtopic = subtopic,
                strings = strings,
                onPlayAudio = onPlayAudio
            )
        }

        // Exam Traps Callout Card
        if (topic.examTraps.isNotEmpty()) {
            ExamTrapsCard(
                traps = topic.examTraps,
                strings = strings
            )
        }

        // Bottom Navigation Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onBackToList,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(strings.allTopicsChip)
            }

            Button(
                onClick = { onStartInteractiveQuiz(topic.level, topic.id) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Start Quiz",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun SubtopicCard(
    index: Int,
    subtopic: GrammarGuideSubtopic,
    strings: AppStrings,
    onPlayAudio: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Subtopic Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "$index",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Text(
                    text = subtopic.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Explanation formatted
            Text(
                text = subtopic.explanation,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Table (if present)
            if (subtopic.table != null) {
                Spacer(modifier = Modifier.height(14.dp))
                GrammarTableView(table = subtopic.table, strings = strings)
            }

            // Examples
            if (subtopic.examples.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Beispiele (Examples):",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                subtopic.examples.forEach { example ->
                    ExampleItem(
                        example = example,
                        onPlayAudio = { onPlayAudio(example.german) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

private fun calculateColumnWidth(header: String, cellValues: List<String>, totalColumns: Int): Dp {
    val maxCellLength = (cellValues + header).maxOfOrNull { it.length } ?: 10
    val isExampleCol = header.contains("Beispiel", ignoreCase = true) ||
            header.contains("Example", ignoreCase = true) ||
            header.contains("Satz", ignoreCase = true) ||
            header.contains("Sentence", ignoreCase = true)
    val isMeaningCol = header.contains("Bedeutung", ignoreCase = true) ||
            header.contains("Meaning", ignoreCase = true) ||
            header.contains("Funktion", ignoreCase = true) ||
            header.contains("Function", ignoreCase = true)
    return when {
        isExampleCol -> if (totalColumns <= 3) 190.dp else 240.dp
        isMeaningCol -> 135.dp
        maxCellLength > 28 -> 200.dp
        maxCellLength > 16 -> 140.dp
        maxCellLength <= 6 -> 76.dp
        maxCellLength <= 10 -> 90.dp
        else -> 110.dp
    }
}

@Composable
private fun GrammarTableView(
    table: GrammarTable,
    strings: AppStrings
) {
    val scrollState = rememberScrollState()

    val columnWidths = remember(table) {
        table.headers.indices.map { colIndex ->
            val header = table.headers[colIndex]
            val cellValues = table.rows.mapNotNull { it.getOrNull(colIndex) }
            calculateColumnWidth(header, cellValues, table.headers.size)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        if (table.headers.size > 2) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Text(
                    text = "↔",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = strings.tableScrollHint,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) {
            Column {
                // Table Header Row
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    table.headers.forEachIndexed { colIdx, header ->
                        val width = columnWidths[colIdx]
                        val align = if (width <= 85.dp) TextAlign.Center else TextAlign.Start
                        Text(
                            text = header,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = align,
                            modifier = Modifier
                                .width(width)
                                .padding(horizontal = 8.dp)
                        )
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))

                // Table Data Rows
                table.rows.forEachIndexed { rowIndex, row ->
                    val rowBg = if (rowIndex % 2 == 1) {
                        MaterialTheme.colorScheme.surfaceContainerLowest
                    } else {
                        MaterialTheme.colorScheme.surface
                    }

                    Row(
                        modifier = Modifier
                            .background(rowBg)
                            .padding(vertical = 9.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        row.forEachIndexed { colIndex, cell ->
                            val isFirstCol = colIndex == 0
                            val width = columnWidths.getOrElse(colIndex) { 110.dp }
                            val align = if (width <= 85.dp) TextAlign.Center else TextAlign.Start
                            Text(
                                text = cell,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = if (isFirstCol) FontWeight.Bold else FontWeight.Normal,
                                color = if (isFirstCol) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                },
                                textAlign = align,
                                modifier = Modifier
                                    .width(width)
                                    .padding(horizontal = 8.dp)
                            )
                        }
                    }

                    if (rowIndex < table.rows.size - 1) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExampleItem(
    example: GrammarExample,
    onPlayAudio: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Highlighted German Sentence
                val primaryColor = MaterialTheme.colorScheme.primary
                val annotatedString = remember(example.german, example.highlightWord, primaryColor) {
                    buildAnnotatedString {
                        val full = example.german
                        val tokens = example.highlightWord
                            .split(Regex("""\s*\.\.\.\s*|\s*/\s*"""))
                            .map { it.trim().trim('?', '.', '!', ',') }
                            .filter { it.isNotBlank() }

                        if (tokens.isEmpty()) {
                            withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                                append(full)
                            }
                        } else {
                            val ranges = mutableListOf<IntRange>()
                            var searchStart = 0
                            for (tok in tokens) {
                                val idx = full.indexOf(tok, startIndex = searchStart, ignoreCase = true)
                                if (idx >= 0) {
                                    ranges.add(idx until (idx + tok.length))
                                    searchStart = idx + tok.length
                                }
                            }

                            if (ranges.isEmpty()) {
                                withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                                append(full)
                                }
                            } else {
                                var curr = 0
                                for (range in ranges) {
                                    if (range.first > curr) {
                                        withStyle(SpanStyle(fontWeight = FontWeight.Normal)) {
                                            append(full.substring(curr, range.first))
                                        }
                                    }
                                    withStyle(
                                        SpanStyle(
                                            fontWeight = FontWeight.ExtraBold,
                                            color = primaryColor
                                        )
                                    ) {
                                        append(full.substring(range.first, range.last + 1))
                                    }
                                    curr = range.last + 1
                                }
                                if (curr < full.length) {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Normal)) {
                                        append(full.substring(curr))
                                    }
                                }
                            }
                        }
                    }
                }

                Text(
                    text = annotatedString,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(3.dp))

                // English Translation
                Text(
                    text = example.english,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
                    fontStyle = FontStyle.Italic
                )

                // Note (if present)
                if (!example.note.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = "💡 ${example.note}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Native TTS Audio Button
            FilledTonalIconButton(
                onClick = onPlayAudio,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(36.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = "Play pronunciation",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun ExamTrapsCard(
    traps: List<String>,
    strings: AppStrings
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.35f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.WarningAmber,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = strings.examTrapsHeader,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            traps.forEach { trap ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = trap,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

private data class BadgeColors(
    val bg: Color,
    val fg: Color,
    val border: Color
)

@Composable
private fun PriorityBadge(
    priority: ExamPriority,
    strings: AppStrings
) {
    val (text, badgeColors) = when (priority) {
        ExamPriority.HIGH_FREQUENCY -> Pair(
            strings.examPriorityHigh,
            BadgeColors(Color(0xFFFFEBEE), Color(0xFFC62828), Color(0xFFFFCDD2))
        )
        ExamPriority.CORE_B1 -> Pair(
            strings.examPriorityCore,
            BadgeColors(Color(0xFFEDE7F6), Color(0xFF4A148C), Color(0xFFD1C4E9))
        )
        ExamPriority.REVIEW_A2 -> Pair(
            strings.examPriorityReview,
            BadgeColors(Color(0xFFE3F2FD), Color(0xFF0D47A1), Color(0xFFBBDEFB))
        )
        ExamPriority.BORDERLINE_B2 -> Pair(
            strings.examPriorityBorderline,
            BadgeColors(Color(0xFFFFF3E0), Color(0xFFE65100), Color(0xFFFFE0B2))
        )
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = badgeColors.bg,
        border = BorderStroke(1.dp, badgeColors.border)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = badgeColors.fg
        )
    }
}

@Composable
private fun LevelBadge(
    level: CefrLevel,
    strings: AppStrings
) {
    val (text, badgeColors) = when (level) {
        CefrLevel.A1 -> Pair("A1", BadgeColors(Color(0xFFE8F5E9), Color(0xFF1B5E20), Color(0xFFA5D6A7)))
        CefrLevel.A2 -> Pair("A2", BadgeColors(Color(0xFFE3F2FD), Color(0xFF0D47A1), Color(0xFF90CAF9)))
        CefrLevel.B1 -> Pair("B1", BadgeColors(Color(0xFFEDE7F6), Color(0xFF4A148C), Color(0xFFB39DDB)))
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = badgeColors.bg,
        border = BorderStroke(1.dp, badgeColors.border)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraBold,
            color = badgeColors.fg
        )
    }
}

@Composable
private fun RulesCard(rules: List<String>) {
    if (rules.isEmpty()) return
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("📜", fontSize = 20.sp)
                Text(
                    text = "Grammar Rules (Regeln)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            rules.forEachIndexed { i, rule ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${i + 1}.",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = rule,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun TipsCard(tips: List<String>) {
    if (tips.isEmpty()) return
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
        border = BorderStroke(1.dp, Color(0xFFFFD54F)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("💡", fontSize = 20.sp)
                Text(
                    text = "Study Tips & Mnemonics (Tipps)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100)
                )
            }
            tips.forEach { tip ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("•", fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                    Text(
                        text = tip,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF4E342E)
                    )
                }
            }
        }
    }
}

@Composable
private fun ExceptionsCard(exceptions: List<String>) {
    if (exceptions.isEmpty()) return
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
        border = BorderStroke(1.dp, Color(0xFFEF9A9A)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("⚠️", fontSize = 20.sp)
                Text(
                    text = "Exceptions & Irregularities (Ausnahmen)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC62828)
                )
            }
            exceptions.forEach { exc ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("•", fontWeight = FontWeight.Bold, color = Color(0xFFC62828))
                    Text(
                        text = exc,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF37474F)
                    )
                }
            }
        }
    }
}

