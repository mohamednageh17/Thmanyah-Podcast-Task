package com.example.domain.repository

import com.example.domain.models.SearchResult
import com.example.domain.utilis.DataState
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: String): Flow<DataState<List<SearchResult>>>
}



