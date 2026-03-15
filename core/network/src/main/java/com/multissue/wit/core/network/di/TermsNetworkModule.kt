package com.multissue.wit.core.network.di

import com.multissue.wit.core.network.service.TermsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TermsNetworkModule {

    @Provides
    @Singleton
    fun provideTermsService(retrofit: Retrofit): TermsService =
        retrofit.create(TermsService::class.java)
}
