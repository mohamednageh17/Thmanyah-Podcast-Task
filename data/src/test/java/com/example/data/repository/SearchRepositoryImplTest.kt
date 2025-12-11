package com.example.data.repository

import app.cash.turbine.test
import com.example.domain.datasource.remote_datasource.RemoteDatasource
import com.example.domain.models.SearchResult
import com.example.domain.utilis.DataState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchRepositoryImplTest {

    private lateinit var remoteDatasource: RemoteDatasource
    private lateinit var repository: SearchRepositoryImpl

    @Before
    fun setup() {
        remoteDatasource = mockk()
        repository = SearchRepositoryImpl(remoteDatasource)
    }

    @Test
    fun `search returns success when datasource returns results`() = runTest {
        // Given
        val query = "test"
        val mockResults = listOf(
            SearchResult(
                id = "1",
                name = "Test Podcast",
                description = "A test podcast",
                avatarUrl = "https://example.com/image.jpg",
                type = "podcast",
                duration = null,
                episodeCount = "10"
            )
        )

        coEvery { remoteDatasource.search(query) } returns flowOf(
            DataState.Loading,
            DataState.Success(mockResults)
        )

        // When & Then
        repository.search(query).test {
            val loadingState = awaitItem()
            assertTrue(loadingState is DataState.Loading)

            val successState = awaitItem()
            assertTrue(successState is DataState.Success)
            assertEquals(mockResults, (successState as DataState.Success).data)

            awaitComplete()
        }
    }

    @Test
    fun `search returns empty list when no results found`() = runTest {
        // Given
        val query = "nonexistent"
        val emptyResults = emptyList<SearchResult>()

        coEvery { remoteDatasource.search(query) } returns flowOf(
            DataState.Loading,
            DataState.Success(emptyResults)
        )

        // When & Then
        repository.search(query).test {
            awaitItem() // Loading

            val successState = awaitItem()
            assertTrue(successState is DataState.Success)
            assertTrue((successState as DataState.Success).data.isEmpty())

            awaitComplete()
        }
    }

    @Test
    fun `search returns error when datasource fails`() = runTest {
        // Given
        val query = "test"
        val exception = Exception("Search failed")

        coEvery { remoteDatasource.search(query) } returns flowOf(
            DataState.Loading,
            DataState.Error(exception)
        )

        // When & Then
        repository.search(query).test {
            awaitItem() // Loading

            val errorState = awaitItem()
            assertTrue(errorState is DataState.Error)
            assertEquals(exception, (errorState as DataState.Error).exception)

            awaitComplete()
        }
    }
}


