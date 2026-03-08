package com.multissue.wit.core.ui.travel.state

import java.time.LocalDate

data class TravelItemState(
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
    val content: String = "",
    val ageCondition: String = "",
    val genderCondition: String = "",
    val authorName: String = "",
    val authorAvatarUrl: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val address: String = "",
)

data class UploadTravelData(
    val activityType: String = "",
    val maxParticipants: Int = 0,
    val ageCondition: String = "",
    val genderCondition: String = "",
    val meetingDate: LocalDate? = null,
    val isTimeUndecided: Boolean = false,
    val amPm: AmPm = AmPm.AM,
    val hour: PickerHour = PickerHour.NINE,
    val minute: PickerMinute = PickerMinute.ZERO,
    val title: String = "",
    val content: String = "",
    val location: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val schedule: String = "",  // "M월 D일 · HH:MM AM/PM" 또는 "M월 D일 · 미정"
)

data class SearchResultItemState(
    val id: Int,
    val name: String,
    val address: String,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
)
