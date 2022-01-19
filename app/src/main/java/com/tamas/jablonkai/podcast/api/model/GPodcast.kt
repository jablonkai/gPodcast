package com.tamas.jablonkai.podcast.api.model

import com.tamas.jablonkai.podcast.data.model.Podcast

data class GPodcast(
    val website: String? = null,
    val description: String? = null,
    val title: String? = null,
    val author: String? = null,
    val url: String? = null,
    val position_last_week: Int = 0,
    val subscribers: Int = 0,
    val mygpo_link: String? = null,
    val logo_url: String? = null,
    val scaled_logo_url: String? = null
) {
    fun toPodcast(): Podcast = Podcast(
        website = website ?: "",
        description = description ?: "",
        title = title ?: "",
        author = author ?: "",
        url = url ?: "",
        logoUrl = logo_url ?: "",
        scaledLogoUrl = scaled_logo_url ?: "",
        subtitle = "",
        episodes = emptyList()
    )
}
