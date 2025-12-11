package com.example.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import com.example.core.designsystem.tokens.DesignTokens

/**
 * Thmanyah Shape System
 *
 * Defines all corner radius shapes used in the application.
 * Based on design tokens for consistency.
 */
val ThmanyahShapes = Shapes(
    extraSmall = RoundedCornerShape(DesignTokens.Radius.xs),
    small = RoundedCornerShape(DesignTokens.Radius.sm),
    medium = RoundedCornerShape(DesignTokens.Radius.md),
    large = RoundedCornerShape(DesignTokens.Radius.lg),
    extraLarge = RoundedCornerShape(DesignTokens.Radius.xl)
)

/**
 * Additional shape definitions for specific use cases
 */
object ThmanyahCustomShapes {

    // Card shapes
    val cardSmall = RoundedCornerShape(DesignTokens.Radius.sm)
    val cardMedium = RoundedCornerShape(DesignTokens.Radius.md)
    val cardLarge = RoundedCornerShape(DesignTokens.Radius.lg)

    // Image shapes
    val imageSquare = RoundedCornerShape(DesignTokens.Radius.md)
    val imageRounded = RoundedCornerShape(DesignTokens.Radius.lg)
    val imageCircle = RoundedCornerShape(DesignTokens.Radius.full)

    // Button shapes
    val buttonSmall = RoundedCornerShape(DesignTokens.Radius.sm)
    val buttonMedium = RoundedCornerShape(DesignTokens.Radius.md)
    val buttonPill = RoundedCornerShape(DesignTokens.Radius.full)

    // Input shapes
    val inputField = RoundedCornerShape(DesignTokens.Radius.md)

    // Chip shapes
    val chip = RoundedCornerShape(DesignTokens.Radius.xs)

    // Bottom sheet
    val bottomSheet = RoundedCornerShape(
        topStart = DesignTokens.Radius.xl,
        topEnd = DesignTokens.Radius.xl
    )
}



