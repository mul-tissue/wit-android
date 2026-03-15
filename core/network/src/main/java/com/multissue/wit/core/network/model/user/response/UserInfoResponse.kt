package com.multissue.wit.core.network.model.user.response

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val nickname: String,
    val profileImagePath: String?,
    val age: String,
    val gender: String,
    val isOwner: Boolean,
)
