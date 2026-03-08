package com.multissue.wit.core.datastore.token

import com.multissue.wit.core.datastore.storage.Storage
import com.multissue.wit.core.network.interceptor.TokenProvider
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DataStoreTokenProvider @Inject constructor(
    private val storage: Storage,
) : TokenProvider {

    override fun getAccessToken(): String? = runBlocking {
        storage.get(WitStorageKeys.ACCESS_TOKEN)
    }
}
