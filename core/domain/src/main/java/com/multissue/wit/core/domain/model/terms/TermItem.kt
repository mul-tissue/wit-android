package com.multissue.wit.core.domain.model.terms

data class TermItem(
    val id: String,
    val type: String,
    val title: String,
    val required: Boolean,
    val version: String,
    val contentUrl: String,
)
