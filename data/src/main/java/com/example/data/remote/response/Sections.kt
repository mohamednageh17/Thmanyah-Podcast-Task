package com.example.data.remote.response

import com.google.gson.annotations.SerializedName

data class Sections(
    @SerializedName("name") var name: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("content_type") var contentType: String? = null,
    @SerializedName("order") var order: String? = null,
    @SerializedName("content") var content: List<Content> = arrayListOf()
)
