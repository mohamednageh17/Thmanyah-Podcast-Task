package com.example.core.designsystem.tokens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Design tokens representing the foundational values of the design system.
 * These tokens are the single source of truth for spacing, sizing, and other
 * dimensional values used throughout the application.
 */
object DesignTokens {

    // ═══════════════════════════════════════════════════════════════════════
    // SPACING TOKENS
    // ═══════════════════════════════════════════════════════════════════════

    object Spacing {
        val none: Dp = 0.dp
        val xxxs: Dp = 2.dp
        val xxs: Dp = 4.dp
        val xs: Dp = 8.dp
        val sm: Dp = 12.dp
        val md: Dp = 16.dp
        val lg: Dp = 20.dp
        val xl: Dp = 24.dp
        val xxl: Dp = 32.dp
        val xxxl: Dp = 40.dp
        val huge: Dp = 48.dp
        val massive: Dp = 64.dp
    }

    // ═══════════════════════════════════════════════════════════════════════
    // CORNER RADIUS TOKENS
    // ═══════════════════════════════════════════════════════════════════════

    object Radius {
        val none: Dp = 0.dp
        val xs: Dp = 4.dp
        val sm: Dp = 8.dp
        val md: Dp = 12.dp
        val lg: Dp = 16.dp
        val xl: Dp = 20.dp
        val xxl: Dp = 24.dp
        val full: Dp = 999.dp // For pill shapes
    }

    // ═══════════════════════════════════════════════════════════════════════
    // SIZE TOKENS
    // ═══════════════════════════════════════════════════════════════════════

    object Size {
        // Icon sizes
        val iconXs: Dp = 16.dp
        val iconSm: Dp = 20.dp
        val iconMd: Dp = 24.dp
        val iconLg: Dp = 32.dp
        val iconXl: Dp = 40.dp
        val iconXxl: Dp = 48.dp

        // Touch targets
        val minTouchTarget: Dp = 48.dp

        // Component heights
        val buttonHeightSm: Dp = 36.dp
        val buttonHeightMd: Dp = 44.dp
        val buttonHeightLg: Dp = 52.dp

        // Card sizes
        val cardSmall: Dp = 120.dp
        val cardMedium: Dp = 160.dp
        val cardLarge: Dp = 200.dp
    }

    // ═══════════════════════════════════════════════════════════════════════
    // ELEVATION TOKENS (DP)
    // ═══════════════════════════════════════════════════════════════════════

    object Elevation {
        val none: Dp = 0.dp
        val xs: Dp = 1.dp
        val sm: Dp = 2.dp
        val md: Dp = 4.dp
        val lg: Dp = 8.dp
        val xl: Dp = 12.dp
        val xxl: Dp = 16.dp
    }

    // ═══════════════════════════════════════════════════════════════════════
    // ANIMATION DURATION TOKENS (MS)
    // ═══════════════════════════════════════════════════════════════════════

    object Duration {
        const val instant: Int = 0
        const val fast: Int = 150
        const val normal: Int = 300
        const val slow: Int = 450
        const val slower: Int = 600
    }

    // ═══════════════════════════════════════════════════════════════════════
    // OPACITY TOKENS
    // ═══════════════════════════════════════════════════════════════════════

    object Opacity {
        const val transparent: Float = 0f
        const val disabled: Float = 0.38f
        const val medium: Float = 0.6f
        const val high: Float = 0.87f
        const val full: Float = 1f
    }
}

