package com.multissue.wit.core.domain.model.feed

data class FeedDistrictFeeds(
    val district: DistrictInfo,
    val feeds: List<FeedItem>,
    val isLast: Boolean,
)

data class DistrictInfo(
    val districtId: String,
    val districtName: String,
    val totalFeedCount: Long,
)

data class FeedItem(
    val feedId: String,
    val latitude: Double,
    val longitude: Double,
    val images: List<String>,
    val content: String,
    val author: Author,
    val createdAt: String,
)

data class Author(
    val userId: String,
    val nickname: String,
    val profileImagePath: String,
)
