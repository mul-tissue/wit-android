package com.multissue.wit.core.network.service

import com.multissue.wit.core.network.model.location.response.MyDistrictResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {

    @GET("v1/locations/my-district")
    suspend fun getMyDistrict(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
    ): MyDistrictResponse
}
