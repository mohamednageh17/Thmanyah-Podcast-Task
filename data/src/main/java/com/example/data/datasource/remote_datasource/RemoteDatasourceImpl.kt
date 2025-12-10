package com.example.data.datasource.remote_datasource

import com.example.data.mappers.toDomain
import com.example.data.mappers.toSearchResultList
import com.example.data.remote.api.PodcastsApi
import com.example.data.remote.api.SearchApi
import com.example.domain.datasource.remote_datasource.RemoteDatasource
import com.example.domain.models.PodcastsList
import com.example.domain.models.SearchResult
import com.example.domain.utilis.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDatasourceImpl(
    private val podcastsApi: PodcastsApi,
    private val searchApi: SearchApi
) : RemoteDatasource {

    override fun fetchPodcasts(): Flow<DataState<PodcastsList>> {
        return flow {
            emit(DataState.Loading)
            try {
                val apiResponse = podcastsApi.getPodcastsList()
                emit(DataState.Success(apiResponse.toDomain()))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
    }

    override fun search(query: String): Flow<DataState<List<SearchResult>>> {
        return flow {
            emit(DataState.Loading)
            try {
                val apiResponse = searchApi.search(query)
                emit(DataState.Success(apiResponse.toSearchResultList()))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
    }
}