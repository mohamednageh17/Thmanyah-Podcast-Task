package com.example.thmanyah_podcast_task.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ThmanyahTeal,
    onPrimary = Color.Black,
    primaryContainer = ThmanyahTealDark,
    onPrimaryContainer = ThmanyahTealLight,
    secondary = ThmanyahTealLight,
    onSecondary = Color.Black,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    error = ErrorColor,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = ThmanyahTeal,
    onPrimary = Color.White,
    primaryContainer = ThmanyahTealLight,
    onPrimaryContainer = ThmanyahTealDark,
    secondary = ThmanyahTealDark,
    onSecondary = Color.White,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    error = ErrorColor,
    onError = Color.White
)

@Composable
fun ThmanyahBoadcastTaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}