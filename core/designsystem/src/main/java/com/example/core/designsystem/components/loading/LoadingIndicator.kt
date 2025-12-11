package com.example.core.designsystem.components.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Loading indicator sizes
 */
enum class LoadingSize {
    Small,
    Medium,
    Large
}

/**
 * Standard loading indicator following Thmanyah design system
 *
 * @param modifier Modifier for the indicator
 * @param size Size variant (Small, Medium, Large)
 * @param color Color of the indicator (defaults to primary)
 */
@Composable
fun AppLoadingIndicator(
    modifier: Modifier = Modifier,
    size: LoadingSize = LoadingSize.Medium,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val (indicatorSize, strokeWidth) = when (size) {
        LoadingSize.Small -> 20.dp to 2.dp
        LoadingSize.Medium -> 32.dp to 3.dp
        LoadingSize.Large -> 48.dp to 4.dp
    }

    CircularProgressIndicator(
        modifier = modifier.size(indicatorSize),
        color = color,
        strokeWidth = strokeWidth
    )
}

/**
 * Pulsing loading indicator
 *
 * @param modifier Modifier for the indicator
 * @param size Size of the indicator
 * @param color Color of the indicator
 */
@Composable
fun PulsingLoadingIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulsing")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .rotate(rotation),
            color = color,
            strokeWidth = 3.dp
        )
    }
}

/**
 * Loading dots indicator (three bouncing dots)
 *
 * Note: This is a simpler implementation using CircularProgressIndicator.
 * For a more sophisticated animation, consider using custom canvas drawing.
 */
@Composable
fun LoadingDots(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    // Using standard circular indicator for simplicity
    // Can be enhanced with custom dot animation if needed
    AppLoadingIndicator(
        modifier = modifier,
        size = LoadingSize.Small,
        color = color
    )
}



