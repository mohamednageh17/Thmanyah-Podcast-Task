package com.example.thmanyah_podcast_task.util

import android.content.Context
import com.example.thmanyah_podcast_task.R

object ContentTypeMapper {

    fun getLocalizedName(context: Context, contentType: String): String {
        return when (contentType.lowercase()) {
            "podcast" -> context.getString(R.string.content_type_podcast)
            "episode" -> context.getString(R.string.content_type_episode)
            "audio_book", "audiobook" -> context.getString(R.string.content_type_audio_book)
            "audio_article", "audioarticle" -> context.getString(R.string.content_type_audio_article)
            else -> contentType.replaceFirstChar { it.uppercase() }
        }
    }
}

