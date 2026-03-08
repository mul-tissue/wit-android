package com.multissue.wit.core.network.service

import com.multissue.wit.core.network.model.feed.response.FeedDistrictFeedsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedService {

    @GET("v1/feeds/districts/{districtId}")
    suspend fun getDistrictFeeds(
        @Path("districtId") districtId: String,
        @Query("lastFeedId") lastFeedId: String? = null,
        @Query("size") size: Int? = null,
    ): FeedDistrictFeedsResponse
}
