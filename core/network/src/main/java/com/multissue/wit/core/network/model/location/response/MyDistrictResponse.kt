package com.multissue.wit.core.network.model.location.response

import kotlinx.serialization.Serializable

@Serializable
data class MyDistrictResponse(
    val countryCode: String,
    val country: String,
    val city: String,
    val districtId: String,
    val districtName: String,
    val centerLat: Double,
    val centerLng: Double,
    val bounds: BoundsResponse,
    val zoomLevel: Int,
)

@Serializable
data class BoundsResponse(
    val swLat: Double,
    val swLng: Double,
    val neLat: Double,
    val neLng: Double,
)
