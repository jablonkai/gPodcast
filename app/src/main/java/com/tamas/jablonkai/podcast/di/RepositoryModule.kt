package com.tamas.jablonkai.podcast.di

import com.tamas.jablonkai.podcast.repository.PodcastRepository
import com.tamas.jablonkai.podcast.repository.PodcastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPodcastRepository(
        podcastRepository: PodcastRepositoryImpl
    ): PodcastRepository
}
