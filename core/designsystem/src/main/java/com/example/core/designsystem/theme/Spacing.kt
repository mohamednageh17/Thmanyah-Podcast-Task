package com.example.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import com.example.core.designsystem.tokens.DesignTokens

/**
 * Thmanyah Spacing System
 *
 * Provides consistent spacing throughout the application.
 * Accessible via LocalSpacing composition local.
 */
data class Spacing(
    val none: Dp = DesignTokens.Spacing.none,
    val xxxs: Dp = DesignTokens.Spacing.xxxs,
    val xxs: Dp = DesignTokens.Spacing.xxs,
    val xs: Dp = DesignTokens.Spacing.xs,
    val sm: Dp = DesignTokens.Spacing.sm,
    val md: Dp = DesignTokens.Spacing.md,
    val lg: Dp = DesignTokens.Spacing.lg,
    val xl: Dp = DesignTokens.Spacing.xl,
    val xxl: Dp = DesignTokens.Spacing.xxl,
    val xxxl: Dp = DesignTokens.Spacing.xxxl,
    val huge: Dp = DesignTokens.Spacing.huge,
    val massive: Dp = DesignTokens.Spacing.massive,

    // Semantic spacing
    val screenHorizontalPadding: Dp = DesignTokens.Spacing.md,
    val screenVerticalPadding: Dp = DesignTokens.Spacing.md,
    val sectionSpacing: Dp = DesignTokens.Spacing.xl,
    val itemSpacing: Dp = DesignTokens.Spacing.sm,
    val cardPadding: Dp = DesignTokens.Spacing.sm,
    val listItemPadding: Dp = DesignTokens.Spacing.md
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }

/**
 * Extension property to access Spacing from MaterialTheme
 */
object ThmanyahSpacing {
    val spacing: Spacing
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacing.current
}



