package com.tuwaiq.mangareader.mangaApi.models

data class Chapter(
    val id: String,
    val number: Int,
    val title: String,
    val uploaded_at: String,
    val url: String,
    val views_count: Int
)