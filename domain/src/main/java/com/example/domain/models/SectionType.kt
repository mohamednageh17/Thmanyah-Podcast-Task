package com.example.domain.models

enum class SectionType(val value: String) {
    SQUARE("square"),
    TWO_LINES_GRID("2_lines_grid"),
    QUEUE("queue"),
    BIG_SQUARE("big_square"),
    UNKNOWN("unknown");

    companion object {
        fun fromValue(value: String?): SectionType {
            return entries.find { it.value.equals(value, ignoreCase = true) } ?: UNKNOWN
        }
    }
}


