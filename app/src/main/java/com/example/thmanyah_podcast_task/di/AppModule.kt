package com.example.thmanyah_podcast_task.di

import com.example.thmanyah_podcast_task.ui.home.HomeViewModel
import com.example.thmanyah_podcast_task.ui.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // ViewModels
    viewModel { HomeViewModel(fetchPodcastsUseCase = get()) }
    viewModel { SearchViewModel(searchPodcastsUseCase = get()) }
}

