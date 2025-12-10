package com.example.thmanyah_podcast_task.ui.home

import app.cash.turbine.test
import com.example.domain.models.Content
import com.example.domain.models.PodcastsList
import com.example.domain.models.Sections
import com.example.domain.usecases.FetchPodcastsUseCase
import com.example.domain.utilis.DataState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fetchPodcastsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state shows loading`() = runTest {
        // Given
        every { fetchPodcastsUseCase() } returns flowOf(DataState.Loading)

        // When
        val viewModel = HomeViewModel(fetchPodcastsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `success state updates sections`() = runTest {
        // Given
        val mockSections = listOf(
            Sections(
                name = "Popular",
                type = "horizontal_list",
                contentType = "podcast",
                order = "1",
                content = listOf(
                    Content(podcastId = "1", name = "Podcast 1")
                )
            ),
            Sections(
                name = "New Releases",
                type = "grid",
                contentType = "podcast",
                order = "2",
                content = listOf(
                    Content(podcastId = "2", name = "Podcast 2")
                )
            )
        )
        val mockPodcastsList = PodcastsList(sections = mockSections)

        every { fetchPodcastsUseCase() } returns flowOf(
            DataState.Loading,
            DataState.Success(mockPodcastsList)
        )

        // When
        val viewModel = HomeViewModel(fetchPodcastsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(2, state.sections.size)
            assertNull(state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `sections are sorted by order`() = runTest {
        // Given
        val mockSections = listOf(
            Sections(name = "Third", order = "3", content = emptyList()),
            Sections(name = "First", order = "1", content = emptyList()),
            Sections(name = "Second", order = "2", content = emptyList())
        )
        val mockPodcastsList = PodcastsList(sections = mockSections)

        every { fetchPodcastsUseCase() } returns flowOf(
            DataState.Loading,
            DataState.Success(mockPodcastsList)
        )

        // When
        val viewModel = HomeViewModel(fetchPodcastsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("First", state.sections[0].name)
            assertEquals("Second", state.sections[1].name)
            assertEquals("Third", state.sections[2].name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `error state shows error message`() = runTest {
        // Given
        val errorMessage = "Network error"
        every { fetchPodcastsUseCase() } returns flowOf(
            DataState.Loading,
            DataState.Error(Exception(errorMessage))
        )

        // When
        val viewModel = HomeViewModel(fetchPodcastsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(errorMessage, state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `retry fetches podcasts again`() = runTest {
        // Given
        var callCount = 0
        every { fetchPodcastsUseCase() } answers {
            callCount++
            flowOf(DataState.Loading, DataState.Success(PodcastsList()))
        }

        // When
        val viewModel = HomeViewModel(fetchPodcastsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.retry()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(2, callCount)
    }
}

