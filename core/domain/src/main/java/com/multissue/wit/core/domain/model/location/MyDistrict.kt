package com.multissue.wit.core.domain.model.location

data class MyDistrict(
    val countryCode: String,
    val country: String,
    val city: String,
    val districtId: String,
    val districtName: String,
    val centerLat: Double,
    val centerLng: Double,
    val bounds: Bounds,
    val zoomLevel: Int,
)

data class Bounds(
    val swLat: Double,
    val swLng: Double,
    val neLat: Double,
    val neLng: Double,
)
