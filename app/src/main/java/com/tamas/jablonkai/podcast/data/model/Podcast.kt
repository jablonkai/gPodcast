package com.tamas.jablonkai.podcast.data.model

data class Podcast(
    val website: String,
    val description: String,
    val title: String,
    val author: String,
    val url: String,
    val logoUrl: String,
    val scaledLogoUrl: String,
    val subtitle: String,
    val episodes: List<Episode>
)
