package com.tamas.jablonkai.podcast.ui.screen.podcastlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamas.jablonkai.podcast.domain.GetTopPodcastsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PodcastListViewModel @Inject constructor(
    private val getTopPodcastsUseCase: GetTopPodcastsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<PodcastListState>(PodcastListState.Loading)
    val state: StateFlow<PodcastListState> = _state

    init {
        viewModelScope.launch {
            try {
                val podcasts = getTopPodcastsUseCase.execute()
                _state.value = if (podcasts.isEmpty()) {
                    PodcastListState.Empty
                } else {
                    PodcastListState.Loaded(podcasts)
                }
            } catch (e: Exception) {
                Timber.e(e)
                _state.value = PodcastListState.Error
            }
        }
    }
}
