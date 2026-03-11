package com.multissue.wit.core.data.repository

import com.multissue.wit.core.domain.exception.WitException
import com.multissue.wit.core.domain.repository.UserRepository
import com.multissue.wit.core.network.Dispatcher
import com.multissue.wit.core.network.WitDispatchers
import com.multissue.wit.core.network.model.ApiResponse
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
}
