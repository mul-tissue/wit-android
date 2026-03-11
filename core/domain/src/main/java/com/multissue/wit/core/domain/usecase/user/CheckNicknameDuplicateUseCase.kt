package com.multissue.wit.core.domain.usecase.user

import com.multissue.wit.core.domain.repository.UserRepository
import javax.inject.Inject

class CheckNicknameDuplicateUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(nickname: String): Result<Boolean> =
        userRepository.checkNicknameDuplicate(nickname)
}
