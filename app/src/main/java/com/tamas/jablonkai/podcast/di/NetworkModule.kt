package com.tamas.jablonkai.podcast.di

import com.tamas.jablonkai.podcast.api.GPodderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideGPodderApi(): GPodderApi {
        return GPodderApi.create()
    }
}
