package com.tamas.jablonkai.podcast.domain

import com.tamas.jablonkai.podcast.data.model.Tag
import com.tamas.jablonkai.podcast.repository.PodcastRepository
import javax.inject.Inject

interface GetPodcastsForTopTagsUseCase {
    suspend fun execute(): List<Tag>
}

class GetPodcastsForTopTagsUseCaseImpl @Inject constructor(
    private val podcastRepository: PodcastRepository
) :
    GetPodcastsForTopTagsUseCase {
    override suspend fun execute(): List<Tag> = podcastRepository.getTopTags()
        .map { tag ->
            val podcasts = podcastRepository.getPodcastsForTag(tag.tag)
            tag.podcasts.addAll(podcasts)
            tag
        }
        .toList()
}
