package com.example.core.designsystem.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.example.core.designsystem.theme.ThmanyahCustomShapes
import com.example.core.designsystem.theme.ThmanyahTheme
import com.example.core.designsystem.tokens.DesignTokens

/**
 * Card size variants
 */
enum class AppCardSize {
    Small,
    Medium,
    Large
}

/**
 * Card style variants
 */
enum class AppCardVariant {
    Elevated,
    Filled,
    Outlined
}

/**
 * Thmanyah App Card
 *
 * A versatile card component for displaying content in a contained, styled surface.
 *
 * @param modifier Modifier for the card
 * @param size Card size (Small, Medium, Large) - affects corner radius
 * @param variant Card style (Elevated, Filled, Outlined)
 * @param onClick Optional click handler. If provided, card becomes clickable
 * @param enabled Whether the card is enabled (for clickable cards)
 * @param content Content to display inside the card
 */
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    size: AppCardSize = AppCardSize.Medium,
    variant: AppCardVariant = AppCardVariant.Filled,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = getCardShape(size)
    val elevation = getCardElevation(variant)

    when (variant) {
        AppCardVariant.Elevated -> {
            Card(
                modifier = modifier
                    .clip(shape)
                    .then(
                        if (onClick != null && enabled) {
                            Modifier.clickable(onClick = onClick)
                        } else {
                            Modifier
                        }
                    ),
                shape = shape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = elevation)
            ) {
                Column(content = content)
            }
        }

        AppCardVariant.Filled -> {
            Surface(
                modifier = modifier
                    .clip(shape)
                    .then(
                        if (onClick != null && enabled) {
                            Modifier.clickable(onClick = onClick)
                        } else {
                            Modifier
                        }
                    ),
                shape = shape,
                color = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Column(content = content)
            }
        }

        AppCardVariant.Outlined -> {
            Card(
                modifier = modifier
                    .clip(shape)
                    .then(
                        if (onClick != null && enabled) {
                            Modifier.clickable(onClick = onClick)
                        } else {
                            Modifier
                        }
                    ),
                shape = shape,
                colors = CardDefaults.outlinedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(content = content)
            }
        }
    }
}

/**
 * Simplified card for content items (podcast cards, search results, etc.)
 *
 * @param modifier Modifier for the card
 * @param onClick Optional click handler
 * @param backgroundColor Background color (defaults to surface)
 * @param content Content to display
 */
@Composable
fun AppContentCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .clip(ThmanyahCustomShapes.cardMedium)
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        shape = ThmanyahCustomShapes.cardMedium,
        color = backgroundColor,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(content = content)
    }
}

/**
 * Horizontal card for list items (search results, episodes, etc.)
 *
 * @param modifier Modifier for the card
 * @param onClick Optional click handler
 * @param content Content to display
 */
@Composable
fun AppListItemCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(ThmanyahCustomShapes.cardMedium)
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        shape = ThmanyahCustomShapes.cardMedium,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier.padding(ThmanyahTheme.spacing.sm),
            content = content
        )
    }
}

private fun getCardShape(size: AppCardSize): Shape {
    return when (size) {
        AppCardSize.Small -> ThmanyahCustomShapes.cardSmall
        AppCardSize.Medium -> ThmanyahCustomShapes.cardMedium
        AppCardSize.Large -> ThmanyahCustomShapes.cardLarge
    }
}

private fun getCardElevation(variant: AppCardVariant): Dp {
    return when (variant) {
        AppCardVariant.Elevated -> DesignTokens.Elevation.md
        AppCardVariant.Filled -> DesignTokens.Elevation.none
        AppCardVariant.Outlined -> DesignTokens.Elevation.none
    }
}

