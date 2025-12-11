package com.example.data.repository

import app.cash.turbine.test
import com.example.domain.datasource.remote_datasource.RemoteDatasource
import com.example.domain.error.AppError
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
    fun `search returns results successfully`() = runTest {
        val mockResults = listOf(
            SearchResult("1", "Podcast", "desc", "url", "podcast", "60", "10")
        )

        coEvery { remoteDatasource.search("test") } returns flowOf(
            DataState.Loading,
            DataState.Success(mockResults)
        )

        repository.search("test").test {
            assertTrue(awaitItem() is DataState.Loading)
            val success = awaitItem() as DataState.Success
            assertEquals(1, success.data.size)
            awaitComplete()
        }
    }

    @Test
    fun `search returns error on failure`() = runTest {
        coEvery { remoteDatasource.search("test") } returns flowOf(
            DataState.Loading,
            DataState.Error(AppError.Network.Timeout)
        )

        repository.search("test").test {
            awaitItem()
            val error = awaitItem() as DataState.Error
            assertTrue(error.error is AppError.Network.Timeout)
            awaitComplete()
        }
    }
}
