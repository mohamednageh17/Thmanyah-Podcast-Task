package com.example.domain.datasource.remote_datasource

import com.example.domain.models.PodcastsList
import com.example.domain.utilis.DataState
import kotlinx.coroutines.flow.Flow

interface RemoteDatasource {

    fun fetchPodcasts(): Flow<DataState<PodcastsList>>
}