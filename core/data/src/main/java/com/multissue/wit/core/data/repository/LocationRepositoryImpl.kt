package com.multissue.wit.core.data.repository

import com.multissue.wit.core.data.mapper.toDomain
import com.multissue.wit.core.domain.exception.WitException
import com.multissue.wit.core.domain.model.location.MyDistrict
import com.multissue.wit.core.domain.repository.LocationRepository
import com.multissue.wit.core.network.Dispatcher
import com.multissue.wit.core.network.WitDispatchers
import com.multissue.wit.core.network.model.ApiResponse
import com.multissue.wit.core.network.service.LocationService
import com.multissue.wit.core.network.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationService: LocationService,
    @Dispatcher(WitDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : LocationRepository {

    override suspend fun getMyDistrict(
        lat: Double,
        lng: Double,
    ): Result<MyDistrict> = withContext(ioDispatcher) {
        when (val response = safeApiCall { locationService.getMyDistrict(lat, lng) }) {
            is ApiResponse.Success -> Result.success(response.data.toDomain())
            is ApiResponse.Failure -> Result.failure(WitException.HttpException(response.code, response.message))
            is ApiResponse.NetworkError -> Result.failure(WitException.NetworkException(response.throwable))
        }
    }
}
