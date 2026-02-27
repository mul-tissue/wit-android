package com.multissue.wit.feature.map.state.travel

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState
import java.time.LocalDate

data class TravelUiState(
    val travelItems: List<TravelItemState> = emptyList(),
    val ageOptions: List<String> = emptyList(),
    val genderOptions: List<String> = emptyList(),

    val selectedActivityType: String = "",
    val selectedAge: String = "",
    val selectedGender: String = "",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,

    val draftActivityType: String = "",
    val draftSelectedAge: String = "",
    val draftSelectedGender: String = "",
    val draftStartDate: LocalDate? = null,
    val draftEndDate: LocalDate? = null,

    val showActivityTypeSheet: Boolean = false,
    val showAgeGenderSheet: Boolean = false,
    val showDateSelectionSheet: Boolean = false,
    val showCalendarDialog: Boolean = false,

    val showUploadDateSelectionSheet: Boolean = false,
    val draftUploadDate: LocalDate? = null,
    val showUploadCalendarDialog: Boolean = false,
    val showSelectTimeDialog: Boolean = false,
    val showUploadActivityTypeSheet: Boolean = false,
    val showLocationSheet: Boolean = false,
    val showPostConfirmSheet: Boolean = false,
    val uploadData: UploadTravelData = UploadTravelData(),
    val showSearchScreen: Boolean = false,
    val searchText: String = "",
    val searchResults: List<SearchResultItemState> = emptyList(),
    val selectedSearchResult: SearchResultItemState? = null,

    val showJoinChatDialog: Boolean = false,
    val pendingChatTravelId: Int? = null,

    val showTravelPostCard: Boolean = false,
    val selectedTravelItem: TravelItemState? = null,

    val showOptionSheet: Boolean = false,
    val showDeleteConfirmDialog: Boolean = false,
    val isEditMode: Boolean = false,
    val uploadInitialPage: Int = 0,
) : UiState {
    val ageAndGenderStr: String
        get() = buildString {
            if (selectedAge.isNotEmpty()) append(selectedAge)
            if (selectedAge.isNotEmpty() && selectedGender.isNotEmpty()) append(" / ")
            if (selectedGender.isNotEmpty()) append(selectedGender)
        }

    val dateStr: String
        get() = buildString {
            if (startDate != null && endDate != null) {
                append("${startDate.monthValue}/${startDate.dayOfMonth} - ${endDate.monthValue}/${endDate.dayOfMonth}")
            } else if (startDate != null) {
                append("${startDate.monthValue}/${startDate.dayOfMonth}")
            }
        }
}

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

data class SearchResultItemState(
    val id: Int,
    val name: String,
    val address: String,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
)

sealed class TravelUiIntent : UiIntent {
    data object ShowActivityTypeSheet : TravelUiIntent()
    data object HideActivityTypeSheet : TravelUiIntent()
    data object ShowAgeGenderSheet : TravelUiIntent()
    data object HideAgeGenderSheet : TravelUiIntent()
    data object ShowDateSelectionSheet : TravelUiIntent()
    data object HideDateSelectionSheet : TravelUiIntent()
    data object ShowCalendarDialog : TravelUiIntent()
    data object HideCalendarDialog : TravelUiIntent()
    data class DraftSelectActivityType(val activityType: String) : TravelUiIntent()
    data class DraftSelectAge(val age: String) : TravelUiIntent()
    data class DraftSelectGender(val gender: String) : TravelUiIntent()
    data class DraftSelectDate(val date: LocalDate) : TravelUiIntent()
    data object DraftResetDate : TravelUiIntent()
    data object ConfirmActivityType : TravelUiIntent()
    data object ConfirmAgeGender : TravelUiIntent()
    data object ConfirmDate : TravelUiIntent()
    data object ClearActivityType : TravelUiIntent()
    data object ClearAgeGender : TravelUiIntent()
    data object ClearDate : TravelUiIntent()
    data object Reload : TravelUiIntent()
    data object ShowUploadDateSelectionSheet : TravelUiIntent()
    data object HideUploadDateSelectionSheet : TravelUiIntent()
    data class DraftSelectUploadDate(val date: LocalDate) : TravelUiIntent()
    data object DraftResetUploadDate : TravelUiIntent()
    data object ConfirmUploadDate : TravelUiIntent()
    data object ShowUploadCalendarDialog : TravelUiIntent()
    data object HideUploadCalendarDialog : TravelUiIntent()
    data object HideSelectTimeDialog : TravelUiIntent()
    data class ConfirmTime(val amPm: AmPm, val hour: PickerHour, val minute: PickerMinute) : TravelUiIntent()
    data object ConfirmTimeUndecided : TravelUiIntent()

    // 업로드 플로우 — 3단계 시트 (활동유형 / 조건 / 글작성)
    data object HideUploadActivityTypeSheet : TravelUiIntent()
    data class SelectUploadActivityType(val activityType: String) : TravelUiIntent()
    data class SelectUploadParticipants(val count: Int) : TravelUiIntent()
    data class SelectUploadAge(val age: String) : TravelUiIntent()
    data class SelectUploadGender(val gender: String) : TravelUiIntent()
    data object ResetUploadConditions : TravelUiIntent()
    data class UpdateUploadTitle(val title: String) : TravelUiIntent()
    data class UpdateUploadContent(val content: String) : TravelUiIntent()
    data object ConfirmUpload : TravelUiIntent()

    // 업로드 플로우 — 위치 추가 시트
    data object HideLocationSheet : TravelUiIntent()
    data object BackFromLocationSheet : TravelUiIntent()
    data class UpdateUploadLocation(val location: String) : TravelUiIntent()
    data object SkipLocation : TravelUiIntent()
    data object ConfirmLocation : TravelUiIntent()

    // 업로드 플로우 — 검색 화면
    data object HideSearchScreen : TravelUiIntent()
    data class UpdateSearchText(val text: String) : TravelUiIntent()
    data class SelectSearchResult(val item: SearchResultItemState) : TravelUiIntent()
    data object ConfirmSearchResult : TravelUiIntent()

    // 업로드 플로우 — 게시 전 확인 시트
    data object PostConfirmBack : TravelUiIntent()
    data object PostConfirmClose : TravelUiIntent()
    data object PostConfirmComplete : TravelUiIntent()

    data class NavigateToTravelDetail(val travelId: Int) : TravelUiIntent()
    data class ShowJoinChatDialog(val travelId: Int) : TravelUiIntent()
    data object HideJoinChatDialog : TravelUiIntent()
    data object ConfirmJoinChat : TravelUiIntent()

    data class ShowTravelPostCard(val travelId: Int) : TravelUiIntent()
    data object HideTravelPostCard : TravelUiIntent()

    data object ShowOptionSheet : TravelUiIntent()
    data object HideOptionSheet : TravelUiIntent()
    data object ShowDeleteConfirmDialog : TravelUiIntent()
    data object HideDeleteConfirmDialog : TravelUiIntent()
    data object ConfirmDelete : TravelUiIntent()

    data object ShowEditMode : TravelUiIntent()
    data object ConfirmEdit : TravelUiIntent()
    data object EditTypeSection : TravelUiIntent()
    data object EditScheduleSection : TravelUiIntent()
    data object EditLocationSection : TravelUiIntent()
    data object EditWriteSection : TravelUiIntent()
}

sealed interface TravelSideEffect : UiSideEffect {
    data class NavigateToDetail(val travelId: Int) : TravelSideEffect
    data class NavigateToChatRoom(val chatRoomId: Int) : TravelSideEffect
    data object ShowDeletedSnackbar : TravelSideEffect
}
