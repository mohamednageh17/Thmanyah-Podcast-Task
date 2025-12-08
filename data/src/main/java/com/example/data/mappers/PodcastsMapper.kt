package com.example.data.mappers

import com.example.data.remote.response.PodcastsListResponse
import com.example.data.remote.response.Sections
import com.example.domain.models.PodcastsList

fun PodcastsListResponse.toDomain() = PodcastsList(
    sections = this.sections.map { it.toDomain() }
)

fun Sections.toDomain() = com.example.domain.models.Sections(
    name = this.name,
    type = this.type,
    contentType = this.contentType,
    order = this.order,
    content = this.content.map { it.toDomain() }
)

fun com.example.data.remote.response.Content.toDomain() = com.example.domain.models.Content(
    podcastId = this.podcastId,
    name = this.name,
    description = this.description,
    avatarUrl = this.avatarUrl,
    episodeCount = this.episodeCount,
    duration = this.duration,
    language = this.language,
    priority = this.priority,
    popularityScore = this.popularityScore,
    score = this.score
)