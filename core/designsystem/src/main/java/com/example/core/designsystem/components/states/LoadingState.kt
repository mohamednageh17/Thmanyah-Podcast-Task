package com.example.core.designsystem.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.core.designsystem.components.loading.AppLoadingIndicator
import com.example.core.designsystem.components.loading.LoadingSize
import com.example.core.designsystem.components.loading.ShimmerHomeScreen
import com.example.core.designsystem.components.loading.ShimmerSearchResults
import com.example.core.designsystem.theme.ThmanyahTheme

/**
 * Loading state type
 */
enum class LoadingStateType {
    Spinner,
    Shimmer,
    ShimmerHome,
    ShimmerSearch
}

/**
 * Full-screen loading state component
 *
 * @param message Optional loading message
 * @param type Type of loading indicator to show
 * @param modifier Modifier for the component
 */
@Composable
fun LoadingState(
    message: String? = null,
    type: LoadingStateType = LoadingStateType.Spinner,
    modifier: Modifier = Modifier
) {
    when (type) {
        LoadingStateType.Spinner -> {
            SpinnerLoadingState(
                message = message,
                modifier = modifier
            )
        }

        LoadingStateType.ShimmerHome -> {
            ShimmerHomeScreen(modifier = modifier)
        }

        LoadingStateType.ShimmerSearch -> {
            ShimmerSearchResults(modifier = modifier)
        }

        LoadingStateType.Shimmer -> {
            ShimmerHomeScreen(modifier = modifier)
        }
    }
}

/**
 * Spinner-based loading state
 */
@Composable
private fun SpinnerLoadingState(
    message: String? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppLoadingIndicator(
                size = LoadingSize.Large,
                color = MaterialTheme.colorScheme.primary
            )

            message?.let { msg ->
                Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.md))

                Text(
                    text = msg,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Compact loading state for inline use
 *
 * @param message Optional loading message
 * @param modifier Modifier for the component
 */
@Composable
fun CompactLoadingState(
    message: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(ThmanyahTheme.spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppLoadingIndicator(
            size = LoadingSize.Medium,
            color = MaterialTheme.colorScheme.primary
        )

        message?.let { msg ->
            Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.sm))

            Text(
                text = msg,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Overlay loading state (for showing loading over existing content)
 *
 * @param isLoading Whether to show the loading overlay
 * @param message Optional loading message
 * @param content The content to show beneath the overlay
 */
@Composable
fun LoadingOverlay(
    isLoading: Boolean,
    message: String? = null,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        content()

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(ThmanyahTheme.spacing.md),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppLoadingIndicator(
                        size = LoadingSize.Medium
                    )

                    message?.let { msg ->
                        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.sm))
                        Text(
                            text = msg,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}



