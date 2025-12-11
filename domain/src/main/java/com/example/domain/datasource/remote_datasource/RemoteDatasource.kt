package com.example.domain.datasource.remote_datasource

import com.example.domain.models.PodcastsList
import com.example.domain.models.SearchResult
import com.example.domain.utilis.DataState
import kotlinx.coroutines.flow.Flow

interface RemoteDatasource {

    fun fetchPodcasts(page: Int): Flow<DataState<PodcastsList>>

    fun search(query: String): Flow<DataState<List<SearchResult>>>
}
