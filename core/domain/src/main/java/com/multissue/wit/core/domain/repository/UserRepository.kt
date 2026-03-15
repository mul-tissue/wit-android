package com.multissue.wit.core.domain.repository

import com.multissue.wit.core.domain.model.user.UserInfo

interface UserRepository {
    suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean>
    suspend fun completeOnboarding(
        nickname: String,
        gender: String,
        birthDate: String,
        profileImagePath: String?,
    ): Result<Unit>
    suspend fun getMyInfo(): Result<UserInfo>
}
