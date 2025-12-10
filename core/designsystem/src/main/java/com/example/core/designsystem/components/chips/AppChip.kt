package com.example.core.designsystem.components.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.theme.ThmanyahCustomShapes
import com.example.core.designsystem.theme.ThmanyahTextStyles
import com.example.core.designsystem.theme.ThmanyahTheme
import com.example.core.designsystem.tokens.DesignTokens

/**
 * Chip style variants
 */
enum class AppChipVariant {
    Filled,
    Outlined,
    Tonal
}

/**
 * Chip size variants
 */
enum class AppChipSize {
    Small,
    Medium
}

/**
 * Thmanyah App Chip
 *
 * A chip component for displaying tags, categories, or filters.
 *
 * @param text Chip text
 * @param modifier Modifier for the chip
 * @param variant Chip style (Filled, Outlined, Tonal)
 * @param size Chip size (Small, Medium)
 * @param selected Whether the chip is selected
 * @param enabled Whether the chip is enabled
 * @param leadingIcon Optional icon before text
 * @param onClick Optional click handler
 */
@Composable
fun AppChip(
    text: String,
    modifier: Modifier = Modifier,
    variant: AppChipVariant = AppChipVariant.Tonal,
    size: AppChipSize = AppChipSize.Small,
    selected: Boolean = false,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    onClick: (() -> Unit)? = null
) {
    val colors = getChipColors(variant, selected, enabled)
    val padding = when (size) {
        AppChipSize.Small -> ThmanyahTheme.spacing.xs to ThmanyahTheme.spacing.xxxs
        AppChipSize.Medium -> ThmanyahTheme.spacing.sm to ThmanyahTheme.spacing.xxs
    }
    val iconSize = when (size) {
        AppChipSize.Small -> 14.dp
        AppChipSize.Medium -> 16.dp
    }

    Box(
        modifier = modifier
            .clip(ThmanyahCustomShapes.chip)
            .then(
                when (variant) {
                    AppChipVariant.Outlined -> Modifier.border(
                        width = 1.dp,
                        color = colors.borderColor,
                        shape = ThmanyahCustomShapes.chip
                    )

                    else -> Modifier
                }
            )
            .background(colors.backgroundColor)
            .then(
                if (onClick != null && enabled) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            )
            .padding(horizontal = padding.first, vertical = padding.second)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(iconSize),
                    tint = colors.contentColor
                )
                Spacer(modifier = Modifier.width(DesignTokens.Spacing.xxs))
            }

            Text(
                text = text,
                style = ThmanyahTextStyles.chip,
                color = colors.contentColor
            )
        }
    }
}

/**
 * Category chip for content type labels
 *
 * @param category Category text (e.g., "Podcast", "Episode", "Article")
 * @param modifier Modifier for the chip
 */
@Composable
fun CategoryChip(
    category: String,
    modifier: Modifier = Modifier
) {
    AppChip(
        text = category.replaceFirstChar { it.uppercase() },
        modifier = modifier,
        variant = AppChipVariant.Tonal,
        size = AppChipSize.Small
    )
}

/**
 * Tag chip for metadata labels
 *
 * @param tag Tag text
 * @param modifier Modifier for the chip
 * @param onClick Optional click handler
 */
@Composable
fun TagChip(
    tag: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    AppChip(
        text = tag,
        modifier = modifier,
        variant = AppChipVariant.Outlined,
        size = AppChipSize.Small,
        onClick = onClick
    )
}

/**
 * Filter chip for selection states - Pill shaped
 *
 * @param text Filter text
 * @param selected Whether the filter is selected
 * @param onClick Click handler
 * @param modifier Modifier for the chip
 */
@Composable
fun FilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        Color(0xFF2A2A2A)
    }

    val contentColor = if (selected) {
        Color.Black
    } else {
        Color(0xFFAAAAAA)
    }

    Box(
        modifier = modifier
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = if (selected) androidx.compose.ui.text.font.FontWeight.SemiBold else androidx.compose.ui.text.font.FontWeight.Normal
            ),
            color = contentColor,
            fontSize = 13.sp
        )
    }
}

/**
 * Internal data class for chip colors
 */
private data class ChipColors(
    val backgroundColor: Color,
    val contentColor: Color,
    val borderColor: Color
)

@Composable
private fun getChipColors(
    variant: AppChipVariant,
    selected: Boolean,
    enabled: Boolean
): ChipColors {
    val alpha = if (enabled) 1f else DesignTokens.Opacity.disabled

    return when {
        selected -> ChipColors(
            backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = alpha),
            contentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = alpha),
            borderColor = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
        )

        variant == AppChipVariant.Filled -> ChipColors(
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha),
            borderColor = Color.Transparent
        )

        variant == AppChipVariant.Outlined -> ChipColors(
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha),
            borderColor = MaterialTheme.colorScheme.outline.copy(alpha = alpha)
        )

        variant == AppChipVariant.Tonal -> ChipColors(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = alpha),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = alpha),
            borderColor = Color.Transparent
        )

        else -> ChipColors(
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha),
            borderColor = Color.Transparent
        )
    }
}

