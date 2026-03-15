package com.multissue.wit.core.domain.model.auth

data class LoginResult(
    val status: AuthStatus,
    val nickname: String?,
    val profileImagePath: String?,
)
