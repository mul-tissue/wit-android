package com.multissue.wit.core.domain.repository

import com.multissue.wit.core.domain.model.location.MyDistrict

interface LocationRepository {

    suspend fun getMyDistrict(
        lat: Double,
        lng: Double,
    ): Result<MyDistrict>
}
