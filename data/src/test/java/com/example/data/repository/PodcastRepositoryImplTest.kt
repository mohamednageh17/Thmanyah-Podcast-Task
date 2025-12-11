package com.example.data.repository

import app.cash.turbine.test
import com.example.domain.datasource.remote_datasource.RemoteDatasource
import com.example.domain.models.Content
import com.example.domain.models.PodcastsList
import com.example.domain.models.Sections
import com.example.domain.utilis.DataState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PodcastRepositoryImplTest {

    private lateinit var remoteDatasource: RemoteDatasource
    private lateinit var repository: PodcastRepositoryImpl

    @Before
    fun setup() {
        remoteDatasource = mockk()
        repository = PodcastRepositoryImpl(remoteDatasource)
    }

    @Test
    fun `fetchPodcasts returns success when datasource returns data`() = runTest {
        // Given
        val mockSections = listOf(
            Sections(
                name = "Popular Podcasts",
                type = "horizontal_list",
                contentType = "podcast",
                order = "1",
                content = listOf(
                    Content(
                        podcastId = "1",
                        name = "Test Podcast",
                        description = "Description",
                        avatarUrl = "https://example.com/image.jpg"
                    )
                )
            )
        )
        val mockPodcastsList = PodcastsList(sections = mockSections)

        coEvery { remoteDatasource.fetchPodcasts() } returns flowOf(
            DataState.Loading,
            DataState.Success(mockPodcastsList)
        )

        // When & Then
        repository.fetchPodcasts().test {
            val loadingState = awaitItem()
            assertTrue(loadingState is DataState.Loading)

            val successState = awaitItem()
            assertTrue(successState is DataState.Success)
            assertEquals(mockPodcastsList, (successState as DataState.Success).data)

            awaitComplete()
        }
    }

    @Test
    fun `fetchPodcasts returns error when datasource fails`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { remoteDatasource.fetchPodcasts() } returns flowOf(
            DataState.Loading,
            DataState.Error(exception)
        )

        // When & Then
        repository.fetchPodcasts().test {
            val loadingState = awaitItem()
            assertTrue(loadingState is DataState.Loading)

            val errorState = awaitItem()
            assertTrue(errorState is DataState.Error)
            assertEquals(exception, (errorState as DataState.Error).exception)

            awaitComplete()
        }
    }

    @Test
    fun `fetchPodcasts emits loading state first`() = runTest {
        // Given
        val mockPodcastsList = PodcastsList(sections = emptyList())
        coEvery { remoteDatasource.fetchPodcasts() } returns flowOf(
            DataState.Loading,
            DataState.Success(mockPodcastsList)
        )

        // When & Then
        repository.fetchPodcasts().test {
            val firstState = awaitItem()
            assertTrue("First emission should be Loading", firstState is DataState.Loading)

            awaitItem() // Success state
            awaitComplete()
        }
    }
}


