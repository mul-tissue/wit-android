package com.multissue.wit.core.network.model.feed.response

import kotlinx.serialization.Serializable

@Serializable
data class FeedDistrictFeedsResponse(
    val district: DistrictInfoResponse,
    val feeds: List<FeedItemResponse>,
    val isLast: Boolean,
)
@Serializable
data class DistrictInfoResponse(
    val districtId: String,
    val districtName: String,
    val totalFeedCount: Long,
)

@Serializable
data class FeedItemResponse(
    val feedId: String,
    val latitude: Double,
    val longitude: Double,
    val images: List<String>,
    val content: String,
    val author: AuthorInfoResponse,
    val createdAt: String,
)

@Serializable
data class AuthorInfoResponse(
    val userId: String,
    val nickname: String,
    val profileImagePath: String,
)