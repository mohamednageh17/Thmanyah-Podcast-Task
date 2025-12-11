package com.example.domain.usecases

import app.cash.turbine.test
import com.example.domain.error.AppError
import com.example.domain.models.PodcastsList
import com.example.domain.models.Sections
import com.example.domain.repository.PodcastRepository
import com.example.domain.utilis.DataState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchPodcastsUseCaseTest {

    private lateinit var podcastRepository: PodcastRepository
    private lateinit var useCase: FetchPodcastsUseCase

    @Before
    fun setup() {
        podcastRepository = mockk()
        useCase = FetchPodcastsUseCase(podcastRepository)
    }

    @Test
    fun `returns success when repository returns data`() = runTest {
        val mockData = PodcastsList(
            sections = listOf(Sections(name = "Test", order = "1", content = emptyList()))
        )

        coEvery { podcastRepository.fetchPodcasts(1) } returns flowOf(DataState.Success(mockData))

        useCase(1).test {
            val success = awaitItem() as DataState.Success
            assertEquals(1, success.data.sections.size)
            awaitComplete()
        }
    }

    @Test
    fun `returns error when repository fails`() = runTest {
        coEvery { podcastRepository.fetchPodcasts(1) } returns flowOf(
            DataState.Error(AppError.Network.NoConnection)
        )

        useCase(1).test {
            val error = awaitItem() as DataState.Error
            assertTrue(error.error is AppError.Network.NoConnection)
            awaitComplete()
        }
    }
}
