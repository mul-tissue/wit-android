package com.multissue.wit.core.data.di

import com.multissue.wit.core.data.repository.TermsRepositoryImpl
import com.multissue.wit.core.domain.repository.TermsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TermsDataModule {

    @Binds
    @Singleton
    abstract fun bindTermsRepository(
        impl: TermsRepositoryImpl,
    ): TermsRepository
}
