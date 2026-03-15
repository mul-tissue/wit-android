package com.multissue.wit.core.domain.usecase.auth

import com.multissue.wit.core.domain.repository.AuthRepository
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): Boolean = authRepository.hasAccessToken()
}
