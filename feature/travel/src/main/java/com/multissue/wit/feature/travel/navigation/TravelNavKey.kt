package com.multissue.wit.feature.travel.navigation

import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class TravelNavKey(
    val travelId: Int,
) : NavKey

fun Navigator.navigateToTravel(travelId: Int) {
    navigate(TravelNavKey(travelId))
}
