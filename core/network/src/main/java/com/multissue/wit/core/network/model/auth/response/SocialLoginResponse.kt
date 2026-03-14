package com.multissue.wit.core.network.model.auth.response

import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginResponse(
    val accessToken: String,
    val accessTokenExpiresAt: Long,
    val refreshToken: String,
    val refreshTokenExpiresAt: Long,
    val status: String,
    val nickname: String? = null,
    val profileImagePath: String? = null,
)
