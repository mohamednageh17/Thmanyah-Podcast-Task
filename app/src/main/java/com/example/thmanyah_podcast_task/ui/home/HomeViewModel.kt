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
    val allSections: List<Sections> = emptyList(),
    val filteredSections: List<Sections> = emptyList(),
    val contentTypeFilters: List<String> = emptyList(),
    val selectedFilter: String = FILTER_ALL,
    val error: String? = null
) {
    companion object {
        const val FILTER_ALL = "الكل"
    }
}

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
                        val sortedSections = dataState.data.sections.sortedBy { section ->
                            section.order?.toIntOrNull() ?: Int.MAX_VALUE
                        }

                        val contentTypes = extractContentTypes(sortedSections)

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                allSections = sortedSections,
                                filteredSections = sortedSections,
                                contentTypeFilters = contentTypes,
                                selectedFilter = HomeUiState.FILTER_ALL,
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

                    is DataState.Default -> {}
                }
            }
            .launchIn(viewModelScope)
    }

    fun onFilterSelected(filter: String) {
        val currentState = _uiState.value

        val filteredSections = if (filter == HomeUiState.FILTER_ALL) {
            currentState.allSections
        } else {
            currentState.allSections.filter { section ->
                section.contentType.equals(filter, ignoreCase = true)
            }
        }

        _uiState.update {
            it.copy(
                selectedFilter = filter,
                filteredSections = filteredSections
            )
        }
    }

    fun retry() {
        fetchHomeSections()
    }

    private fun extractContentTypes(sections: List<Sections>): List<String> {
        val uniqueTypes = sections
            .mapNotNull { it.contentType }
            .filter { it.isNotBlank() }
            .distinct()

        return listOf(HomeUiState.FILTER_ALL) + uniqueTypes
    }
}
