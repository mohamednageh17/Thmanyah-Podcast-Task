package com.example.domain.models

data class SearchResult(
    val id: String,
    val name: String,
    val description: String?,
    val avatarUrl: String?,
    val type: String?,
    val duration: String?,
    val episodeCount: String?
)

