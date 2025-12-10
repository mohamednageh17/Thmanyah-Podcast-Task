package com.example.core.designsystem.theme

import androidx.compose.ui.graphics.Color

/**
 * Thmanyah Design System Color Palette
 *
 * This file defines all colors used in the application.
 * Colors are organized by their semantic meaning rather than their visual appearance.
 */
object ThmanyahColors {

    // ═══════════════════════════════════════════════════════════════════════
    // BRAND COLORS
    // ═══════════════════════════════════════════════════════════════════════

    val Teal = Color(0xFF00B4A0)
    val TealLight = Color(0xFF5EEFD8)
    val TealDark = Color(0xFF008577)
    val TealSubtle = Color(0x1A00B4A0) // 10% opacity

    // ═══════════════════════════════════════════════════════════════════════
    // NEUTRAL COLORS - DARK THEME
    // ═══════════════════════════════════════════════════════════════════════

    object Dark {
        val Background = Color(0xFF0A0A0A)
        val BackgroundElevated = Color(0xFF121212)
        val Surface = Color(0xFF1A1A1A)
        val SurfaceElevated = Color(0xFF242424)
        val SurfaceVariant = Color(0xFF2E2E2E)
        val Border = Color(0xFF3A3A3A)
        val BorderSubtle = Color(0xFF2A2A2A)

        val OnBackground = Color(0xFFF5F5F5)
        val OnBackgroundMuted = Color(0xFFB0B0B0)
        val OnSurface = Color(0xFFF0F0F0)
        val OnSurfaceMuted = Color(0xFF9A9A9A)
        val OnSurfaceDisabled = Color(0xFF606060)
    }

    // ═══════════════════════════════════════════════════════════════════════
    // NEUTRAL COLORS - LIGHT THEME
    // ═══════════════════════════════════════════════════════════════════════

    object Light {
        val Background = Color(0xFFF8F9FA)
        val BackgroundElevated = Color(0xFFFFFFFF)
        val Surface = Color(0xFFFFFFFF)
        val SurfaceElevated = Color(0xFFFFFFFF)
        val SurfaceVariant = Color(0xFFF0F0F0)
        val Border = Color(0xFFE0E0E0)
        val BorderSubtle = Color(0xFFEEEEEE)

        val OnBackground = Color(0xFF1A1A1A)
        val OnBackgroundMuted = Color(0xFF5A5A5A)
        val OnSurface = Color(0xFF1A1A1A)
        val OnSurfaceMuted = Color(0xFF6A6A6A)
        val OnSurfaceDisabled = Color(0xFFA0A0A0)
    }

    // ═══════════════════════════════════════════════════════════════════════
    // SEMANTIC COLORS
    // ═══════════════════════════════════════════════════════════════════════

    object Semantic {
        // Success
        val Success = Color(0xFF22C55E)
        val SuccessLight = Color(0xFF86EFAC)
        val SuccessDark = Color(0xFF16A34A)
        val OnSuccess = Color(0xFFFFFFFF)

        // Warning
        val Warning = Color(0xFFF59E0B)
        val WarningLight = Color(0xFFFCD34D)
        val WarningDark = Color(0xFFD97706)
        val OnWarning = Color(0xFF1A1A1A)

        // Error
        val Error = Color(0xFFEF4444)
        val ErrorLight = Color(0xFFFCA5A5)
        val ErrorDark = Color(0xFFDC2626)
        val OnError = Color(0xFFFFFFFF)

        // Info
        val Info = Color(0xFF3B82F6)
        val InfoLight = Color(0xFF93C5FD)
        val InfoDark = Color(0xFF2563EB)
        val OnInfo = Color(0xFFFFFFFF)
    }

    // ═══════════════════════════════════════════════════════════════════════
    // GRADIENT COLORS
    // ═══════════════════════════════════════════════════════════════════════

    object Gradient {
        val TealStart = Color(0xFF00B4A0)
        val TealEnd = Color(0xFF008577)

        val DarkOverlayStart = Color(0x00000000)
        val DarkOverlayEnd = Color(0xCC000000)
    }

    // ═══════════════════════════════════════════════════════════════════════
    // SHIMMER COLORS
    // ═══════════════════════════════════════════════════════════════════════

    object Shimmer {
        val DarkBase = Color(0xFF1A1A1A)
        val DarkHighlight = Color(0xFF2A2A2A)

        val LightBase = Color(0xFFE8E8E8)
        val LightHighlight = Color(0xFFF5F5F5)
    }
}

