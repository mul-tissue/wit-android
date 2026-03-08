package com.multissue.wit.feature.mypage.state.map.feed

import com.google.android.gms.maps.model.LatLng
import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

data class MapFeedItem(
    val id: String = "",
    val thumbnailUrl: String = "",
    val isLiked: Boolean = false,
    val likeCount: Int = 0,
    val date: String = "",
    val location: String = "",
    val latLng: LatLng = LatLng(0.0, 0.0),
)

data class MapFeedUiState(
    val selectedCityName: String = "",
    val feedList: List<MapFeedItem> = emptyList(),
) : UiState

sealed interface MapFeedUiSideEffect : UiSideEffect {
    data class NavigateToLocation(val latLng: LatLng) : MapFeedUiSideEffect
}

sealed interface MapFeedUiIntent : UiIntent {
    data class LoadFeed(val cityName: String) : MapFeedUiIntent
    data class ClickFeedItem(val id: String) : MapFeedUiIntent
    data class ToggleLike(val id: String) : MapFeedUiIntent
}
