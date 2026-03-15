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

    override fun getRefreshToken(): String? = runBlocking {
        storage.get(WitStorageKeys.REFRESH_TOKEN)
    }

    override fun getUserStatus(): String? = runBlocking {
        storage.get(WitStorageKeys.USER_STATUS)
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        storage.writeValue(WitStorageKeys.ACCESS_TOKEN, accessToken)
        storage.writeValue(WitStorageKeys.REFRESH_TOKEN, refreshToken)
    }

    override suspend fun saveUserStatus(status: String) {
        storage.writeValue(WitStorageKeys.USER_STATUS, status)
    }

    override suspend fun clearTokens() {
        storage.clearValue(WitStorageKeys.ACCESS_TOKEN)
        storage.clearValue(WitStorageKeys.REFRESH_TOKEN)
        storage.clearValue(WitStorageKeys.USER_STATUS)
    }
}
