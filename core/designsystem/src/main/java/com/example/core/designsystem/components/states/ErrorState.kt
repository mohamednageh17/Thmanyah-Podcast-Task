package com.example.core.designsystem.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.components.buttons.AppButton
import com.example.core.designsystem.components.buttons.AppButtonSize
import com.example.core.designsystem.components.buttons.AppButtonVariant
import com.example.core.designsystem.theme.ThmanyahTheme

/**
 * Error type for displaying appropriate icon and messaging
 */
enum class ErrorType {
    General,
    Network,
    Server,
    NotFound
}

/**
 * Full-screen error state component
 *
 * @param title Error title
 * @param message Error description message
 * @param errorType Type of error (affects icon shown)
 * @param onRetry Optional retry callback
 * @param retryButtonText Text for retry button
 * @param modifier Modifier for the component
 */
@Composable
fun ErrorState(
    title: String = "Something went wrong",
    message: String = "An unexpected error occurred. Please try again.",
    errorType: ErrorType = ErrorType.General,
    onRetry: (() -> Unit)? = null,
    retryButtonText: String = "Try Again",
    modifier: Modifier = Modifier
) {
    val icon = getErrorIcon(errorType)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(ThmanyahTheme.spacing.xxl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Error Icon
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.lg))

        // Title
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.xs))

        // Message
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        // Retry Button
        onRetry?.let { retry ->
            Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.xl))

            AppButton(
                text = retryButtonText,
                onClick = retry,
                variant = AppButtonVariant.Primary,
                size = AppButtonSize.Medium
            )
        }
    }
}

/**
 * Compact error state for inline use
 *
 * @param message Error message
 * @param onRetry Optional retry callback
 * @param modifier Modifier for the component
 */
@Composable
fun CompactErrorState(
    message: String = "Failed to load",
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(ThmanyahTheme.spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.xs))

        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        onRetry?.let { retry ->
            Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.sm))
            AppButton(
                text = "Retry",
                onClick = retry,
                variant = AppButtonVariant.Text,
                size = AppButtonSize.Small
            )
        }
    }
}

private fun getErrorIcon(errorType: ErrorType): ImageVector {
    return when (errorType) {
        ErrorType.Network -> Icons.Outlined.WifiOff
        ErrorType.General,
        ErrorType.Server,
        ErrorType.NotFound -> Icons.Outlined.ErrorOutline
    }
}


