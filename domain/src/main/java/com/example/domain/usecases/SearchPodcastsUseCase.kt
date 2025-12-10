package com.example.domain.usecases

import com.example.domain.repository.SearchRepository

class SearchPodcastsUseCase(private val searchRepository: SearchRepository) {
    operator fun invoke(query: String) = searchRepository.search(query)
}

