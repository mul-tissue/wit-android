package com.multissue.wit.core.domain.usecase.feed

import com.multissue.wit.core.domain.model.feed.FeedDistrictFeeds
import com.multissue.wit.core.domain.repository.FeedRepository
import javax.inject.Inject

class GetDistrictFeedsUseCase @Inject constructor(
    private val feedRepository: FeedRepository,
) {
    suspend operator fun invoke(
        districtId: String,
        lastFeedId: String? = null,
        size: Int? = null,
    ): Result<FeedDistrictFeeds> =
        feedRepository.getDistrictFeeds(districtId, lastFeedId, size)
}
