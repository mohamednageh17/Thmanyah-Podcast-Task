package com.example.core.designsystem.components.loading

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.theme.ThmanyahColors
import com.example.core.designsystem.theme.ThmanyahCustomShapes
import com.example.core.designsystem.theme.ThmanyahTheme
import com.example.core.designsystem.tokens.DesignTokens

/**
 * Creates an animated shimmer brush for loading placeholders
 */
@Composable
fun shimmerBrush(
    darkMode: Boolean = isSystemInDarkTheme()
): Brush {
    val shimmerColors = if (darkMode) {
        listOf(
            ThmanyahColors.Shimmer.DarkBase,
            ThmanyahColors.Shimmer.DarkHighlight,
            ThmanyahColors.Shimmer.DarkBase
        )
    } else {
        listOf(
            ThmanyahColors.Shimmer.LightBase,
            ThmanyahColors.Shimmer.LightHighlight,
            ThmanyahColors.Shimmer.LightBase
        )
    }

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation - 500f, translateAnimation - 500f),
        end = Offset(translateAnimation, translateAnimation)
    )
}

/**
 * Basic shimmer box for creating loading placeholders
 *
 * @param modifier Modifier for the shimmer box
 * @param shape Shape of the shimmer (defaults to rounded rectangle)
 */
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    shape: Shape = ThmanyahCustomShapes.cardMedium
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(shimmerBrush())
    )
}

/**
 * Shimmer placeholder for text lines
 *
 * @param width Width of the text line
 * @param height Height of the text line (defaults to typical text height)
 */
@Composable
fun ShimmerText(
    modifier: Modifier = Modifier,
    width: Dp = 120.dp,
    height: Dp = 14.dp
) {
    ShimmerBox(
        modifier = modifier
            .width(width)
            .height(height),
        shape = RoundedCornerShape(DesignTokens.Radius.xs)
    )
}

/**
 * Shimmer placeholder for square images (podcast covers, etc.)
 *
 * @param size Size of the square
 */
@Composable
fun ShimmerSquare(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp
) {
    ShimmerBox(
        modifier = modifier.size(size),
        shape = ThmanyahCustomShapes.imageSquare
    )
}

/**
 * Shimmer placeholder for circular images (avatars, etc.)
 *
 * @param size Diameter of the circle
 */
@Composable
fun ShimmerCircle(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    ShimmerBox(
        modifier = modifier.size(size),
        shape = ThmanyahCustomShapes.imageCircle
    )
}

/**
 * Shimmer placeholder for a podcast card (image + title + subtitle)
 */
@Composable
fun ShimmerPodcastCard(
    modifier: Modifier = Modifier,
    imageSize: Dp = 140.dp
) {
    Column(modifier = modifier.width(imageSize)) {
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = ThmanyahCustomShapes.imageSquare
        )
        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.xs))
        ShimmerText(width = imageSize * 0.9f, height = 14.dp)
        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.xxs))
        ShimmerText(width = imageSize * 0.6f, height = 12.dp)
    }
}

/**
 * Shimmer placeholder for a horizontal list section
 */
@Composable
fun ShimmerHorizontalSection(
    modifier: Modifier = Modifier,
    itemCount: Int = 4,
    itemWidth: Dp = 140.dp
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Section title shimmer
        ShimmerText(
            width = 150.dp,
            height = 20.dp,
            modifier = Modifier.padding(horizontal = ThmanyahTheme.spacing.md)
        )

        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.sm))

        // Horizontal list shimmer
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(ThmanyahTheme.spacing.sm),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = ThmanyahTheme.spacing.md
            )
        ) {
            items(itemCount) {
                ShimmerPodcastCard(imageSize = itemWidth)
            }
        }
    }
}

/**
 * Shimmer placeholder for a list item (horizontal card with image + text)
 */
@Composable
fun ShimmerListItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(ThmanyahTheme.spacing.sm)
    ) {
        ShimmerSquare(size = 80.dp)

        Spacer(modifier = Modifier.width(ThmanyahTheme.spacing.sm))

        Column(modifier = Modifier.weight(1f)) {
            ShimmerText(width = 180.dp, height = 16.dp)
            Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.xs))
            ShimmerText(width = 240.dp, height = 13.dp)
            Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.xxs))
            ShimmerText(width = 100.dp, height = 12.dp)
        }
    }
}

/**
 * Full screen shimmer for home page loading
 */
@Composable
fun ShimmerHomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = ThmanyahTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(ThmanyahTheme.spacing.xl)
    ) {
        repeat(3) {
            ShimmerHorizontalSection()
        }
    }
}

/**
 * Full screen shimmer for search results loading
 */
@Composable
fun ShimmerSearchResults(
    modifier: Modifier = Modifier,
    itemCount: Int = 6
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(ThmanyahTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(ThmanyahTheme.spacing.sm)
    ) {
        repeat(itemCount) {
            ShimmerListItem()
        }
    }
}


