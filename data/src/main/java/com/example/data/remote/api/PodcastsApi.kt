package com.example.data.remote.api

import com.example.data.remote.response.PodcastsListResponse
import retrofit2.http.GET

interface PodcastsApi {
    @GET(Endpoints.HOME_SECTIONS)
    suspend fun getPodcastsList(): PodcastsListResponse
}