package com.multissue.wit.di

import android.content.Context
import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import com.multissue.wit.core.network.config.NetworkConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkConfigModule {

    @Provides
    @Singleton
    fun provideNetworkConfig(
        @ApplicationContext context: Context,
    ): NetworkConfig {
        val isDebug = context.applicationInfo.flags and FLAG_DEBUGGABLE != 0
        return object : NetworkConfig {
            override val baseUrl: String =
                if (isDebug) "https://dev.api.example.com/"
                else "https://api.example.com/"
            override val isDebug: Boolean = isDebug
        }
    }
}
