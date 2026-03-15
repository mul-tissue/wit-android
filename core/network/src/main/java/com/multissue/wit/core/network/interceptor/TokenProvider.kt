package com.multissue.wit.core.network.interceptor

interface TokenProvider {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun getUserStatus(): String?
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun saveUserStatus(status: String)
    suspend fun clearTokens()
}
