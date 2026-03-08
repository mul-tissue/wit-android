package com.multissue.wit.feature.mypage.state.map.travel

import com.google.android.gms.maps.model.LatLng
import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState
import com.multissue.wit.core.ui.travel.state.AmPm
import com.multissue.wit.core.ui.travel.state.PickerHour
import com.multissue.wit.core.ui.travel.state.PickerMinute
import com.multissue.wit.core.ui.travel.state.SearchResultItemState
import com.multissue.wit.core.ui.travel.state.TravelItemState
import com.multissue.wit.core.ui.travel.state.UploadTravelData
import java.time.LocalDate

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
    val content: String = "",
    val ageCondition: String = "",
    val genderCondition: String = "",
    val authorName: String = "",
    val authorAvatarUrl: String = "",
    val address: String = "",
)

fun MapTravelItem.toTravelItemState(): TravelItemState = TravelItemState(
    id = id,
    title = title,
    description = description,
    activityType = activityType,
    meetingDate = meetingDate,
    dayDiff = dayDiff,
    location = location,
    maxParticipants = maxParticipants,
    currentParticipants = currentParticipants,
    companionThumbnails = companionThumbnails,
    content = content,
    ageCondition = ageCondition,
    genderCondition = genderCondition,
    authorName = authorName,
    authorAvatarUrl = authorAvatarUrl,
    lat = latLng.latitude,
    lng = latLng.longitude,
    address = address,
)

data class MapTravelUiState(
    val selectedCityName: String = "",
    val travelList: List<MapTravelItem> = emptyList(),

    // TravelPostCard
    val selectedTravelItem: TravelItemState? = null,
    val showTravelPostCard: Boolean = false,

    // Option / Delete
    val showOptionSheet: Boolean = false,
    val showDeleteConfirmDialog: Boolean = false,

    // Edit / Upload
    val showPostConfirmSheet: Boolean = false,
    val isEditMode: Boolean = false,
    val uploadData: UploadTravelData = UploadTravelData(),
    val uploadInitialPage: Int = 0,

    val showUploadActivityTypeSheet: Boolean = false,
    val showUploadDateSelectionSheet: Boolean = false,
    val showUploadCalendarDialog: Boolean = false,
    val showSelectTimeDialog: Boolean = false,
    val draftUploadDate: LocalDate? = null,

    val showLocationSheet: Boolean = false,
    val showSearchScreen: Boolean = false,
    val searchText: String = "",
    val searchResults: List<SearchResultItemState> = emptyList(),
    val selectedSearchResult: SearchResultItemState? = null,

    val ageOptions: List<String> = emptyList(),
    val genderOptions: List<String> = emptyList(),
) : UiState

sealed interface MapTravelUiSideEffect : UiSideEffect {
    data class NavigateToLocation(val latLng: LatLng) : MapTravelUiSideEffect
    data object ShowDeletedSnackbar : MapTravelUiSideEffect
}

sealed interface MapTravelUiIntent : UiIntent {
    data class LoadTravel(val cityName: String) : MapTravelUiIntent
    data class ClickTravelItem(val id: Int) : MapTravelUiIntent
    data class ShowTravelPostCard(val id: Int) : MapTravelUiIntent
    data object HideTravelPostCard : MapTravelUiIntent
    data object ShowOptionSheet : MapTravelUiIntent
    data object HideOptionSheet : MapTravelUiIntent
    data object ShowDeleteConfirmDialog : MapTravelUiIntent
    data object HideDeleteConfirmDialog : MapTravelUiIntent
    data object ConfirmDelete : MapTravelUiIntent
    data object ShowEditMode : MapTravelUiIntent
    data object ConfirmEdit : MapTravelUiIntent
    data object EditTypeSection : MapTravelUiIntent
    data object EditScheduleSection : MapTravelUiIntent
    data object EditLocationSection : MapTravelUiIntent
    data object EditWriteSection : MapTravelUiIntent
    data object HideUploadActivityTypeSheet : MapTravelUiIntent
    data class SelectUploadActivityType(val activityType: String) : MapTravelUiIntent
    data class SelectUploadParticipants(val count: Int) : MapTravelUiIntent
    data class SelectUploadAge(val age: String) : MapTravelUiIntent
    data class SelectUploadGender(val gender: String) : MapTravelUiIntent
    data object ResetUploadConditions : MapTravelUiIntent
    data class UpdateUploadTitle(val title: String) : MapTravelUiIntent
    data class UpdateUploadContent(val content: String) : MapTravelUiIntent
    data object ConfirmUpload : MapTravelUiIntent
    data object ShowUploadDateSelectionSheet : MapTravelUiIntent
    data object HideUploadDateSelectionSheet : MapTravelUiIntent
    data class DraftSelectUploadDate(val date: LocalDate) : MapTravelUiIntent
    data object DraftResetUploadDate : MapTravelUiIntent
    data object ConfirmUploadDate : MapTravelUiIntent
    data object ShowUploadCalendarDialog : MapTravelUiIntent
    data object HideUploadCalendarDialog : MapTravelUiIntent
    data object HideSelectTimeDialog : MapTravelUiIntent
    data class ConfirmTime(val amPm: AmPm, val hour: PickerHour, val minute: PickerMinute) : MapTravelUiIntent
    data object ConfirmTimeUndecided : MapTravelUiIntent
    data object HideLocationSheet : MapTravelUiIntent
    data object BackFromLocationSheet : MapTravelUiIntent
    data object SkipLocation : MapTravelUiIntent
    data object ConfirmLocation : MapTravelUiIntent
    data object HideSearchScreen : MapTravelUiIntent
    data class UpdateSearchText(val text: String) : MapTravelUiIntent
    data class SelectSearchResult(val item: SearchResultItemState) : MapTravelUiIntent
    data object ConfirmSearchResult : MapTravelUiIntent
    data object PostConfirmBack : MapTravelUiIntent
    data object PostConfirmClose : MapTravelUiIntent
}
