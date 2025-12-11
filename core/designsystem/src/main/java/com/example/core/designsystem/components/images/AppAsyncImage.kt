package com.example.core.designsystem.components.images

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ImageNotSupported
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.example.core.designsystem.components.loading.ShimmerBox
import com.example.core.designsystem.theme.ThmanyahCustomShapes

/**
 * Image shape variants
 */
enum class AppImageShape {
    Square,
    Rounded,
    Circle
}

/**
 * Async image component with loading and error states
 *
 * @param imageUrl URL of the image to load
 * @param contentDescription Accessibility description
 * @param modifier Modifier for the image
 * @param imageShape Shape of the image
 * @param contentScale How the image should be scaled
 * @param placeholderIcon Icon to show while loading
 * @param errorIcon Icon to show on error
 */
@Composable
fun AppAsyncImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    imageShape: AppImageShape = AppImageShape.Rounded,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderIcon: ImageVector = Icons.Outlined.Photo,
    errorIcon: ImageVector = Icons.Outlined.ImageNotSupported
) {
    val shape = getImageShape(imageShape)

    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier.clip(shape),
        contentScale = contentScale
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                ShimmerBox(
                    modifier = Modifier.fillMaxSize(),
                    shape = shape
                )
            }

            is AsyncImagePainter.State.Error -> {
                ImagePlaceholder(
                    icon = errorIcon,
                    shape = shape,
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            is AsyncImagePainter.State.Empty -> {
                ImagePlaceholder(
                    icon = placeholderIcon,
                    shape = shape,
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
            }
        }
    }
}

/**
 * Square async image (1:1 aspect ratio)
 *
 * @param imageUrl URL of the image
 * @param contentDescription Accessibility description
 * @param size Size of the square image
 * @param modifier Modifier for the image
 * @param cornerShape Shape of corners
 */
@Composable
fun AppSquareImage(
    imageUrl: String?,
    contentDescription: String?,
    size: Dp,
    modifier: Modifier = Modifier,
    cornerShape: AppImageShape = AppImageShape.Rounded
) {
    AppAsyncImage(
        imageUrl = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .size(size)
            .aspectRatio(1f),
        imageShape = cornerShape
    )
}

/**
 * Podcast cover image component
 *
 * @param imageUrl URL of the podcast cover
 * @param podcastName Name of the podcast (for content description)
 * @param modifier Modifier for the image
 */
@Composable
fun PodcastCoverImage(
    imageUrl: String?,
    podcastName: String?,
    modifier: Modifier = Modifier
) {
    AppAsyncImage(
        imageUrl = imageUrl,
        contentDescription = podcastName?.let { "Cover for $it" },
        modifier = modifier.aspectRatio(1f),
        imageShape = AppImageShape.Rounded
    )
}

/**
 * Avatar/profile image component
 *
 * @param imageUrl URL of the avatar
 * @param name Name of the person (for content description)
 * @param size Size of the avatar
 * @param modifier Modifier for the image
 */
@Composable
fun AvatarImage(
    imageUrl: String?,
    name: String?,
    size: Dp = 48.dp,
    modifier: Modifier = Modifier
) {
    AppAsyncImage(
        imageUrl = imageUrl,
        contentDescription = name?.let { "Avatar of $it" },
        modifier = modifier.size(size),
        imageShape = AppImageShape.Circle
    )
}

/**
 * Image placeholder component
 */
@Composable
private fun ImagePlaceholder(
    icon: ImageVector,
    shape: Shape,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(shape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(32.dp)
        )
    }
}

private fun getImageShape(imageShape: AppImageShape): Shape {
    return when (imageShape) {
        AppImageShape.Square -> ThmanyahCustomShapes.cardSmall
        AppImageShape.Rounded -> ThmanyahCustomShapes.imageSquare
        AppImageShape.Circle -> ThmanyahCustomShapes.imageCircle
    }
}

