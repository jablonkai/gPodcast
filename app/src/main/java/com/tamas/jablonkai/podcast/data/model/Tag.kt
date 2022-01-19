package com.tamas.jablonkai.podcast.data.model

data class Tag(
    val title: String,
    val tag: String,
    val podcasts: ArrayList<Podcast> = arrayListOf()
)
