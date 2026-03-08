package com.multissue.wit.core.data.di

import com.multissue.wit.core.data.repository.FeedRepositoryImpl
import com.multissue.wit.core.domain.repository.FeedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedDataModule {

    @Binds
    @Singleton
    abstract fun bindFeedRepository(
        impl: FeedRepositoryImpl,
    ): FeedRepository
}
