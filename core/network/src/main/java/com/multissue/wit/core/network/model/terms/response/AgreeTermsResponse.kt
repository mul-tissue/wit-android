package com.multissue.wit.core.network.model.terms.response

import kotlinx.serialization.Serializable

@Serializable
data class AgreeTermsResponse(
    val userStatus: String,
)
