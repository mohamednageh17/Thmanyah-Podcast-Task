package com.example.thmanyah_podcast_task.ui.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.components.chips.FilterChip
import com.example.core.designsystem.components.images.AppAsyncImage
import com.example.core.designsystem.components.images.AppImageShape
import com.example.core.designsystem.components.states.ErrorState
import com.example.core.designsystem.components.states.ErrorType
import com.example.core.designsystem.components.states.LoadingState
import com.example.core.designsystem.components.states.LoadingStateType
import com.example.domain.models.SectionType
import com.example.domain.models.Sections
import com.example.thmanyah_podcast_task.ui.home.components.HorizontalPodcastSection
import com.example.thmanyah_podcast_task.ui.home.components.LargeFeaturedSection
import com.example.thmanyah_podcast_task.ui.home.components.PodcastGridSection
import com.example.thmanyah_podcast_task.ui.home.components.QueueSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            uiState.isLoading -> {
                LoadingState(
                    message = "جاري التحميل...",
                    type = LoadingStateType.ShimmerHome
                )
            }
            uiState.error != null -> {
                ErrorState(
                    title = "حدث خطأ",
                    message = uiState.error!!,
                    errorType = ErrorType.Network,
                    onRetry = viewModel::retry,
                    retryButtonText = "إعادة المحاولة"
                )
            }
            else -> {
                HomeContent(
                    sections = uiState.filteredSections,
                    filters = uiState.contentTypeFilters,
                    selectedFilter = uiState.selectedFilter,
                    onFilterSelected = viewModel::onFilterSelected
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    sections: List<Sections>,
    filters: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            HomeHeader()
        }

        item {
            FilterChipsRow(
                filters = filters,
                selectedFilter = selectedFilter,
                onFilterSelected = onFilterSelected
            )
        }

        itemsIndexed(
            items = sections,
            key = { index, section -> "section_${index}_${section.order}" }
        ) { _, section ->
            SectionItem(section = section)
        }
    }
}

@Composable
private fun HomeHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AppAsyncImage(
                    imageUrl = null,
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize(),
                    imageShape = AppImageShape.Circle
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "مساء الخير، عبدالرحمن 👋",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun FilterChipsRow(
    filters: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(filters.size) { index ->
            val filter = filters[index]
            FilterChip(
                text = filter,
                selected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) }
            )
        }
    }
}

@Composable
private fun SectionItem(
    section: Sections,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        section.name?.let { name ->
            SectionHeader(title = name)
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (SectionType.fromValue(section.type)) {
            SectionType.QUEUE -> {
                QueueSection(content = section.content)
            }

            SectionType.TWO_LINES_GRID -> {
                PodcastGridSection(content = section.content)
            }

            SectionType.BIG_SQUARE -> {
                LargeFeaturedSection(content = section.content)
            }

            SectionType.SQUARE -> {
                HorizontalPodcastSection(content = section.content)
            }

            SectionType.UNKNOWN -> {
                HorizontalPodcastSection(content = section.content)
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Start,
        modifier = modifier.padding(horizontal = 16.dp)
    )
}
