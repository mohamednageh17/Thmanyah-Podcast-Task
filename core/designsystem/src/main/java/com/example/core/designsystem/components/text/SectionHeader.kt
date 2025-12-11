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
                    .padding(ThmanyahTheme.spacing.xs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}