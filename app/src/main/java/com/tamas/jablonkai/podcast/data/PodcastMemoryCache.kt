package com.tamas.jablonkai.podcast.data

import com.tamas.jablonkai.podcast.data.model.Podcast

interface PodcastMemoryCache {
    suspend fun savePodcasts(podcasts: List<Podcast>)
    suspend fun getPodcast(url: String): Podcast
}

class PodcastMemoryCacheImpl : PodcastMemoryCache {
    private var podcasts: ArrayList<Podcast> = arrayListOf()

    override suspend fun savePodcasts(podcasts: List<Podcast>) {
        podcasts.filterNot { podcast ->
            this.podcasts.contains(podcast)
        }.also { notContained ->
            this.podcasts.addAll(notContained)
        }
    }

    override suspend fun getPodcast(url: String): Podcast {
        return podcasts.find {
            it.url == url
        } ?: throw Exception("podcast not found")
    }
}
