package com.kth.composeplayground.video.core.data.di

import com.kth.composeplayground.video.core.api.VideoRepository
import com.kth.composeplayground.video.core.data.DefaultVideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindVideoRepository(
        impl: DefaultVideoRepository
    ): VideoRepository
}