package com.multissue.wit.core.domain.repository

interface UserRepository {
    suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean>
    suspend fun completeOnboarding(
        nickname: String,
        gender: String,
        birthDate: String,
        profileImagePath: String?,
    ): Result<Unit>
}
