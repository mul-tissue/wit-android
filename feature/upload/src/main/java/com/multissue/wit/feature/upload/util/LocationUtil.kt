package com.multissue.wit.feature.upload.util

import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun getLocationAddress(context: Context): String? {
    val location = suspendCoroutine<Location?> { continuation ->
        try {
            LocationServices.getFusedLocationProviderClient(context).lastLocation
                .addOnSuccessListener { continuation.resume(it) }
                .addOnFailureListener { continuation.resume(null) }
        } catch (e: SecurityException) {
            continuation.resume(null)
        }
    } ?: return null

    return withContext(Dispatchers.IO) {
        try {
            @Suppress("DEPRECATION")
            val addresses = Geocoder(context, Locale.getDefault())
                .getFromLocation(location.latitude, location.longitude, 1)
            val address = addresses?.firstOrNull() ?: return@withContext null
            val district = address.subLocality ?: address.subAdminArea ?: address.locality
            val region = address.adminArea
            when {
                district != null && region != null -> "$district, $region"
                else -> district ?: region
            }?.takeIf { it.isNotBlank() }
        } catch (e: Exception) {
            null
        }
    }
}
