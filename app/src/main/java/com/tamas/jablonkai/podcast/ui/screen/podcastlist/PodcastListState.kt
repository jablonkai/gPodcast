package com.tamas.jablonkai.podcast.ui.screen.podcastlist

import com.tamas.jablonkai.podcast.data.model.Podcast

sealed class PodcastListState {
    object Loading : PodcastListState()
    data class Loaded(val podcasts: List<Podcast>) : PodcastListState()
    object Empty : PodcastListState()
    object Error : PodcastListState()
}
