package com.multissue.wit.feature.login.social

import android.content.Context
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object SocialLoginClient {

    suspend fun loginWithKakao(context: Context): String =
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            try {
                loginWithKakaoTalk(context)
            } catch (e: Exception) {
                if (e is ClientError && e.reason == ClientErrorCause.Cancelled) throw e
                loginWithKakaoAccount(context)
            }
        } else {
            loginWithKakaoAccount(context)
        }

    private suspend fun loginWithKakaoTalk(context: Context): String =
        suspendCancellableCoroutine { cont ->
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                when {
                    error != null -> cont.resumeWithException(error)
                    token != null -> cont.resume(token.accessToken)
                    else -> cont.resumeWithException(IllegalStateException("카카오톡 로그인 실패"))
                }
            }
        }

    private suspend fun loginWithKakaoAccount(context: Context): String =
        suspendCancellableCoroutine { cont ->
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                when {
                    error != null -> cont.resumeWithException(error)
                    token != null -> cont.resume(token.accessToken)
                    else -> cont.resumeWithException(IllegalStateException("카카오 계정 로그인 실패"))
                }
            }
        }
}
