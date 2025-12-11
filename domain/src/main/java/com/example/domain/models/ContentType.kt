package com.example.domain.models

enum class ContentType(val value: String) {
    PODCAST("podcast"),
    EPISODE("episode"),
    AUDIO_BOOK("audiobook"),
    AUDIO_ARTICLE("audio_article"),
    UNKNOWN("unknown");

    companion object {
        fun fromValue(value: String?): ContentType {
            return entries.find { it.value.equals(value, ignoreCase = true) } ?: UNKNOWN
        }
    }
}

