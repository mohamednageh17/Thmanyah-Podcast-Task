package com.example.data.di

import com.example.data.datasource.remote_datasource.RemoteDatasourceImpl
import com.example.data.repository.PodcastRepositoryImpl
import com.example.data.repository.SearchRepositoryImpl
import com.example.domain.datasource.remote_datasource.RemoteDatasource
import com.example.domain.repository.PodcastRepository
import com.example.domain.repository.SearchRepository
import org.koin.dsl.module

val dataModule = module {

    // Datasources
    single<RemoteDatasource> {
        RemoteDatasourceImpl(
            podcastsApi = get(),
            searchApi = get()
        )
    }

    // Repositories
    single<PodcastRepository> {
        PodcastRepositoryImpl(remoteDatasource = get())
    }

    single<SearchRepository> {
        SearchRepositoryImpl(remoteDatasource = get())
    }
}

