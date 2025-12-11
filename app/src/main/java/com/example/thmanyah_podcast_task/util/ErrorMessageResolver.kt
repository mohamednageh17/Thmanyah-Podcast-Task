package com.example.thmanyah_podcast_task.util

import android.content.Context
import com.example.domain.error.AppError
import com.example.thmanyah_podcast_task.R

object ErrorMessageResolver {

    fun getMessage(context: Context, error: AppError): String {
        return when (error) {
            is AppError.Network.NoConnection -> context.getString(R.string.error_no_connection)
            is AppError.Network.Timeout -> context.getString(R.string.error_timeout)
            is AppError.Network.SlowConnection -> context.getString(R.string.error_slow_connection)
            is AppError.Network.ServerError -> context.getString(R.string.error_server, error.code)
            is AppError.Auth.Unauthorized -> context.getString(R.string.error_unauthorized)
            is AppError.Auth.SessionExpired -> context.getString(R.string.error_session_expired)
            is AppError.Auth.Forbidden -> context.getString(R.string.error_forbidden)
            is AppError.Data.NotFound -> context.getString(R.string.error_not_found)
            is AppError.Data.ParsingError -> context.getString(R.string.error_parsing)
            is AppError.Data.EmptyResponse -> context.getString(R.string.error_empty_response)
            is AppError.Data.InvalidData -> context.getString(R.string.error_invalid_data)
            is AppError.Unknown -> error.originalMessage
                ?: context.getString(R.string.error_unknown)
        }
    }

    fun getTitle(context: Context, error: AppError): String {
        return when (error) {
            is AppError.Network -> context.getString(R.string.error_title_network)
            is AppError.Auth -> context.getString(R.string.error_title_auth)
            is AppError.Data -> context.getString(R.string.error_title_data)
            is AppError.Unknown -> context.getString(R.string.error_title_unknown)
        }
    }

    fun getActionText(context: Context, error: AppError): String? {
        return when {
            error.isRetryable -> context.getString(R.string.error_action_retry)
            error.requiresUserAction && error is AppError.Auth -> context.getString(R.string.error_action_login)
            else -> null
        }
    }
}
