package com.tamas.jablonkai.podcast.ui.screen.podcastdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamas.jablonkai.podcast.domain.GetPodcastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PodcastDetailsViewModel @Inject constructor(
    private val getPodcastUseCase: GetPodcastUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<PodcastDetailsState>(PodcastDetailsState.Loading)
    val state: StateFlow<PodcastDetailsState> = _state

    fun loadPodcast(url: String?) {
        url?.run {
            try {
                viewModelScope.launch {
                    val podcast = getPodcastUseCase.execute(url)
                    _state.value = PodcastDetailsState.Loaded(podcast)
                }
            } catch (e: Exception) {
                Timber.e(e)
                _state.value = PodcastDetailsState.Error
            }
        }
    }
}
