package com.example.data.repository

import com.example.domain.datasource.remote_datasource.RemoteDatasource
import com.example.domain.models.SearchResult
import com.example.domain.repository.SearchRepository
import com.example.domain.utilis.DataState
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val remoteDatasource: RemoteDatasource
) : SearchRepository {

    override fun search(query: String): Flow<DataState<List<SearchResult>>> =
        remoteDatasource.search(query)
}

