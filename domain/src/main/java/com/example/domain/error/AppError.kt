package com.example.domain.error

sealed class AppError {

    sealed class Network : AppError() {
        data object NoConnection : Network()
        data object Timeout : Network()
        data class ServerError(val code: Int, val message: String? = null) : Network()
        data object SlowConnection : Network()
    }

    sealed class Auth : AppError() {
        data object Unauthorized : Auth()
        data object SessionExpired : Auth()
        data object Forbidden : Auth()
    }

    sealed class Data : AppError() {
        data object NotFound : Data()
        data object ParsingError : Data()
        data object EmptyResponse : Data()
        data object InvalidData : Data()
    }

    data class Unknown(val originalMessage: String? = null) : AppError()

    val isRetryable: Boolean
        get() = when (this) {
            is Network.NoConnection, is Network.Timeout, is Network.SlowConnection -> true
            is Network.ServerError -> code in 500..599
            is Data.EmptyResponse -> true
            else -> false
        }

    val requiresUserAction: Boolean
        get() = this is Auth
}
