package com.multissue.wit.core.network.model.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginRequest(
    val socialType: String,
    val token: String,
)
