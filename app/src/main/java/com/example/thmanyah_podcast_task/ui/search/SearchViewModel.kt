package com.example.thmanyah_podcast_task.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.SearchResult
import com.example.domain.usecases.SearchPodcastsUseCase
import com.example.domain.utilis.DataState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val results: List<SearchResult> = emptyList(),
    val error: String? = null,
    val isEmpty: Boolean = false
)

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchPodcastsUseCase: SearchPodcastsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        setupSearchDebounce()
    }

    /**
     * Sets up search debounce flow to prevent excessive API calls.
     * Waits 200ms after user stops typing before triggering search.
     */
    private fun setupSearchDebounce() {
        _searchQuery
            .debounce(DEBOUNCE_DELAY_MS)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            results = emptyList(),
                            isEmpty = false,
                            error = null
                        )
                    }
                    flowOf(null)
                } else {
                    searchPodcastsUseCase(query)
                }
            }
            .filter { it != null }
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }

                    is DataState.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                results = dataState.data,
                                isEmpty = dataState.data.isEmpty(),
                                error = null
                            )
                        }
                    }

                    is DataState.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = dataState.exception?.message ?: "Search failed"
                            )
                        }
                    }

                    else -> {}
                }
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
        _searchQuery.value = query
    }

    fun clearSearch() {
        _uiState.update { SearchUiState() }
        _searchQuery.value = ""
    }

    companion object {
        const val DEBOUNCE_DELAY_MS = 200L
    }
}
