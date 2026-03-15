package com.multissue.wit.core.network.model.user.response

import kotlinx.serialization.Serializable

@Serializable
data class OnboardingResponse(
    val userStatus: String,
    val nickname: String,
    val profileImagePath: String? = null,
)
