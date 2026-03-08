package com.multissue.wit.core.data.mapper

import com.multissue.wit.core.domain.model.feed.Author
import com.multissue.wit.core.domain.model.feed.DistrictInfo
import com.multissue.wit.core.domain.model.feed.FeedDistrictFeeds
import com.multissue.wit.core.domain.model.feed.FeedItem
import com.multissue.wit.core.network.model.feed.response.FeedDistrictFeedsResponse
import com.multissue.wit.core.network.model.feed.response.FeedItemResponse

fun FeedDistrictFeedsResponse.toDomain() = FeedDistrictFeeds(
    district = DistrictInfo(
        districtId = district.districtId,
        districtName = district.districtName,
        totalFeedCount = district.totalFeedCount,
    ),
    feeds = feeds.map { it.toDomain() },
    isLast = isLast,
)

private fun FeedItemResponse.toDomain() = FeedItem(
    feedId = feedId,
    latitude = latitude,
    longitude = longitude,
    images = images,
    content = content,
    author = Author(
        userId = author.userId,
        nickname = author.nickname,
        profileImagePath = author.profileImagePath,
    ),
    createdAt = createdAt,
)
