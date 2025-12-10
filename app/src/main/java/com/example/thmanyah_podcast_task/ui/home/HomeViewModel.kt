package com.example.thmanyah_podcast_task.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Sections
import com.example.domain.usecases.FetchPodcastsUseCase
import com.example.domain.utilis.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val isLoading: Boolean = false,
    val sections: List<Sections> = emptyList(),
    val error: String? = null
)

class HomeViewModel(
    private val fetchPodcastsUseCase: FetchPodcastsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchHomeSections()
    }

    fun fetchHomeSections() {
        fetchPodcastsUseCase()
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }

                    is DataState.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                sections = dataState.data.sections.sortedBy { section ->
                                    section.order?.toIntOrNull() ?: Int.MAX_VALUE
                                },
                                error = null
                            )
                        }
                    }

                    is DataState.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = dataState.exception?.message ?: "Unknown error occurred"
                            )
                        }
                    }

                    is DataState.Default -> {
                        // Initial state, do nothing
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun retry() {
        fetchHomeSections()
    }
}

