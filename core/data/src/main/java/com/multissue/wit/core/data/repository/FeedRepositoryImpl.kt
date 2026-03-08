package com.multissue.wit.core.data.repository

import com.multissue.wit.core.data.mapper.toDomain
import com.multissue.wit.core.domain.exception.WitException
import com.multissue.wit.core.domain.model.feed.FeedDistrictFeeds
import com.multissue.wit.core.domain.repository.FeedRepository
import com.multissue.wit.core.network.Dispatcher
import com.multissue.wit.core.network.WitDispatchers
import com.multissue.wit.core.network.model.ApiResponse
import com.multissue.wit.core.network.service.FeedService
import com.multissue.wit.core.network.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedService: FeedService,
    @Dispatcher(WitDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : FeedRepository {

    override suspend fun getDistrictFeeds(
        districtId: String,
        lastFeedId: String?,
        size: Int?,
    ): Result<FeedDistrictFeeds> = withContext(ioDispatcher) {
        when (val response = safeApiCall { feedService.getDistrictFeeds(districtId, lastFeedId, size) }) {
            is ApiResponse.Success -> Result.success(response.data.toDomain())
            is ApiResponse.Failure -> Result.failure(WitException.HttpException(response.code, response.message))
            is ApiResponse.NetworkError -> Result.failure(WitException.NetworkException(response.throwable))
        }
    }
}
