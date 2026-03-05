package com.multissue.wit.feature.mypage.state.map.travel

import com.google.android.gms.maps.model.LatLng
import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

data class MapTravelItem(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val activityType: String = "",
    val meetingDate: String = "",
    val dayDiff: Int = 0,
    val location: String = "",
    val maxParticipants: Int = 0,
    val currentParticipants: Int = 0,
    val companionThumbnails: List<String> = emptyList(),
    val latLng: LatLng = LatLng(0.0, 0.0),
)

data class MapTravelUiState(
    val selectedCityName: String = "",
    val travelList: List<MapTravelItem> = emptyList(),
) : UiState

sealed interface MapTravelUiSideEffect : UiSideEffect {
    data class NavigateToLocation(val latLng: LatLng) : MapTravelUiSideEffect
}

sealed interface MapTravelUiIntent : UiIntent {
    data class LoadTravel(val cityName: String) : MapTravelUiIntent
    data class ClickTravelItem(val id: Int) : MapTravelUiIntent
}
