package com.multissue.wit.core.domain.usecase.location

import com.multissue.wit.core.domain.model.location.MyDistrict
import com.multissue.wit.core.domain.repository.LocationRepository
import javax.inject.Inject

class GetMyDistrictUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
) {
    suspend operator fun invoke(
        lat: Double,
        lng: Double,
    ): Result<MyDistrict> =
        locationRepository.getMyDistrict(lat, lng)
}
