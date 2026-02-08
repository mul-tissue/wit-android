package com.multissue.wit.feature.map.util.location

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

fun getCurrentLocation(
    context: Context,
    onLocation: (LatLng) -> Unit
) {
    val client = LocationServices.getFusedLocationProviderClient(context)

    client.lastLocation.addOnSuccessListener { location ->
        location?.let {
            onLocation(LatLng(it.latitude, it.longitude))
        }
    }
}