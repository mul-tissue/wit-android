package com.multissue.wit.core.data.di

import com.multissue.wit.core.data.repository.LocationRepositoryImpl
import com.multissue.wit.core.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationDataModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        impl: LocationRepositoryImpl,
    ): LocationRepository
}
