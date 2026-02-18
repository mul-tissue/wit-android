package com.multissue.wit.feature.map.util.location

import android.location.Location
import com.google.android.gms.maps.model.LatLng

fun distanceMeters(a: LatLng, b: LatLng): Float {
    val result = FloatArray(1)
    Location.distanceBetween(
        a.latitude, a.longitude,
        b.latitude, b.longitude,
        result
    )
    return result[0]
}