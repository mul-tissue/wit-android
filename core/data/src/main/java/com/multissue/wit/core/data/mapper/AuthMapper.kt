package com.multissue.wit.core.data.mapper

import com.multissue.wit.core.domain.model.auth.AuthStatus
import com.multissue.wit.core.domain.model.auth.LoginResult
import com.multissue.wit.core.network.model.auth.response.SocialLoginResponse

fun SocialLoginResponse.toDomain() = LoginResult(
    status = runCatching {
        AuthStatus.valueOf(status)
    }.getOrDefault(AuthStatus.PENDING_AGREEMENT),
    nickname = nickname,
    profileImagePath = profileImagePath,
)
