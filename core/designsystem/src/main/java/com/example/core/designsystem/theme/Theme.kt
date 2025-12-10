package com.example.core.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Dark color scheme for Thmanyah Design System
 */
private val ThmanyahDarkColorScheme = darkColorScheme(
    // Primary
    primary = ThmanyahColors.Teal,
    onPrimary = Color.Black,
    primaryContainer = ThmanyahColors.TealDark,
    onPrimaryContainer = ThmanyahColors.TealLight,

    // Secondary
    secondary = ThmanyahColors.TealLight,
    onSecondary = Color.Black,
    secondaryContainer = ThmanyahColors.TealDark,
    onSecondaryContainer = ThmanyahColors.TealLight,

    // Tertiary
    tertiary = ThmanyahColors.TealLight,
    onTertiary = Color.Black,

    // Background & Surface
    background = ThmanyahColors.Dark.Background,
    onBackground = ThmanyahColors.Dark.OnBackground,
    surface = ThmanyahColors.Dark.Surface,
    onSurface = ThmanyahColors.Dark.OnSurface,
    surfaceVariant = ThmanyahColors.Dark.SurfaceVariant,
    onSurfaceVariant = ThmanyahColors.Dark.OnSurfaceMuted,

    // Outline
    outline = ThmanyahColors.Dark.Border,
    outlineVariant = ThmanyahColors.Dark.BorderSubtle,

    // Error
    error = ThmanyahColors.Semantic.Error,
    onError = ThmanyahColors.Semantic.OnError,
    errorContainer = ThmanyahColors.Semantic.ErrorDark,
    onErrorContainer = ThmanyahColors.Semantic.ErrorLight,

    // Inverse
    inverseSurface = ThmanyahColors.Light.Surface,
    inverseOnSurface = ThmanyahColors.Light.OnSurface,
    inversePrimary = ThmanyahColors.TealDark,

    // Scrim
    scrim = Color.Black.copy(alpha = 0.5f)
)

/**
 * Light color scheme for Thmanyah Design System
 */
private val ThmanyahLightColorScheme = lightColorScheme(
    // Primary
    primary = ThmanyahColors.Teal,
    onPrimary = Color.White,
    primaryContainer = ThmanyahColors.TealLight,
    onPrimaryContainer = ThmanyahColors.TealDark,

    // Secondary
    secondary = ThmanyahColors.TealDark,
    onSecondary = Color.White,
    secondaryContainer = ThmanyahColors.TealLight,
    onSecondaryContainer = ThmanyahColors.TealDark,

    // Tertiary
    tertiary = ThmanyahColors.TealDark,
    onTertiary = Color.White,

    // Background & Surface
    background = ThmanyahColors.Light.Background,
    onBackground = ThmanyahColors.Light.OnBackground,
    surface = ThmanyahColors.Light.Surface,
    onSurface = ThmanyahColors.Light.OnSurface,
    surfaceVariant = ThmanyahColors.Light.SurfaceVariant,
    onSurfaceVariant = ThmanyahColors.Light.OnSurfaceMuted,

    // Outline
    outline = ThmanyahColors.Light.Border,
    outlineVariant = ThmanyahColors.Light.BorderSubtle,

    // Error
    error = ThmanyahColors.Semantic.Error,
    onError = ThmanyahColors.Semantic.OnError,
    errorContainer = ThmanyahColors.Semantic.ErrorLight,
    onErrorContainer = ThmanyahColors.Semantic.ErrorDark,

    // Inverse
    inverseSurface = ThmanyahColors.Dark.Surface,
    inverseOnSurface = ThmanyahColors.Dark.OnSurface,
    inversePrimary = ThmanyahColors.TealLight,

    // Scrim
    scrim = Color.Black.copy(alpha = 0.3f)
)

/**
 * Thmanyah Design System Theme
 *
 * Main theme composable that wraps the entire application.
 * Provides Material 3 theming with custom colors, typography, shapes,
 * and design system extensions (spacing, elevation).
 *
 * @param darkTheme Whether to use dark theme (defaults to system setting)
 * @param content The content to be themed
 */
@Composable
fun ThmanyahTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) ThmanyahDarkColorScheme else ThmanyahLightColorScheme

    // Update system bars color
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    // Provide custom composition locals
    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalElevation provides Elevation()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = ThmanyahTypography,
            shapes = ThmanyahShapes,
            content = content
        )
    }
}

/**
 * Object to access extended theme properties
 */
object ThmanyahTheme {
    /**
     * Access spacing values
     */
    val spacing: Spacing
        @Composable
        get() = LocalSpacing.current

    /**
     * Access elevation values
     */
    val elevation: Elevation
        @Composable
        get() = LocalElevation.current

    /**
     * Access custom text styles
     */
    val textStyles: ThmanyahTextStyles
        get() = ThmanyahTextStyles

    /**
     * Access custom shapes
     */
    val customShapes: ThmanyahCustomShapes
        get() = ThmanyahCustomShapes

    /**
     * Access brand colors directly
     */
    val colors: ThmanyahColors
        get() = ThmanyahColors
}

