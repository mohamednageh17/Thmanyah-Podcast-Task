package com.example.domain.di

import com.example.domain.usecases.FetchPodcastsUseCase
import com.example.domain.usecases.SearchPodcastsUseCase
import org.koin.dsl.module

val domainModule = module {

    // Use Cases
    factory { FetchPodcastsUseCase(podcastRepository = get()) }
    factory { SearchPodcastsUseCase(searchRepository = get()) }
}


