package com.multissue.wit.core.network.model.terms.request

import kotlinx.serialization.Serializable

@Serializable
data class AgreeTermsRequest(
    val terms: List<TermAgreement>,
)

@Serializable
data class TermAgreement(
    val termId: String,
    val agreed: Boolean,
)
