package com.example.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("results") val results: List<SearchResultResponse> = emptyList()
)

data class SearchResultResponse(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("duration") val duration: String? = null,
    @SerializedName("episode_count") val episodeCount: String? = null
)


