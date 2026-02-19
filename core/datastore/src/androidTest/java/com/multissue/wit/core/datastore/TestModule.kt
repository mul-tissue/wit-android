package com.multissue.wit.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.multissue.wit.core.datastore.di.DataStoreModule
import com.multissue.wit.core.datastore.storage.Storage
import com.multissue.wit.core.datastore.storage.WitDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import java.util.UUID
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class]
)

object TestModule {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideTestDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("test_wit_datastore_${UUID.randomUUID()}") },
            scope = TestScope(UnconfinedTestDispatcher())
        )
    }

    @Provides
    @Singleton
    fun provideStorage(dataStore: DataStore<Preferences>): Storage {
        return WitDataStore(dataStore)
    }
}