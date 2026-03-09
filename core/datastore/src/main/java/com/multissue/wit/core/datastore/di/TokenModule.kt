package com.multissue.wit.core.datastore.di

import com.multissue.wit.core.datastore.token.DataStoreTokenProvider
import com.multissue.wit.core.network.interceptor.TokenProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenModule {

    @Binds
    @Singleton
    abstract fun bindTokenProvider(
        dataStoreTokenProvider: DataStoreTokenProvider,
    ): TokenProvider
}
