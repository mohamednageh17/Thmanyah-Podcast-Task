package com.example.domain.models

data class PodcastsList(
    val sections: List<Sections> = arrayListOf(),
    val pagination: Pagination? = Pagination()
)
