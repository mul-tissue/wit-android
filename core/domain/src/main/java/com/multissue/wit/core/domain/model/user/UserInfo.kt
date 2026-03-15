package com.multissue.wit.core.domain.model.user

data class UserInfo(
    val nickname: String,
    val profileImagePath: String?,
    val age: String,
    val gender: String,
    val isOwner: Boolean,
)
