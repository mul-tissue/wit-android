package com.multissue.wit.core.domain.model.auth

data class LoginResult(
    val status: AuthStatus,
    // TODO: 서버 응답에 따라 필요한 필드 추가 (nickname, profileImagePath 등)
)
