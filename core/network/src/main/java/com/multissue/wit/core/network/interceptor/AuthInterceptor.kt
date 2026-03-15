package com.multissue.wit.core.network.interceptor

import com.multissue.wit.core.network.interceptor.Constant.AUTHORIZATION
import com.multissue.wit.core.network.interceptor.Constant.BEARER_PREFIX
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.getAccessToken()
        val request = if (token != null) {
            chain.request().newBuilder()
                .header(AUTHORIZATION, "$BEARER_PREFIX $token")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}
