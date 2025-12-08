package com.example.domain.utilis

sealed class DataState<out T> {
    data class Success<T>(val data: T, val message: String? = null) : DataState<T>()
    data class Error(
        val exception: Throwable? = null
    ) : DataState<Nothing>()

    data object Loading : DataState<Nothing>()
    data object Default : DataState<Nothing>()
}

