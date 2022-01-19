package com.tamas.jablonkai.podcast.ui.screen.toptagslist

import com.tamas.jablonkai.podcast.data.model.Tag

sealed class TopTagsListState {
    object Loading : TopTagsListState()
    data class Loaded(val tags: List<Tag>) : TopTagsListState()
    object Empty : TopTagsListState()
    object Error : TopTagsListState()
}
