package com.example.domain.usecases

import app.cash.turbine.test
import com.example.domain.error.AppError
import com.example.domain.models.SearchResult
import com.example.domain.repository.SearchRepository
import com.example.domain.utilis.DataState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchPodcastsUseCaseTest {

    private lateinit var searchRepository: SearchRepository
    private lateinit var useCase: SearchPodcastsUseCase

    @Before
    fun setup() {
        searchRepository = mockk()
        useCase = SearchPodcastsUseCase(searchRepository)
    }

    @Test
    fun `returns results when repository succeeds`() = runTest {
        val mockResults = listOf(
            SearchResult("1", "Test", null, null, "podcast", null, "10")
        )

        coEvery { searchRepository.search("test") } returns flowOf(DataState.Success(mockResults))

        useCase("test").test {
            val success = awaitItem() as DataState.Success
            assertEquals(1, success.data.size)
            awaitComplete()
        }
    }

    @Test
    fun `returns error when repository fails`() = runTest {
        coEvery { searchRepository.search("test") } returns flowOf(
            DataState.Error(AppError.Network.ServerError(500, "Server Error"))
        )

        useCase("test").test {
            val error = awaitItem() as DataState.Error
            assertTrue(error.error is AppError.Network.ServerError)
            awaitComplete()
        }
    }
}
