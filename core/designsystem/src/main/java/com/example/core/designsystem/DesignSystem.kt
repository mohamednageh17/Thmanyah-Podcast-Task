@file:Suppress("unused")

package com.example.core.designsystem

/**
 * Thmanyah Design System
 *
 * This module provides a comprehensive design system for the Thmanyah Podcast application.
 * It includes theme configuration, reusable components, and design tokens.
 *
 * ## Usage
 *
 * ### Theme
 * Wrap your app content with ThmanyahTheme:
 * ```kotlin
 * ThmanyahTheme {
 *     // Your app content
 * }
 * ```
 *
 * ### Accessing Theme Extensions
 * ```kotlin
 * val spacing = ThmanyahTheme.spacing
 * val elevation = ThmanyahTheme.elevation
 * ```
 *
 * ### Using Components
 * ```kotlin
 * AppButton(
 *     text = "Click me",
 *     onClick = { },
 *     variant = AppButtonVariant.Primary
 * )
 *
 * SectionHeader(
 *     title = "Popular Podcasts",
 *     showSeeAll = true,
 *     onSeeAllClick = { }
 * )
 * ```
 *
 * ## Module Structure
 *
 * - `theme/` - Color, Typography, Shape, Spacing, Elevation, Theme
 * - `tokens/` - Design tokens (spacing, radius, size values)
 * - `components/` - Reusable UI components
 *   - `buttons/` - AppButton variants
 *   - `cards/` - AppCard, AppContentCard, AppListItemCard
 *   - `chips/` - AppChip, CategoryChip, TagChip, FilterChip
 *   - `images/` - AppAsyncImage, PodcastCoverImage, AvatarImage
 *   - `loading/` - AppShimmer, LoadingIndicator, shimmer placeholders
 *   - `states/` - ErrorState, EmptyState, LoadingState
 *   - `text/` - SectionHeader, SubtitleText, MetadataText
 */

// Re-export theme components
typealias Theme = com.example.core.designsystem.theme.ThmanyahTheme

// Version info
object DesignSystemInfo {
    const val VERSION = "1.0.0"
    const val MODULE_NAME = "core:designsystem"
}


