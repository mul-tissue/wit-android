package com.multissue.wit.core.network.interceptor

import com.multissue.wit.core.network.auth.AuthEvent
import com.multissue.wit.core.network.auth.AuthEventBus
import com.multissue.wit.core.network.interceptor.Constant.AUTHORIZATION
import com.multissue.wit.core.network.interceptor.Constant.BEARER_PREFIX
import com.multissue.wit.core.network.interceptor.Constant.HEADER_RETRY
import com.multissue.wit.core.network.model.auth.request.RefreshTokenRequest
import com.multissue.wit.core.network.service.AuthService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenProvider: TokenProvider,
    private val authService: AuthService,
    private val authEventBus: AuthEventBus,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // 이미 refresh 시도 후 실패한 경우 무한 루프 방지
        if (response.request.header(HEADER_RETRY) != null) {
            runBlocking { tokenProvider.clearTokens() }
            authEventBus.emit(AuthEvent.TokenExpired)
            return null
        }

        val refreshToken = tokenProvider.getRefreshToken() ?: run {
            runBlocking { tokenProvider.clearTokens() }
            authEventBus.emit(AuthEvent.TokenExpired)
            return null
        }

        return synchronized(this) {
            // 다른 스레드에서 이미 갱신했는지 확인
            val currentToken = tokenProvider.getAccessToken()
            val requestToken = response.request.header(AUTHORIZATION)?.removePrefix("$BEARER_PREFIX ")

            if (currentToken != null && currentToken != requestToken) {
                // 이미 다른 스레드에서 갱신됨 → 새 토큰으로 재시도
                return@synchronized response.request.newBuilder()
                    .header(AUTHORIZATION, "$BEARER_PREFIX $currentToken")
                    .header(HEADER_RETRY, "true")
                    .build()
            }

            // Refresh Token으로 갱신 시도
            val newTokens = try {
                runBlocking {
                    authService.refreshToken(RefreshTokenRequest(refreshToken))
                }
            } catch (_: Exception) {
                runBlocking { tokenProvider.clearTokens() }
                authEventBus.emit(AuthEvent.TokenExpired)
                return@synchronized null
            }

            val data = newTokens.data ?: run {
                runBlocking { tokenProvider.clearTokens() }
                authEventBus.emit(AuthEvent.TokenExpired)
                return@synchronized null
            }

            runBlocking {
                tokenProvider.saveTokens(data.accessToken, data.refreshToken)
            }

            response.request.newBuilder()
                .header(AUTHORIZATION, "$BEARER_PREFIX ${data.accessToken}")
                .header(HEADER_RETRY, "true")
                .build()
        }
    }
}
