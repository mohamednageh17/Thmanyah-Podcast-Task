package com.example.data.remote.api

import com.example.data.remote.response.PodcastsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PodcastsApi {
    @GET(Endpoints.HOME_SECTIONS)
    suspend fun getPodcastsList(): PodcastsListResponse

    @GET(Endpoints.HOME_SECTIONS)
    suspend fun getPodcastsList(
        @Query("page") page: Int
    ): PodcastsListResponse
}
