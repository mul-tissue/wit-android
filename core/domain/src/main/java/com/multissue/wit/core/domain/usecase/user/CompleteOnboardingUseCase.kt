package com.multissue.wit.core.domain.usecase.user

import com.multissue.wit.core.domain.repository.UserRepository
import javax.inject.Inject

class CompleteOnboardingUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        nickname: String,
        gender: String,
        birthDate: String,
        profileImagePath: String? = null,
    ): Result<Unit> = userRepository.completeOnboarding(
        nickname = nickname,
        gender = gender,
        birthDate = birthDate,
        profileImagePath = profileImagePath,
    )
}
