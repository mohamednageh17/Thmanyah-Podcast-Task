package com.example.thmanyah_podcast_task.ui.search

import app.cash.turbine.test
import com.example.domain.models.SearchResult
import com.example.domain.usecases.SearchPodcastsUseCase
import com.example.domain.utilis.DataState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var searchPodcastsUseCase: SearchPodcastsUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchPodcastsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is empty`() = runTest {
        // When
        val viewModel = SearchViewModel(searchPodcastsUseCase)

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("", state.query)
            assertFalse(state.isLoading)
            assertTrue(state.results.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onQueryChange updates query in state`() = runTest {
        // Given
        val viewModel = SearchViewModel(searchPodcastsUseCase)

        // When
        viewModel.onQueryChange("test")

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("test", state.query)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `search is debounced by 200ms`() = runTest {
        // Given
        val mockResults = listOf(
            SearchResult(
                id = "1",
                name = "Test Podcast",
                description = null,
                avatarUrl = null,
                type = "podcast",
                duration = null,
                episodeCount = null
            )
        )

        every { searchPodcastsUseCase(any()) } returns flowOf(
            DataState.Loading,
            DataState.Success(mockResults)
        )

        val viewModel = SearchViewModel(searchPodcastsUseCase)

        // When - Type quickly
        viewModel.onQueryChange("t")
        advanceTimeBy(50)
        viewModel.onQueryChange("te")
        advanceTimeBy(50)
        viewModel.onQueryChange("tes")
        advanceTimeBy(50)
        viewModel.onQueryChange("test")

        // Advance time to just before debounce threshold
        advanceTimeBy(150)

        // Verify search was not called yet
        verify(exactly = 0) { searchPodcastsUseCase(any()) }

        // Advance past debounce threshold
        advanceTimeBy(100)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Search should be called only once with final query
        verify(exactly = 1) { searchPodcastsUseCase("test") }
    }

    @Test
    fun `search returns results successfully`() = runTest {
        // Given
        val mockResults = listOf(
            SearchResult(
                id = "1",
                name = "Test Podcast",
                description = "Description",
                avatarUrl = "https://example.com/image.jpg",
                type = "podcast",
                duration = null,
                episodeCount = "10"
            )
        )

        every { searchPodcastsUseCase("test") } returns flowOf(
            DataState.Loading,
            DataState.Success(mockResults)
        )

        val viewModel = SearchViewModel(searchPodcastsUseCase)

        // When
        viewModel.onQueryChange("test")
        advanceTimeBy(300) // Past debounce
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockResults, state.results)
            assertFalse(state.isLoading)
            assertFalse(state.isEmpty)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `empty search results sets isEmpty to true`() = runTest {
        // Given
        every { searchPodcastsUseCase("nonexistent") } returns flowOf(
            DataState.Loading,
            DataState.Success(emptyList())
        )

        val viewModel = SearchViewModel(searchPodcastsUseCase)

        // When
        viewModel.onQueryChange("nonexistent")
        advanceTimeBy(300)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.isEmpty)
            assertTrue(state.results.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `clearSearch resets state`() = runTest {
        // Given
        val mockResults = listOf(
            SearchResult("1", "Test", null, null, null, null, null)
        )
        every { searchPodcastsUseCase("test") } returns flowOf(
            DataState.Loading,
            DataState.Success(mockResults)
        )

        val viewModel = SearchViewModel(searchPodcastsUseCase)

        // First do a search
        viewModel.onQueryChange("test")
        advanceTimeBy(300)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.clearSearch()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("", state.query)
            assertTrue(state.results.isEmpty())
            assertFalse(state.isEmpty)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `blank query does not trigger search`() = runTest {
        // Given
        val viewModel = SearchViewModel(searchPodcastsUseCase)

        // When
        viewModel.onQueryChange("   ")
        advanceTimeBy(300)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(exactly = 0) { searchPodcastsUseCase(any()) }
    }

    @Test
    fun `search error updates error state`() = runTest {
        // Given
        val errorMessage = "Search failed"
        every { searchPodcastsUseCase("test") } returns flowOf(
            DataState.Loading,
            DataState.Error(Exception(errorMessage))
        )

        val viewModel = SearchViewModel(searchPodcastsUseCase)

        // When
        viewModel.onQueryChange("test")
        advanceTimeBy(300)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
            assertFalse(state.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `duplicate queries do not trigger multiple searches`() = runTest {
        // Given
        val mockResults = listOf(
            SearchResult("1", "Test", null, null, null, null, null)
        )
        every { searchPodcastsUseCase("test") } returns flowOf(
            DataState.Loading,
            DataState.Success(mockResults)
        )

        val viewModel = SearchViewModel(searchPodcastsUseCase)

        // When - Same query multiple times
        viewModel.onQueryChange("test")
        advanceTimeBy(300)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onQueryChange("test")
        advanceTimeBy(300)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Search called only once due to distinctUntilChanged
        verify(exactly = 1) { searchPodcastsUseCase("test") }
    }
}


