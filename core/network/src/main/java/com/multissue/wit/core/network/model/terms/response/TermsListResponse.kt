package com.multissue.wit.core.network.model.terms.response

import kotlinx.serialization.Serializable

@Serializable
data class TermsListResponse(
    val terms: List<TermItemResponse>,
)

@Serializable
data class TermItemResponse(
    val id: String,
    val type: String,
    val title: String,
    val required: Boolean,
    val version: String,
    val contentUrl: String,
)
