package com.multissue.wit.feature.map.navigation

import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class MapNavKey(
    val latitude: Double? = null,
    val longitude: Double? = null
) : NavKey

fun Navigator.navigateToMap(
    latitude: Double? = null,
    longitude: Double? = null,
) {
    navigate(MapNavKey(latitude, longitude))
}