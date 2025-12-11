package com.example.thmanyah_podcast_task.ui.search

import app.cash.turbine.test
import com.example.domain.error.AppError
import com.example.domain.models.SearchResult
import com.example.domain.network.NetworkMonitor
import com.example.domain.network.NetworkStatus
import com.example.domain.usecases.SearchPodcastsUseCase
import com.example.domain.utilis.DataState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
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
    private lateinit var networkMonitor: NetworkMonitor
    private val networkStatusFlow = MutableStateFlow<NetworkStatus>(NetworkStatus.Connected)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchPodcastsUseCase = mockk()
        networkMonitor = mockk {
            every { networkStatus } returns networkStatusFlow
            every { isConnected } returns true
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search is debounced by 200ms`() = runTest {
        every { searchPodcastsUseCase(any()) } returns flowOf(DataState.Success(emptyList()))

        val viewModel = SearchViewModel(searchPodcastsUseCase, networkMonitor)

        viewModel.onQueryChange("t")
        advanceTimeBy(50)
        viewModel.onQueryChange("te")
        advanceTimeBy(50)
        viewModel.onQueryChange("test")
        advanceTimeBy(150)

        verify(exactly = 0) { searchPodcastsUseCase(any()) }

        advanceTimeBy(100)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(exactly = 1) { searchPodcastsUseCase("test") }
    }

    @Test
    fun `returns search results successfully`() = runTest {
        val mockResults = listOf(
            SearchResult("1", "Podcast 1", null, null, "podcast", null, "10")
        )
        every { searchPodcastsUseCase("test") } returns flowOf(DataState.Success(mockResults))

        val viewModel = SearchViewModel(searchPodcastsUseCase, networkMonitor)

        viewModel.onQueryChange("test")
        advanceTimeBy(300)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(1, state.results.size)
            assertFalse(state.isEmpty)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shows error on search failure`() = runTest {
        every { searchPodcastsUseCase("test") } returns flowOf(
            DataState.Error(AppError.Network.NoConnection)
        )

        val viewModel = SearchViewModel(searchPodcastsUseCase, networkMonitor)

        viewModel.onQueryChange("test")
        advanceTimeBy(300)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.error is AppError.Network.NoConnection)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `clearSearch resets state`() = runTest {
        every { searchPodcastsUseCase("test") } returns flowOf(
            DataState.Success(listOf(SearchResult("1", "Test", null, null, null, null, null)))
        )

        val viewModel = SearchViewModel(searchPodcastsUseCase, networkMonitor)

        viewModel.onQueryChange("test")
        advanceTimeBy(300)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.clearSearch()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("", state.query)
            assertTrue(state.results.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `blank query does not trigger search`() = runTest {
        val viewModel = SearchViewModel(searchPodcastsUseCase, networkMonitor)

        viewModel.onQueryChange("   ")
        advanceTimeBy(300)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(exactly = 0) { searchPodcastsUseCase(any()) }
    }
}
