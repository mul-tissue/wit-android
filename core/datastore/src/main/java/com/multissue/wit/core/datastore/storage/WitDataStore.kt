package com.multissue.wit.core.datastore.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WitDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
): Storage {
    override fun <T> getAsFlow(key: Storage.Key<T>): Flow<T?> {
        return when (key) {
            is Storage.Key.DataKey<T> -> {
                dataStore.data.map { preferences ->
                    val str = preferences[stringPreferencesKey(key.name)]
                    if (str != null) key.serializer.decode(str) else key.defaultValue
                }
            }
            else -> {
                dataStore.data
                    .map { preferences ->
                        preferences[getDatastoreKey(key)] ?: key.defaultValue
                    }
            }
        }
    }

    override suspend fun <T> get(key: Storage.Key<T>): T? = getAsFlow(key).firstOrNull() ?: key.defaultValue

    override suspend fun <T> writeValue(key: Storage.Key<T>, value: T?) {
        dataStore.edit { preferences ->
            when (key) {
                is Storage.Key.DataKey<T> -> {
                    val dkey = stringPreferencesKey(key.name)
                    value?.let { preferences[dkey] = key.serializer.encode(it) }
                        ?: preferences.remove(dkey)
                }
                else -> {
                    val datastoreKey = getDatastoreKey(key)
                    value?.let {
                        preferences[datastoreKey] = it
                    } ?: run {
                        preferences.remove(datastoreKey)
                    }
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getDatastoreKey(key: Storage.Key<T>): Preferences.Key<T> {
        return when (key) {
            is Storage.Key.StringKey -> stringPreferencesKey(key.name)
            is Storage.Key.IntKey -> intPreferencesKey(key.name)
            is Storage.Key.BooleanKey -> booleanPreferencesKey(key.name)
            is Storage.Key.DoubleKey -> doublePreferencesKey(key.name)
            is Storage.Key.FloatKey -> floatPreferencesKey(key.name)
            is Storage.Key.LongKey -> longPreferencesKey(key.name)
            is Storage.Key.DataKey<*> -> stringPreferencesKey(key.name)
        } as Preferences.Key<T>
    }
}