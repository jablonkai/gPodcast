package com.tamas.jablonkai.podcast.di

import com.tamas.jablonkai.podcast.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindGetTopPodcastsUseCase(
        topPodcastsUseCase: GetTopPodcastsUseCaseImpl
    ): GetTopPodcastsUseCase

    @Binds
    abstract fun bindGetPodcastUseCase(
        getPodcastUseCase: GetPodcastUseCaseImpl
    ): GetPodcastUseCase

    @Binds
    abstract fun bindGetPodcastsForTopTagsUseCase(
        getPodcastUseCase: GetPodcastsForTopTagsUseCaseImpl
    ): GetPodcastsForTopTagsUseCase
}
