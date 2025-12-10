package com.example.core.designsystem.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.theme.ThmanyahCustomShapes
import com.example.core.designsystem.theme.ThmanyahTextStyles
import com.example.core.designsystem.tokens.DesignTokens

/**
 * Button size variants
 */
enum class AppButtonSize {
    Small,
    Medium,
    Large
}

/**
 * Button variant styles
 */
enum class AppButtonVariant {
    Primary,
    Secondary,
    Outline,
    Ghost,
    Text
}

/**
 * Internal data class for button size specifications
 */
@Stable
private data class ButtonSizeSpec(
    val height: Dp,
    val horizontalPadding: Dp,
    val iconSize: Dp,
    val textStyle: TextStyle,
    val iconSpacing: Dp
)

private val ButtonSizeSpecs = mapOf(
    AppButtonSize.Small to ButtonSizeSpec(
        height = DesignTokens.Size.buttonHeightSm,
        horizontalPadding = DesignTokens.Spacing.sm,
        iconSize = DesignTokens.Size.iconSm,
        textStyle = ThmanyahTextStyles.buttonSmall,
        iconSpacing = DesignTokens.Spacing.xxs
    ),
    AppButtonSize.Medium to ButtonSizeSpec(
        height = DesignTokens.Size.buttonHeightMd,
        horizontalPadding = DesignTokens.Spacing.md,
        iconSize = DesignTokens.Size.iconMd,
        textStyle = ThmanyahTextStyles.buttonMedium,
        iconSpacing = DesignTokens.Spacing.xs
    ),
    AppButtonSize.Large to ButtonSizeSpec(
        height = DesignTokens.Size.buttonHeightLg,
        horizontalPadding = DesignTokens.Spacing.lg,
        iconSize = DesignTokens.Size.iconMd,
        textStyle = ThmanyahTextStyles.buttonLarge,
        iconSpacing = DesignTokens.Spacing.xs
    )
)

/**
 * Thmanyah App Button
 *
 * A customizable button component following the Thmanyah design system.
 *
 * @param text Button text label
 * @param onClick Click handler
 * @param modifier Modifier for the button
 * @param variant Button style variant (Primary, Secondary, Outline, Ghost, Text)
 * @param size Button size (Small, Medium, Large)
 * @param enabled Whether the button is enabled
 * @param loading Whether to show loading state
 * @param leadingIcon Optional icon before the text
 * @param trailingIcon Optional icon after the text
 */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: AppButtonVariant = AppButtonVariant.Primary,
    size: AppButtonSize = AppButtonSize.Medium,
    enabled: Boolean = true,
    loading: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null
) {
    val sizeSpec = ButtonSizeSpecs[size]!!
    val shape = getButtonShape(size)
    val isClickable = enabled && !loading

    when (variant) {
        AppButtonVariant.Primary -> {
            PrimaryButton(
                text = text,
                onClick = onClick,
                modifier = modifier,
                sizeSpec = sizeSpec,
                shape = shape,
                enabled = isClickable,
                loading = loading,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }

        AppButtonVariant.Secondary -> {
            SecondaryButton(
                text = text,
                onClick = onClick,
                modifier = modifier,
                sizeSpec = sizeSpec,
                shape = shape,
                enabled = isClickable,
                loading = loading,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }

        AppButtonVariant.Outline -> {
            OutlineButton(
                text = text,
                onClick = onClick,
                modifier = modifier,
                sizeSpec = sizeSpec,
                shape = shape,
                enabled = isClickable,
                loading = loading,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }

        AppButtonVariant.Ghost -> {
            GhostButton(
                text = text,
                onClick = onClick,
                modifier = modifier,
                sizeSpec = sizeSpec,
                shape = shape,
                enabled = isClickable,
                loading = loading,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }

        AppButtonVariant.Text -> {
            AppTextButton(
                text = text,
                onClick = onClick,
                modifier = modifier,
                sizeSpec = sizeSpec,
                enabled = isClickable,
                loading = loading,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }
    }
}

@Composable
private fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    sizeSpec: ButtonSizeSpec,
    shape: Shape,
    enabled: Boolean,
    loading: Boolean,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector?
) {
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = sizeSpec.height),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f)
        ),
        contentPadding = PaddingValues(horizontal = sizeSpec.horizontalPadding)
    ) {
        ButtonContent(
            text = text,
            sizeSpec = sizeSpec,
            loading = loading,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    sizeSpec: ButtonSizeSpec,
    shape: Shape,
    enabled: Boolean,
    loading: Boolean,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector?
) {
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = sizeSpec.height),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.38f),
            disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.38f)
        ),
        contentPadding = PaddingValues(horizontal = sizeSpec.horizontalPadding)
    ) {
        ButtonContent(
            text = text,
            sizeSpec = sizeSpec,
            loading = loading,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    sizeSpec: ButtonSizeSpec,
    shape: Shape,
    enabled: Boolean,
    loading: Boolean,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector?
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = sizeSpec.height),
        enabled = enabled,
        shape = shape,
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.38f)
            }
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f)
        ),
        contentPadding = PaddingValues(horizontal = sizeSpec.horizontalPadding)
    ) {
        ButtonContent(
            text = text,
            sizeSpec = sizeSpec,
            loading = loading,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun GhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    sizeSpec: ButtonSizeSpec,
    shape: Shape,
    enabled: Boolean,
    loading: Boolean,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector?
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = sizeSpec.height),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f)
        ),
        contentPadding = PaddingValues(horizontal = sizeSpec.horizontalPadding)
    ) {
        ButtonContent(
            text = text,
            sizeSpec = sizeSpec,
            loading = loading,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    sizeSpec: ButtonSizeSpec,
    enabled: Boolean,
    loading: Boolean,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector?
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = sizeSpec.height),
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        ),
        contentPadding = PaddingValues(horizontal = sizeSpec.horizontalPadding / 2)
    ) {
        ButtonContent(
            text = text,
            sizeSpec = sizeSpec,
            loading = loading,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ButtonContent(
    text: String,
    sizeSpec: ButtonSizeSpec,
    loading: Boolean,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector?,
    contentColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(sizeSpec.iconSize),
                strokeWidth = 2.dp,
                color = contentColor
            )
            Spacer(modifier = Modifier.width(sizeSpec.iconSpacing))
        } else {
            leadingIcon?.let { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(sizeSpec.iconSize)
                )
                Spacer(modifier = Modifier.width(sizeSpec.iconSpacing))
            }
        }

        Text(
            text = text,
            style = sizeSpec.textStyle
        )

        trailingIcon?.let { icon ->
            Spacer(modifier = Modifier.width(sizeSpec.iconSpacing))
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(sizeSpec.iconSize)
            )
        }
    }
}

private fun getButtonShape(size: AppButtonSize): Shape {
    return when (size) {
        AppButtonSize.Small -> ThmanyahCustomShapes.buttonSmall
        AppButtonSize.Medium -> ThmanyahCustomShapes.buttonMedium
        AppButtonSize.Large -> ThmanyahCustomShapes.buttonMedium
    }
}

