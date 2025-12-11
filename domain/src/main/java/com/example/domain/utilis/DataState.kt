package com.example.domain.utilis

import com.example.domain.error.AppError

/**
 * Represents the state of data operations.
 * Uses AppError for type-safe error handling following Clean Architecture.
 */
sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val error: AppError) : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
    data object Default : DataState<Nothing>()
}

