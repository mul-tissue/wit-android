package com.multissue.wit.core.data.repository

import com.multissue.wit.core.data.mapper.toDomain
import com.multissue.wit.core.datastore.storage.Storage
import com.multissue.wit.core.datastore.token.WitStorageKeys
import com.multissue.wit.core.domain.exception.WitException
import com.multissue.wit.core.domain.model.auth.LoginResult
import com.multissue.wit.core.domain.model.auth.SocialType
import com.multissue.wit.core.domain.repository.AuthRepository
import com.multissue.wit.core.network.Dispatcher
import com.multissue.wit.core.network.WitDispatchers
import com.multissue.wit.core.network.model.ApiResponse
import com.multissue.wit.core.network.model.auth.request.SocialLoginRequest
import com.multissue.wit.core.network.service.AuthService
import com.multissue.wit.core.network.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val storage: Storage,
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
                val data = response.data.data
                storage.writeValue(WitStorageKeys.ACCESS_TOKEN, data.accessToken)
                storage.writeValue(WitStorageKeys.REFRESH_TOKEN, data.refreshToken)
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
}
