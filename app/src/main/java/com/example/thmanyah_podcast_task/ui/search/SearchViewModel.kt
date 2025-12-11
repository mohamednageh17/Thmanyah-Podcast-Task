package com.example.thmanyah_podcast_task.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.error.AppError
import com.example.domain.models.SearchResult
import com.example.domain.network.NetworkMonitor
import com.example.domain.network.NetworkStatus
import com.example.domain.usecases.SearchPodcastsUseCase
import com.example.domain.utilis.DataState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val results: List<SearchResult> = emptyList(),
    val error: AppError? = null,
    val isEmpty: Boolean = false,
    val isOffline: Boolean = false
)

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchPodcastsUseCase: SearchPodcastsUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        observeNetworkStatus()
        setupSearchDebounce()
    }

    /**
     * Observe network status changes in real-time
     */
    private fun observeNetworkStatus() {
        networkMonitor.networkStatus
            .onEach { status ->
                val isOffline = status is NetworkStatus.Disconnected
                _uiState.update { it.copy(isOffline = isOffline) }
            }
            .launchIn(viewModelScope)
    }

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
                    // Check network before searching
                    if (!networkMonitor.isConnected) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = AppError.Network.NoConnection,
                                isOffline = true
                            )
                        }
                        flowOf(null)
                    } else {
                        searchPodcastsUseCase(query)
                    }
                }
            }
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
                                error = dataState.error
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
        _uiState.update { SearchUiState(isOffline = _uiState.value.isOffline) }
        _searchQuery.value = ""
    }

    companion object {
        const val DEBOUNCE_DELAY_MS = 200L
    }
}
