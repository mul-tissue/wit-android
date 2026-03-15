package com.multissue.wit.core.domain.usecase.user

import com.multissue.wit.core.domain.model.user.UserInfo
import com.multissue.wit.core.domain.repository.UserRepository
import javax.inject.Inject

class GetMyInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<UserInfo> = userRepository.getMyInfo()
}
