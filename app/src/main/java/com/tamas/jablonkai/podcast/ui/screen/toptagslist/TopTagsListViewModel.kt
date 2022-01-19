package com.tamas.jablonkai.podcast.ui.screen.toptagslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamas.jablonkai.podcast.domain.GetPodcastsForTopTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TopTagsListViewModel @Inject constructor(
    private val getPodcastsForTopTagsUseCase: GetPodcastsForTopTagsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<TopTagsListState>(TopTagsListState.Loading)
    val state: StateFlow<TopTagsListState> = _state

    init {
        viewModelScope.launch {
            try {
                val tags = getPodcastsForTopTagsUseCase.execute()
                _state.value = if (tags.isEmpty()) {
                    TopTagsListState.Empty
                } else {
                    TopTagsListState.Loaded(tags)
                }
            } catch (e: Exception) {
                Timber.e(e)
                _state.value = TopTagsListState.Error
            }
        }
    }
}
