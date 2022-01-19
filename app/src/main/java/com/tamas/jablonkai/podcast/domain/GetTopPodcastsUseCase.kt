package com.tamas.jablonkai.podcast.domain

import com.tamas.jablonkai.podcast.data.model.Podcast
import com.tamas.jablonkai.podcast.repository.PodcastRepository
import javax.inject.Inject

interface GetTopPodcastsUseCase {
    suspend fun execute(): List<Podcast>
}

class GetTopPodcastsUseCaseImpl @Inject constructor(private val podcastRepository: PodcastRepository) :
    GetTopPodcastsUseCase {
    override suspend fun execute(): List<Podcast> = podcastRepository.getTopPodcasts()
}
