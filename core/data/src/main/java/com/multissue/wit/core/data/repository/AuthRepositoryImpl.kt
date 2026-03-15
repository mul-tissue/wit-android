package com.multissue.wit.core.data.repository

import com.multissue.wit.core.data.mapper.toDomain
import com.multissue.wit.core.domain.exception.WitException
import com.multissue.wit.core.domain.model.auth.LoginResult
import com.multissue.wit.core.domain.model.auth.SocialType
import com.multissue.wit.core.domain.repository.AuthRepository
import com.multissue.wit.core.network.Dispatcher
import com.multissue.wit.core.network.WitDispatchers
import com.multissue.wit.core.network.interceptor.TokenProvider
import com.multissue.wit.core.network.model.ApiResponse
import com.multissue.wit.core.network.model.auth.request.SocialLoginRequest
import com.multissue.wit.core.network.service.AuthService
import com.multissue.wit.core.network.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val tokenProvider: TokenProvider,
    @Dispatcher(WitDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {

    override suspend fun socialLogin(
        socialType: SocialType,
        token: String,
    ): Result<LoginResult> = withContext(ioDispatcher) {
        val request = SocialLoginRequest(
            socialType = socialType.name,
            token = token,
        )
        when (val response = safeApiCall { authService.socialLogin(request) }) {
            is ApiResponse.Success -> {
                val data = response.data.data!!
                tokenProvider.saveTokens(data.accessToken, data.refreshToken)
                Result.success(data.toDomain())
            }
            is ApiResponse.Failure -> Result.failure(
                WitException.HttpException(response.code, response.message)
            )
            is ApiResponse.NetworkError -> Result.failure(
                WitException.NetworkException(response.throwable)
            )
        }
    }

    override fun hasAccessToken(): Boolean = tokenProvider.getAccessToken() != null

    override suspend fun clearTokens() {
        tokenProvider.clearTokens()
    }
}
