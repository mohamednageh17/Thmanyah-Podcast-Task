package com.example.core.designsystem.components.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.core.designsystem.theme.ThmanyahTextStyles
import com.example.core.designsystem.theme.ThmanyahTheme

/**
 * Section header component for list/grid sections
 *
 * @param title Section title text
 * @param modifier Modifier for the header
 * @param subtitle Optional subtitle text
 * @param showSeeAll Whether to show "See All" action
 * @param seeAllText Text for the action button
 * @param onSeeAllClick Callback when "See All" is clicked
 */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    showSeeAll: Boolean = false,
    seeAllText: String = "See All",
    onSeeAllClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = ThmanyahTheme.spacing.md,
                vertical = ThmanyahTheme.spacing.xs
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title section
        Text(
            text = title,
            style = ThmanyahTextStyles.sectionTitle,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f, fill = false)
        )

        // See All action
        if (showSeeAll && onSeeAllClick != null) {
            Row(
                modifier = Modifier
                    .clickable(onClick = onSeeAllClick)
                    .padding(ThmanyahTheme.spacing.xxs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = seeAllText,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * Compact section header with smaller text
 *
 * @param title Section title text
 * @param modifier Modifier for the header
 */
@Composable
fun CompactSectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.SemiBold
        ),
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.padding(
            horizontal = ThmanyahTheme.spacing.md,
            vertical = ThmanyahTheme.spacing.xs
        )
    )
}

/**
 * Large section header for prominent sections
 *
 * @param title Section title text
 * @param subtitle Optional subtitle
 * @param modifier Modifier for the header
 */
@Composable
fun LargeSectionHeader(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = ThmanyahTheme.spacing.md,
                vertical = ThmanyahTheme.spacing.sm
            ),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        subtitle?.let {
            Text(
                text = " · $it",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = ThmanyahTheme.spacing.xxxs)
            )
        }
    }
}

/**
 * Subtitle text component for secondary information
 *
 * @param text Subtitle text
 * @param modifier Modifier for the text
 * @param color Text color
 * @param maxLines Maximum number of lines
 */
@Composable
fun SubtitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    maxLines: Int = 2
) {
    Text(
        text = text,
        style = ThmanyahTextStyles.cardSubtitle,
        color = color,
        modifier = modifier,
        maxLines = maxLines,
        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
    )
}

/**
 * Metadata text component for small details (episode count, duration, etc.)
 *
 * @param text Metadata text
 * @param modifier Modifier for the text
 * @param color Text color
 */
@Composable
fun MetadataText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Text(
        text = text,
        style = ThmanyahTextStyles.metadata,
        color = color,
        modifier = modifier
    )
}

