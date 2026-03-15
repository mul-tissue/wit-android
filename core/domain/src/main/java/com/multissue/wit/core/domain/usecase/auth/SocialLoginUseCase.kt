package com.multissue.wit.core.domain.usecase.auth

import com.multissue.wit.core.domain.model.auth.LoginResult
import com.multissue.wit.core.domain.model.auth.SocialType
import com.multissue.wit.core.domain.repository.AuthRepository
import javax.inject.Inject

class SocialLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(socialType: SocialType, token: String): Result<LoginResult> =
        authRepository.socialLogin(socialType, token)
}
