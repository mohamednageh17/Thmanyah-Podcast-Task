package com.example.thmanyah_podcast_task.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.components.chips.FilterChip
import com.example.core.designsystem.components.states.EmptyState
import com.example.core.designsystem.components.states.EmptyStateType
import com.example.core.designsystem.components.states.ErrorState
import com.example.core.designsystem.components.states.ErrorType
import com.example.core.designsystem.components.states.LoadingState
import com.example.core.designsystem.components.states.LoadingStateType
import com.example.core.designsystem.components.text.SectionHeader
import com.example.domain.models.Content
import com.example.domain.models.ContentType
import com.example.domain.models.SectionType
import com.example.domain.models.Sections
import com.example.thmanyah_podcast_task.R
import com.example.thmanyah_podcast_task.ui.home.components.ContentItemFactory
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onItemClick: (Content) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val greetingMessage = remember {
        "${context.getString(R.string.home_morning_greeting)}${context.getString(R.string.comma)} ${
            context.getString(
                R.string.user_name
            )
        }"
    }

    Scaffold(
        topBar = {
            HomeTopBar(greetingMessage = greetingMessage)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { scaffoldPaddings ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(scaffoldPaddings),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    LoadingState(
                        message = stringResource(R.string.home_loading),
                        type = LoadingStateType.ShimmerHome
                    )
                }

                uiState.error != null && uiState.allSections.isEmpty() -> {
                    ErrorState(
                        title = stringResource(R.string.home_error_title),
                        message = uiState.error?.message ?: stringResource(R.string.error_unknown),
                        errorType = ErrorType.Network,
                        onRetry = viewModel::retry,
                        retryButtonText = stringResource(R.string.home_retry)
                    )
                }

                uiState.allSections.isEmpty() -> {
                    EmptyState(
                        title = stringResource(R.string.home_empty),
                        message = "",
                        emptyType = EmptyStateType.NoContent
                    )
                }

                else -> {
                    HomeContent(
                        sections = uiState.filteredSections,
                        contentTypeFilters = uiState.contentTypeFilters,
                        selectedFilterIndex = uiState.selectedFilterIndex,
                        isLoadingMore = uiState.isLoadingMore,
                        hasMorePages = uiState.hasMorePages,
                        onFilterSelected = viewModel::onFilterSelected,
                        onLoadMore = viewModel::loadNextPage,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeTopBar(
    greetingMessage: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp, top = 16.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(3f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.profile_picture),
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = greetingMessage,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = stringResource(R.string.notifications),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    sections: List<Sections>,
    contentTypeFilters: List<String>,
    selectedFilterIndex: Int,
    isLoadingMore: Boolean,
    hasMorePages: Boolean,
    onFilterSelected: (Int) -> Unit,
    onLoadMore: () -> Unit,
    onItemClick: (Content) -> Unit,
    modifier: Modifier = Modifier
) {
    val filterAll = stringResource(R.string.filter_all)
    val displayFilters = remember(contentTypeFilters, filterAll) {
        listOf(filterAll) + contentTypeFilters
    }

    val listState = rememberLazyListState()

    LaunchedEffect(listState, hasMorePages, isLoadingMore) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex to totalItems
        }
            .distinctUntilChanged()
            .filter { (lastVisible, total) ->
                total > 0 && lastVisible >= total - 2
            }
            .collect {
                if (hasMorePages && !isLoadingMore) {
                    onLoadMore()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item(key = "filters") {
            CategoryChipsRow(
                categories = displayFilters,
                selectedIndex = selectedFilterIndex,
                onCategorySelected = onFilterSelected
            )
        }

        itemsIndexed(
            items = sections,
            key = { index, section -> "section_${index}_${section.name}_${section.order}" }
        ) { _, section ->
            SectionItem(
                section = section,
                onItemClick = onItemClick
            )
        }

        if (isLoadingMore) {
            item(key = "loading_more") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryChipsRow(
    categories: List<String>,
    selectedIndex: Int,
    onCategorySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories.size) { index ->
            FilterChip(
                text = categories[index],
                selected = index == selectedIndex,
                onClick = { onCategorySelected(index) }
            )
        }
    }
}

@Composable
private fun SectionItem(
    section: Sections,
    onItemClick: (Content) -> Unit,
    modifier: Modifier = Modifier
) {
    val sectionType = SectionType.fromValue(section.type)
    val contentType = ContentType.fromValue(section.contentType)

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        section.name?.let { name ->
            SectionHeader(
                title = name,
                showSeeAll = true,
                seeAllText = "",
                onSeeAllClick = { }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (sectionType) {
            SectionType.SQUARE -> {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 280.dp)
                ) {
                    itemsIndexed(
                        items = section.content,
                        key = { index, item -> "square_${index}_${item.podcastId}" }
                    ) { _, item ->
                        ContentItemFactory(
                            item = item,
                            layout = sectionType,
                            contentType = contentType,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }

            SectionType.TWO_LINES_GRID -> {
                val itemCount = section.content.size
                val rowCount = if (itemCount == 1) 1 else 2

                LazyHorizontalGrid(
                    rows = GridCells.Fixed(rowCount),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.heightIn(max = if (rowCount == 1) 108.dp else 216.dp)
                ) {
                    itemsIndexed(
                        items = section.content,
                        key = { index, item -> "grid_${index}_${item.podcastId}" }
                    ) { _, item ->
                        ContentItemFactory(
                            item = item,
                            layout = sectionType,
                            contentType = contentType,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }

            SectionType.BIG_SQUARE -> {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 280.dp)
                ) {
                    itemsIndexed(
                        items = section.content,
                        key = { index, item -> "big_${index}_${item.podcastId}" }
                    ) { _, item ->
                        ContentItemFactory(
                            item = item,
                            layout = sectionType,
                            contentType = contentType,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }

            SectionType.QUEUE -> {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.heightIn(max = 180.dp)
                ) {
                    itemsIndexed(
                        items = section.content,
                        key = { index, item -> "queue_${index}_${item.podcastId}" }
                    ) { _, item ->
                        ContentItemFactory(
                            item = item,
                            layout = sectionType,
                            contentType = contentType,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }

            SectionType.UNKNOWN -> {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 280.dp)
                ) {
                    itemsIndexed(
                        items = section.content,
                        key = { index, item -> "unknown_${index}_${item.podcastId}" }
                    ) { _, item ->
                        ContentItemFactory(
                            item = item,
                            layout = SectionType.SQUARE,
                            contentType = contentType,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
