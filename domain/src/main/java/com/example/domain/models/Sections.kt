package com.example.domain.models

data class Sections(
    val name: String? = null,
    val type: String? = null,
    val contentType: String? = null,
    val order: String? = null,
    val content: List<Content> = arrayListOf()
)
