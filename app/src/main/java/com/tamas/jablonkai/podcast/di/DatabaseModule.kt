package com.tamas.jablonkai.podcast.di

import com.tamas.jablonkai.podcast.data.PodcastMemoryCache
import com.tamas.jablonkai.podcast.data.PodcastMemoryCacheImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providePodcastMemoryCache(): PodcastMemoryCache = PodcastMemoryCacheImpl()
}
