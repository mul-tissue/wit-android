package com.multissue.wit.core.network.interceptor

interface TokenProvider {
    fun getAccessToken(): String?
}
