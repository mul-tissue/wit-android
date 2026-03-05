package com.multissue.wit.feature.mypage

import com.google.android.gms.maps.model.LatLng
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelItem
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiIntent
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiSideEffect
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapTravelViewModel @Inject constructor() :
    BaseViewModel<MapTravelUiState, MapTravelUiSideEffect, MapTravelUiIntent>(MapTravelUiState()) {

    override fun onIntent(intent: MapTravelUiIntent) {
        when (intent) {
            is MapTravelUiIntent.LoadTravel -> {
                setState {
                    copy(
                        selectedCityName = intent.cityName,
                        travelList = travelListByCity(intent.cityName),
                    )
                }
            }
            is MapTravelUiIntent.ClickTravelItem -> {
                val item = currentState.travelList.find { it.id == intent.id } ?: return
                postSideEffect(MapTravelUiSideEffect.NavigateToLocation(item.latLng))
            }
        }
    }

    private fun travelListByCity(cityName: String): List<MapTravelItem> = when (cityName) {
        "파리" -> listOf(
            MapTravelItem(
                id = 1,
                title = "에펠탑 같이 가요",
                description = "에펠탑 앞에서 인생샷 찍어드림",
                activityType = "관광",
                meetingDate = "3월 15일",
                dayDiff = 10,
                location = "에펠탑",
                maxParticipants = 4,
                currentParticipants = 2,
                companionThumbnails = listOf("https://picsum.photos/id/64/100/100"),
                latLng = LatLng(48.8584, 2.2945)
            )
        )
        "런던" -> listOf(
            MapTravelItem(
                id = 2,
                title = "빅벤 야경 투어",
                description = "야경 보면서 산책해요",
                activityType = "산책",
                meetingDate = "3월 20일",
                dayDiff = 15,
                location = "빅벤",
                maxParticipants = 2,
                currentParticipants = 1,
                companionThumbnails = listOf("https://picsum.photos/id/66/100/100"),
                latLng = LatLng(51.5007, -0.1246)
            )
        )
        "바르셀로나" -> listOf(
            MapTravelItem(
                id = 3,
                title = "사그라다 파밀리아",
                description = "가우디 투어 하실 분",
                activityType = "관광",
                meetingDate = "4월 5일",
                dayDiff = 30,
                location = "사그라다 파밀리아",
                maxParticipants = 6,
                currentParticipants = 3,
                companionThumbnails = listOf("https://picsum.photos/id/67/100/100"),
                latLng = LatLng(41.4036, 2.1744)
            )
        )
        "로마" -> listOf(
            MapTravelItem(
                id = 4,
                title = "콜로세움 정복",
                description = "로마 역사 탐방",
                activityType = "관광",
                meetingDate = "4월 10일",
                dayDiff = 35,
                location = "콜로세움",
                maxParticipants = 4,
                currentParticipants = 1,
                companionThumbnails = listOf("https://picsum.photos/id/68/100/100"),
                latLng = LatLng(41.8902, 12.4922)
            )
        )
        else -> emptyList()
    }
}
