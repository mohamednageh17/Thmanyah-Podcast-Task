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
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SearchOff
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
 * Empty state type for displaying appropriate icon and messaging
 */
enum class EmptyStateType {
    General,
    Search,
    NoResults,
    NoContent
}

/**
 * Full-screen empty state component
 *
 * @param title Empty state title
 * @param message Empty state description
 * @param emptyType Type of empty state (affects icon)
 * @param icon Optional custom icon
 * @param actionLabel Optional action button text
 * @param onAction Optional action callback
 * @param modifier Modifier for the component
 */
@Composable
fun EmptyState(
    title: String = "Nothing here yet",
    message: String = "Content will appear here once available.",
    emptyType: EmptyStateType = EmptyStateType.General,
    icon: ImageVector? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val displayIcon = icon ?: getEmptyIcon(emptyType)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(ThmanyahTheme.spacing.xxl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Empty Icon
        Icon(
            imageVector = displayIcon,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.lg))

        // Title
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
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

        // Action Button
        if (actionLabel != null && onAction != null) {
            Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.xl))

            AppButton(
                text = actionLabel,
                onClick = onAction,
                variant = AppButtonVariant.Primary,
                size = AppButtonSize.Medium
            )
        }
    }
}

/**
 * Search-specific empty state
 *
 * @param query The search query that returned no results
 * @param onClearSearch Optional callback to clear search
 * @param modifier Modifier for the component
 */
@Composable
fun SearchEmptyState(
    query: String,
    onClearSearch: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    EmptyState(
        title = "No results found",
        message = "We couldn't find anything for \"$query\". Try searching with different keywords.",
        emptyType = EmptyStateType.NoResults,
        actionLabel = onClearSearch?.let { "Clear Search" },
        onAction = onClearSearch,
        modifier = modifier
    )
}

/**
 * Search idle state (before user starts searching)
 *
 * @param message Message to display
 * @param modifier Modifier for the component
 */
@Composable
fun SearchIdleState(
    message: String = "Search for your favorite podcasts",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(ThmanyahTheme.spacing.xxl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )

        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.md))

        Text(
            text = "Search Podcasts",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.xxs))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Compact empty state for inline use
 *
 * @param message Message to display
 * @param modifier Modifier for the component
 */
@Composable
fun CompactEmptyState(
    message: String = "No items available",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(ThmanyahTheme.spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Inbox,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.xs))

        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

private fun getEmptyIcon(emptyType: EmptyStateType): ImageVector {
    return when (emptyType) {
        EmptyStateType.Search -> Icons.Outlined.Search
        EmptyStateType.NoResults -> Icons.Outlined.SearchOff
        EmptyStateType.General,
        EmptyStateType.NoContent -> Icons.Outlined.Inbox
    }
}


