package com.multissue.wit.core.domain.repository

interface UserRepository {
    suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean>
}
