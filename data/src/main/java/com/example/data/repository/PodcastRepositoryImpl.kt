package com.example.data.repository

import com.example.domain.datasource.remote_datasource.RemoteDatasource
import com.example.domain.repository.PodcastRepository

class PodcastRepositoryImpl(private val remoteDatasource: RemoteDatasource) : PodcastRepository {

    override fun fetchPodcasts() = remoteDatasource.fetchPodcasts()
}