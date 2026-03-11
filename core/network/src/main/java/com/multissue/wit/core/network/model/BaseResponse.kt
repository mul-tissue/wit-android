package com.multissue.wit.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val success: Boolean,
    val status: Int,
    val data: T? = null,
    val timestamp: String,
)
