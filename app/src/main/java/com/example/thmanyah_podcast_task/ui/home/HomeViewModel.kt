package com.example.thmanyah_podcast_task.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.error.AppError
import com.example.domain.models.Sections
import com.example.domain.network.NetworkMonitor
import com.example.domain.network.NetworkStatus
import com.example.domain.usecases.FetchPodcastsUseCase
import com.example.domain.utilis.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import timber.log.Timber

data class HomeUiState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val allSections: List<Sections> = emptyList(),
    val filteredSections: List<Sections> = emptyList(),
    val contentTypeFilters: List<String> = emptyList(),
    val selectedFilterIndex: Int = 0,
    val nextPageToLoad: Int? = null,
    val error: AppError? = null,
    val isOffline: Boolean = false
)

class HomeViewModel(
    private val fetchPodcastsUseCase: FetchPodcastsUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val loadedPages = mutableSetOf<Int>()

    init {
        observeNetworkStatus()
        fetchPage(1)
    }

    private fun observeNetworkStatus() {
        networkMonitor.networkStatus
            .onEach { status ->
                val isOffline = status is NetworkStatus.Disconnected
                _uiState.update { it.copy(isOffline = isOffline) }

                if (status is NetworkStatus.Connected && _uiState.value.error != null) {
                    Timber.d("Network restored, retrying...")
                    retry()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchPage(page: Int) {
        if (loadedPages.contains(page)) {
            Timber.d("Page $page already loaded, skipping")
            return
        }

        if (!networkMonitor.isConnected) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isLoadingMore = false,
                    error = AppError.Network.NoConnection,
                    isOffline = true
                )
            }
            return
        }

        Timber.d("Fetching page: $page")
        fetchPodcastsUseCase(page)
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        if (page == 1) {
                            _uiState.update { it.copy(isLoading = true, error = null) }
                        } else {
                            _uiState.update { it.copy(isLoadingMore = true, error = null) }
                        }
                    }

                    is DataState.Success -> {
                        loadedPages.add(page)

                        val newSections = dataState.data.sections.sortedBy { section ->
                            section.order?.toIntOrNull() ?: Int.MAX_VALUE
                        }

                        val nextPagePath = dataState.data.pagination?.nextPage
                        val nextPage = parsePageNumber(nextPagePath)

                        // Check if next page is already loaded (duplicate response)
                        val actualNextPage =
                            if (nextPage != null && !loadedPages.contains(nextPage)) {
                                nextPage
                            } else {
                                null
                            }

                        Timber.d("Page $page loaded. nextPagePath: $nextPagePath, parsedNextPage: $nextPage, actualNextPage: $actualNextPage")

                        val currentSections = _uiState.value.allSections
                        val mergedSections = if (page == 1) {
                            newSections
                        } else {
                            currentSections + newSections
                        }

                        val contentTypes = extractContentTypes(mergedSections)
                        val filteredSections = applyFilter(
                            mergedSections,
                            _uiState.value.selectedFilterIndex,
                            contentTypes
                        )

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isLoadingMore = false,
                                allSections = mergedSections,
                                filteredSections = filteredSections,
                                contentTypeFilters = contentTypes,
                                nextPageToLoad = actualNextPage,
                                error = null
                            )
                        }
                    }

                    is DataState.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isLoadingMore = false,
                                error = dataState.error
                            )
                        }
                    }

                    is DataState.Default -> {}
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadNextPage() {
        val currentState = _uiState.value

        if (currentState.isLoadingMore) {
            Timber.d("Already loading, skipping")
            return
        }

        val nextPage = currentState.nextPageToLoad
        if (nextPage == null) {
            Timber.d("No more pages to load")
            return
        }

        if (loadedPages.contains(nextPage)) {
            Timber.d("Page $nextPage already loaded, skipping")
            return
        }

        Timber.d("Loading next page: $nextPage")
        fetchPage(nextPage)
    }

    fun onFilterSelected(index: Int) {
        val currentState = _uiState.value
        val filters = currentState.contentTypeFilters

        if (index !in filters.indices && index != 0) return

        val filteredSections = applyFilter(currentState.allSections, index, filters)

        _uiState.update {
            it.copy(
                selectedFilterIndex = index,
                filteredSections = filteredSections
            )
        }
    }

    fun retry() {
        loadedPages.clear()
        _uiState.update { HomeUiState(isOffline = _uiState.value.isOffline) }
        fetchPage(1)
    }

    private fun applyFilter(
        sections: List<Sections>,
        filterIndex: Int,
        filters: List<String>
    ): List<Sections> {
        return if (filterIndex == 0) {
            sections
        } else {
            val selectedFilter = filters.getOrNull(filterIndex) ?: return sections
            sections.filter { section ->
                section.contentType.equals(selectedFilter, ignoreCase = true)
            }
        }
    }

    private fun extractContentTypes(sections: List<Sections>): List<String> {
        return sections
            .mapNotNull { it.contentType }
            .filter { it.isNotBlank() }
            .distinct()
    }

    private fun parsePageNumber(nextPagePath: String?): Int? {
        if (nextPagePath.isNullOrBlank()) return null
        val regex = Regex("page=(\\d+)")
        return regex.find(nextPagePath)?.groupValues?.get(1)?.toIntOrNull()
    }
}
