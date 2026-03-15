package com.multissue.wit.core.data.mapper

import com.multissue.wit.core.domain.model.terms.TermItem
import com.multissue.wit.core.network.model.terms.response.TermItemResponse

fun TermItemResponse.toDomain() = TermItem(
    id = id,
    type = type,
    title = title,
    required = required,
    version = version,
    contentUrl = contentUrl,
)
