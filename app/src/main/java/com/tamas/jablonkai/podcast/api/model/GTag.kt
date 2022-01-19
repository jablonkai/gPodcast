package com.tamas.jablonkai.podcast.api.model

import com.tamas.jablonkai.podcast.data.model.Tag

data class GTag(
    val title: String? = null,
    val tag: String? = null,
    val usage: Int = 0
) {
    fun toTag(): Tag = Tag(
        title = title ?: "",
        tag = tag ?: ""
    )
}
