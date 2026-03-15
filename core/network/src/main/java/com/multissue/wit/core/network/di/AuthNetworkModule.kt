package com.multissue.wit.core.network.di

import com.multissue.wit.core.network.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNetworkModule {

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    @TokenRefresh
    fun provideTokenRefreshAuthService(@TokenRefresh retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)
}
