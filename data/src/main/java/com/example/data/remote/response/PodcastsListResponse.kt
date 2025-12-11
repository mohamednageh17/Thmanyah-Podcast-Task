package com.example.data.remote.response

import com.google.gson.annotations.SerializedName

data class PodcastsListResponse(
    @SerializedName("sections") var sections: List<Sections> = arrayListOf(),
    @SerializedName("pagination") var pagination: Pagination? = Pagination()
)
