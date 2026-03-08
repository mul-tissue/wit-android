package com.multissue.wit.core.domain.repository

import com.multissue.wit.core.domain.model.feed.FeedDistrictFeeds

interface FeedRepository {

    suspend fun getDistrictFeeds(
        districtId: String,
        lastFeedId: String? = null,
        size: Int? = null,
    ): Result<FeedDistrictFeeds>
}
