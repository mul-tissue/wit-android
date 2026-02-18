package com.multissue.wit.feature.map.util.location

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

fun getCurrentLocation(
    context: Context,
    onLocation: (LatLng) -> Unit,
    onFailure: (Exception) -> Unit = {}
) {
    val client = LocationServices.getFusedLocationProviderClient(context)

    try {
        client.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onLocation(LatLng(location.latitude, location.longitude))
                } else {
                    onFailure(IllegalStateException("Location is null"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    } catch (e: SecurityException) {
        onFailure(e)
    }
}