package com.multissue.wit.core.data.mapper

import com.multissue.wit.core.domain.model.user.UserInfo
import com.multissue.wit.core.network.model.user.response.UserInfoResponse

fun UserInfoResponse.toDomain() = UserInfo(
    nickname = nickname,
    profileImagePath = profileImagePath,
    age = age,
    gender = gender,
    isOwner = isOwner,
)
