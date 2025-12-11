package com.example.thmanyah_podcast_task.util

object DurationFormatter {

    fun formatDuration(durationSeconds: Int?): String {
        if (durationSeconds == null || durationSeconds <= 0) return ""

        val hours = durationSeconds / 3600
        val minutes = (durationSeconds % 3600) / 60
        val seconds = durationSeconds % 60

        return when {
            hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
            else -> String.format("%d:%02d", minutes, seconds)
        }
    }

    fun formatDurationString(durationString: String?): String {
        if (durationString.isNullOrBlank()) return ""

        return try {
            val seconds = durationString.toIntOrNull() ?: return durationString
            formatDuration(seconds)
        } catch (e: Exception) {
            durationString
        }
    }

    fun formatMinutes(durationSeconds: Int?): String {
        if (durationSeconds == null || durationSeconds <= 0) return ""

        val minutes = durationSeconds / 60
        return "$minutes min"
    }
}

