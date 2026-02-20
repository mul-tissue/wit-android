package com.multissue.wit.feature.map.state.travel

data class TravelState(
    val travelItem: List<TravelItemState> = listOf()
)

data class TravelItemState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val activityType: String = "",
    val meetingDate: String = "",
    val dayDiff: Int = 0,
    val location: String = "",
    val maxParticipants: Int,
    val currentParticipants: Int,
    val companionThumbnails: List<String> = listOf()
)