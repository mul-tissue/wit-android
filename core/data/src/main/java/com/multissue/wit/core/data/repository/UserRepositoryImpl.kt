package com.multissue.wit.core.data.repository

import com.multissue.wit.core.data.mapper.toDomain
import com.multissue.wit.core.domain.exception.WitException
import com.multissue.wit.core.domain.model.user.UserInfo
import com.multissue.wit.core.domain.repository.UserRepository
import com.multissue.wit.core.network.Dispatcher
import com.multissue.wit.core.network.WitDispatchers
import com.multissue.wit.core.network.model.ApiResponse
import com.multissue.wit.core.network.model.user.request.OnboardingRequest
import com.multissue.wit.core.network.service.UserService
import com.multissue.wit.core.network.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    @Dispatcher(WitDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : UserRepository {

    override suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean> =
        withContext(ioDispatcher) {
            when (val response = safeApiCall { userService.checkNicknameDuplicate(nickname) }) {
                is ApiResponse.Success -> Result.success(true)
                is ApiResponse.Failure -> Result.failure(WitException.HttpException(response.code, response.message))
                is ApiResponse.NetworkError -> Result.failure(
                    WitException.NetworkException(response.throwable)
                )
            }
        }

    override suspend fun completeOnboarding(
        nickname: String,
        gender: String,
        birthDate: String,
        profileImagePath: String?,
    ): Result<Unit> = withContext(ioDispatcher) {
        val request = OnboardingRequest(
            nickname = nickname,
            gender = gender,
            birthDate = birthDate,
            profileImagePath = profileImagePath,
        )
        when (val response = safeApiCall { userService.completeOnboarding(request) }) {
            is ApiResponse.Success -> Result.success(Unit)
            is ApiResponse.Failure -> Result.failure(
                WitException.HttpException(response.code, response.message)
            )
            is ApiResponse.NetworkError -> Result.failure(
                WitException.NetworkException(response.throwable)
            )
        }
    }

    override suspend fun getMyInfo(): Result<UserInfo> = withContext(ioDispatcher) {
        when (val response = safeApiCall { userService.getMyInfo() }) {
            is ApiResponse.Success -> Result.success(response.data.data!!.toDomain())
            is ApiResponse.Failure -> Result.failure(
                WitException.HttpException(response.code, response.message)
            )
            is ApiResponse.NetworkError -> Result.failure(
                WitException.NetworkException(response.throwable)
            )
        }
    }
}
