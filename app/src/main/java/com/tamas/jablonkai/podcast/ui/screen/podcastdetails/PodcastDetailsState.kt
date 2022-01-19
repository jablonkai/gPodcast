package com.tamas.jablonkai.podcast.ui.screen.podcastdetails

import com.tamas.jablonkai.podcast.data.model.Podcast

sealed class PodcastDetailsState {
    object Loading : PodcastDetailsState()
    data class Loaded(val podcast: Podcast) : PodcastDetailsState()
    object Error : PodcastDetailsState()
}
