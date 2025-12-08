package com.example.data.remote.response

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("next_page") var nextPage: String? = null,
    @SerializedName("total_pages") var totalPages: Int? = null
)
