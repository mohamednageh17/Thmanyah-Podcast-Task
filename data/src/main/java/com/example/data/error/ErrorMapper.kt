package com.example.data.error

import com.example.domain.error.AppError
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException

object ErrorMapper {

    fun map(throwable: Throwable): AppError {
        return when (throwable) {
            is UnknownHostException -> AppError.Network.NoConnection
            is ConnectException -> AppError.Network.NoConnection
            is SSLHandshakeException -> AppError.Network.NoConnection
            is SSLException -> AppError.Network.NoConnection
            is SocketTimeoutException -> AppError.Network.Timeout
            is java.util.concurrent.TimeoutException -> AppError.Network.Timeout
            is HttpException -> mapHttpException(throwable)
            is IOException -> AppError.Network.NoConnection
            is com.google.gson.JsonSyntaxException -> AppError.Data.ParsingError
            is com.google.gson.JsonParseException -> AppError.Data.ParsingError
            is com.google.gson.stream.MalformedJsonException -> AppError.Data.ParsingError
            is IllegalStateException -> AppError.Data.InvalidData
            is NullPointerException -> AppError.Data.InvalidData
            else -> AppError.Unknown(throwable.message)
        }
    }

    private fun mapHttpException(exception: HttpException): AppError {
        val code = exception.code()
        val message = exception.message()

        return when (code) {
            400 -> AppError.Data.InvalidData
            401 -> AppError.Auth.Unauthorized
            403 -> AppError.Auth.Forbidden
            404 -> AppError.Data.NotFound
            408 -> AppError.Network.Timeout
            422 -> AppError.Data.InvalidData
            429 -> AppError.Network.ServerError(code, "Too many requests")
            in 500..599 -> AppError.Network.ServerError(code, message)
            else -> AppError.Unknown("HTTP $code: $message")
        }
    }
}
