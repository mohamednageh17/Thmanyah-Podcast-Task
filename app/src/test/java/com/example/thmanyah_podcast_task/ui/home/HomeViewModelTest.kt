package com.example.thmanyah_podcast_task.ui.home

import app.cash.turbine.test
import com.example.domain.error.AppError
import com.example.domain.models.Content
import com.example.domain.models.Pagination
import com.example.domain.models.PodcastsList
import com.example.domain.models.Sections
import com.example.domain.network.NetworkMonitor
import com.example.domain.network.NetworkStatus
import com.example.domain.usecases.FetchPodcastsUseCase
import com.example.domain.utilis.DataState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fetchPodcastsUseCase: FetchPodcastsUseCase
    private lateinit var networkMonitor: NetworkMonitor
    private val networkStatusFlow = MutableStateFlow<NetworkStatus>(NetworkStatus.Connected)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fetchPodcastsUseCase = mockk()
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
    fun `fetches page 1 on init and updates sections`() = runTest {
        val mockSections = listOf(
            createSection("Section 1", "1"),
            createSection("Section 2", "2")
        )
        every { fetchPodcastsUseCase(1) } returns flowOf(
            DataState.Success(PodcastsList(sections = mockSections))
        )

        val viewModel = HomeViewModel(fetchPodcastsUseCase, networkMonitor)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(2, state.allSections.size)
            assertFalse(state.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shows error state on network failure`() = runTest {
        every { fetchPodcastsUseCase(1) } returns flowOf(
            DataState.Error(AppError.Network.NoConnection)
        )

        val viewModel = HomeViewModel(fetchPodcastsUseCase, networkMonitor)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.error is AppError.Network.NoConnection)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `parses next page from response and allows loading`() = runTest {
        val page1Response = PodcastsList(
            sections = listOf(createSection("Page1", "1")),
            pagination = Pagination(nextPage = "/home_sections?page=2", totalPages = 2)
        )
        val page2Response = PodcastsList(
            sections = listOf(createSection("Page2", "2")),
            pagination = Pagination(nextPage = null, totalPages = 2)
        )

        every { fetchPodcastsUseCase(1) } returns flowOf(DataState.Success(page1Response))
        every { fetchPodcastsUseCase(2) } returns flowOf(DataState.Success(page2Response))

        val viewModel = HomeViewModel(fetchPodcastsUseCase, networkMonitor)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(2, viewModel.uiState.value.nextPageToLoad)

        viewModel.loadNextPage()
        testDispatcher.scheduler.advanceUntilIdle()

        verify(exactly = 1) { fetchPodcastsUseCase(2) }
        assertNull(viewModel.uiState.value.nextPageToLoad)
    }

    @Test
    fun `does not load duplicate pages`() = runTest {
        val page1Response = PodcastsList(
            sections = listOf(createSection("Page1", "1")),
            pagination = Pagination(nextPage = "/home_sections?page=2", totalPages = 2)
        )
        val page2Response = PodcastsList(
            sections = listOf(createSection("Page2", "2")),
            pagination = Pagination(nextPage = "/home_sections?page=2", totalPages = 2) // duplicate
        )

        every { fetchPodcastsUseCase(1) } returns flowOf(DataState.Success(page1Response))
        every { fetchPodcastsUseCase(2) } returns flowOf(DataState.Success(page2Response))

        val viewModel = HomeViewModel(fetchPodcastsUseCase, networkMonitor)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.loadNextPage()
        testDispatcher.scheduler.advanceUntilIdle()

        // nextPageToLoad should be null since page 2 is already loaded
        assertNull(viewModel.uiState.value.nextPageToLoad)
    }

    @Test
    fun `filters sections by content type`() = runTest {
        val mockSections = listOf(
            createSection("Podcasts", "1", "podcast"),
            createSection("Episodes", "2", "episode")
        )
        every { fetchPodcastsUseCase(1) } returns flowOf(
            DataState.Success(PodcastsList(sections = mockSections))
        )

        val viewModel = HomeViewModel(fetchPodcastsUseCase, networkMonitor)
        testDispatcher.scheduler.advanceUntilIdle()

        val episodeIndex = viewModel.uiState.value.contentTypeFilters.indexOf("episode")
        viewModel.onFilterSelected(episodeIndex)

        assertEquals(1, viewModel.uiState.value.filteredSections.size)
        assertEquals("episode", viewModel.uiState.value.filteredSections[0].contentType)
    }

    @Test
    fun `retry clears state and refetches`() = runTest {
        var callCount = 0
        every { fetchPodcastsUseCase(1) } answers {
            callCount++
            flowOf(DataState.Success(PodcastsList()))
        }

        val viewModel = HomeViewModel(fetchPodcastsUseCase, networkMonitor)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.retry()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(2, callCount)
    }

    private fun createSection(name: String, order: String, contentType: String = "podcast") =
        Sections(
            name = name,
            type = "square",
            contentType = contentType,
            order = order,
            content = listOf(Content(podcastId = "1", name = "Test"))
        )
}
