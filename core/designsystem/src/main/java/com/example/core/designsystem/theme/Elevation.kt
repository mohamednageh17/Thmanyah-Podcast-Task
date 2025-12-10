package com.example.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import com.example.core.designsystem.tokens.DesignTokens

/**
 * Thmanyah Elevation System
 *
 * Provides consistent elevation/shadow values throughout the application.
 */
data class Elevation(
    val none: Dp = DesignTokens.Elevation.none,
    val xs: Dp = DesignTokens.Elevation.xs,
    val sm: Dp = DesignTokens.Elevation.sm,
    val md: Dp = DesignTokens.Elevation.md,
    val lg: Dp = DesignTokens.Elevation.lg,
    val xl: Dp = DesignTokens.Elevation.xl,
    val xxl: Dp = DesignTokens.Elevation.xxl,

    // Semantic elevations
    val card: Dp = DesignTokens.Elevation.sm,
    val cardElevated: Dp = DesignTokens.Elevation.md,
    val button: Dp = DesignTokens.Elevation.sm,
    val buttonPressed: Dp = DesignTokens.Elevation.xs,
    val dialog: Dp = DesignTokens.Elevation.xl,
    val bottomSheet: Dp = DesignTokens.Elevation.lg,
    val appBar: Dp = DesignTokens.Elevation.sm,
    val fab: Dp = DesignTokens.Elevation.lg
)

val LocalElevation = staticCompositionLocalOf { Elevation() }

/**
 * Extension object to access Elevation
 */
object ThmanyahElevation {
    val elevation: Elevation
        @Composable
        @ReadOnlyComposable
        get() = LocalElevation.current
}

