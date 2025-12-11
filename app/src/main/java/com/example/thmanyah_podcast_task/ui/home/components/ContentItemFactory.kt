package com.example.thmanyah_podcast_task.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.domain.models.Content
import com.example.domain.models.ContentType
import com.example.domain.models.SectionType

@Composable
fun ContentItemFactory(
    item: Content,
    layout: SectionType,
    contentType: ContentType,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    when (layout) {
        SectionType.SQUARE -> {
            when (contentType) {
                ContentType.PODCAST -> SquarePodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.EPISODE -> SquareEpisodeItem(
                    episode = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.AUDIO_BOOK -> SquarePodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.AUDIO_ARTICLE -> SquareEpisodeItem(
                    episode = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.UNKNOWN -> SquarePodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )
            }
        }

        SectionType.TWO_LINES_GRID -> {
            when (contentType) {
                ContentType.PODCAST -> TwoLinesGridPodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.EPISODE -> TwoLinesGridEpisodeItem(
                    episode = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.AUDIO_BOOK -> TwoLinesGridPodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.AUDIO_ARTICLE -> TwoLinesGridEpisodeItem(
                    episode = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.UNKNOWN -> TwoLinesGridPodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )
            }
        }

        SectionType.BIG_SQUARE -> {
            when (contentType) {
                ContentType.PODCAST -> BigSquarePodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.EPISODE -> BigSquareEpisodeItem(
                    episode = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.AUDIO_BOOK -> BigSquarePodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.AUDIO_ARTICLE -> BigSquareEpisodeItem(
                    episode = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.UNKNOWN -> BigSquarePodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )
            }
        }

        SectionType.QUEUE -> {
            when (contentType) {
                ContentType.PODCAST -> QueuePodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.EPISODE -> QueueEpisodeItem(
                    episode = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.AUDIO_BOOK -> QueuePodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.AUDIO_ARTICLE -> QueueEpisodeItem(
                    episode = item,
                    modifier = modifier,
                    onClick = onClick
                )

                ContentType.UNKNOWN -> QueuePodcastItem(
                    podcast = item,
                    modifier = modifier,
                    onClick = onClick
                )
            }
        }

        SectionType.UNKNOWN -> {
            SquarePodcastItem(
                podcast = item,
                modifier = modifier,
                onClick = onClick
            )
        }
    }
}
