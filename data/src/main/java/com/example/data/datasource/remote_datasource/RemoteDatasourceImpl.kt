package com.example.data.datasource.remote_datasource

import com.example.data.mappers.toDomain
import com.example.data.remote.api.PodcastsApi
import com.example.domain.datasource.remote_datasource.RemoteDatasource
import com.example.domain.models.PodcastsList
import com.example.domain.utilis.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDatasourceImpl(
    private val api: PodcastsApi
) : RemoteDatasource {

    override fun fetchPodcasts(): Flow<DataState<PodcastsList>> {
        return flow {
            emit(DataState.Loading)
            try {
                val apiResponse = api.getPodcastsList()
                emit(DataState.Success(apiResponse.toDomain()))

            } catch (e: Exception) {
                emit(DataState.Error(e))
                return@flow
            }
        }
    }
}