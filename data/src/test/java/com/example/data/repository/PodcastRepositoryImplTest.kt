package com.example.data.repository

import app.cash.turbine.test
import com.example.domain.datasource.remote_datasource.RemoteDatasource
import com.example.domain.error.AppError
import com.example.domain.models.Pagination
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
    fun `fetchPodcasts returns success with data`() = runTest {
        val mockData = PodcastsList(
            sections = listOf(Sections(name = "Test", order = "1", content = emptyList())),
            pagination = Pagination(nextPage = "/page=2", totalPages = 2)
        )

        coEvery { remoteDatasource.fetchPodcasts(1) } returns flowOf(
            DataState.Loading,
            DataState.Success(mockData)
        )

        repository.fetchPodcasts(1).test {
            assertTrue(awaitItem() is DataState.Loading)
            val success = awaitItem() as DataState.Success
            assertEquals(1, success.data.sections.size)
            awaitComplete()
        }
    }

    @Test
    fun `fetchPodcasts returns error on failure`() = runTest {
        coEvery { remoteDatasource.fetchPodcasts(1) } returns flowOf(
            DataState.Loading,
            DataState.Error(AppError.Network.NoConnection)
        )

        repository.fetchPodcasts(1).test {
            awaitItem()
            val error = awaitItem() as DataState.Error
            assertTrue(error.error is AppError.Network.NoConnection)
            awaitComplete()
        }
    }
}
