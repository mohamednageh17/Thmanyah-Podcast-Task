package com.example.data.remote.response

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("podcast_id") var podcastId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("avatar_url") var avatarUrl: String? = null,
    @SerializedName("episode_count") var episodeCount: String? = null,
    @SerializedName("duration") var duration: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("priority") var priority: String? = null,
    @SerializedName("popularityScore") var popularityScore: String? = null,
    @SerializedName("score") var score: String? = null
)
