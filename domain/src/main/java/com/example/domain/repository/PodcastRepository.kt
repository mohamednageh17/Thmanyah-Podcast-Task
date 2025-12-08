package com.example.domain.repository

import com.example.domain.models.PodcastsList
import com.example.domain.utilis.DataState
import kotlinx.coroutines.flow.Flow

interface PodcastRepository {

    fun fetchPodcasts(): Flow<DataState<PodcastsList>>
}