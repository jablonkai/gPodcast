package com.tamas.jablonkai.podcast.domain

import com.tamas.jablonkai.podcast.data.model.Podcast
import com.tamas.jablonkai.podcast.repository.PodcastRepository
import javax.inject.Inject

interface GetPodcastUseCase {
    suspend fun execute(url: String): Podcast
}

class GetPodcastUseCaseImpl @Inject constructor(private val podcastRepository: PodcastRepository) :
    GetPodcastUseCase {
    override suspend fun execute(url: String): Podcast =
        podcastRepository.getPodcast(url)
}
