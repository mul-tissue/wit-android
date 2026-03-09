package com.multissue.wit.core.domain.repository

import com.multissue.wit.core.domain.model.auth.LoginResult
import com.multissue.wit.core.domain.model.auth.SocialType

interface AuthRepository {
    suspend fun socialLogin(socialType: SocialType, token: String): Result<LoginResult>
}
