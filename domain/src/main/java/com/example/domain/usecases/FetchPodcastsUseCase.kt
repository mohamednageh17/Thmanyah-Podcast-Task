package com.example.domain.usecases

import com.example.domain.repository.PodcastRepository

class FetchPodcastsUseCase(private val podcastRepository: PodcastRepository) {
    operator fun invoke(page: Int) = podcastRepository.fetchPodcasts(page)
}
