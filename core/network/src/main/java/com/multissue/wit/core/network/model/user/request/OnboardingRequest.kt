package com.multissue.wit.core.network.model.user.request

import kotlinx.serialization.Serializable

@Serializable
data class OnboardingRequest(
    val nickname: String,
    val gender: String,
    val birthDate: String,
    val profileImagePath: String? = null,
)
