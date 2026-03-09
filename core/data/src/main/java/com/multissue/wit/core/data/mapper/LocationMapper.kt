package com.multissue.wit.core.data.mapper

import com.multissue.wit.core.domain.model.location.Bounds
import com.multissue.wit.core.domain.model.location.MyDistrict
import com.multissue.wit.core.network.model.location.response.MyDistrictResponse

fun MyDistrictResponse.toDomain() = MyDistrict(
    countryCode = countryCode,
    country = country,
    city = city,
    districtId = districtId,
    districtName = districtName,
    centerLat = centerLat,
    centerLng = centerLng,
    bounds = Bounds(
        swLat = bounds.swLat,
        swLng = bounds.swLng,
        neLat = bounds.neLat,
        neLng = bounds.neLng,
    ),
    zoomLevel = zoomLevel,
)
