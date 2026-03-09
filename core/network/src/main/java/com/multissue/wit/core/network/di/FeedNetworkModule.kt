package com.multissue.wit.core.network.di

import com.multissue.wit.core.network.service.FeedService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedNetworkModule {

    @Provides
    @Singleton
    fun provideFeedService(retrofit: Retrofit): FeedService =
        retrofit.create(FeedService::class.java)
}
