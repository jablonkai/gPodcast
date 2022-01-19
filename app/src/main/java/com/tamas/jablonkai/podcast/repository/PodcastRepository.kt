package com.tamas.jablonkai.podcast.repository

import com.tamas.jablonkai.podcast.api.GPodderApi
import com.tamas.jablonkai.podcast.data.PodcastMemoryCache
import com.tamas.jablonkai.podcast.data.model.Podcast
import com.tamas.jablonkai.podcast.data.model.Tag
import javax.inject.Inject

interface PodcastRepository {
    suspend fun getTopPodcasts(): List<Podcast>
    suspend fun getPodcast(url: String): Podcast
    suspend fun getTopTags(): List<Tag>
    suspend fun getPodcastsForTag(tag: String): List<Podcast>
}

class PodcastRepositoryImpl @Inject constructor(
    private val gPodderApi: GPodderApi,
    private val podcastMemoryCache: PodcastMemoryCache
) :
    PodcastRepository {

    override suspend fun getTopPodcasts(): List<Podcast> = gPodderApi.getTopPodcasts(
        TOP_PODCAST_SIZE, LOGO_IMAGE_SCALE
    ).map { podcast ->
        podcast.toPodcast()
    }.also { podcasts ->
        podcastMemoryCache.savePodcasts(podcasts)
    }

    override suspend fun getPodcast(url: String): Podcast = podcastMemoryCache.getPodcast(url)

    override suspend fun getTopTags(): List<Tag> = gPodderApi.getTopTags(TOP_TAGS_SIZE)
        .map { tag ->
            tag.toTag()
        }

    override suspend fun getPodcastsForTag(tag: String): List<Podcast> = gPodderApi
        .getPodcastsForTag(
            tag, PODCASTS_FOR_TAG_SIZE, LOGO_IMAGE_SCALE
        ).map { podcast ->
            podcast.toPodcast()
        }.also { podcasts ->
            podcastMemoryCache.savePodcasts(podcasts)
        }

    companion object {
        private const val TOP_PODCAST_SIZE = 20
        private const val LOGO_IMAGE_SCALE = 128
        private const val TOP_TAGS_SIZE = 7
        private const val PODCASTS_FOR_TAG_SIZE = 10
    }
}
