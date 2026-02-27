package com.multissue.wit.feature.travel.state

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

data class TravelDetailUiState(
    val title: String = "",
    val content: String = "",
    val activityType: String = "",
    val meetingDate: String = "",
    val dayDiff: Int = 0,
    val location: String = "",
    val maxParticipants: Int = 0,
    val currentParticipants: Int = 0,
    val travelUserName: String =  "",
    val companionThumbnails: List<String> = emptyList(),
    val ageCondition: String = "",
    val genderCondition: String = "",
    val authorName: String = "",
    val authorAvatarUrl: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val address: String = "",
    val isLoading: Boolean = true,
    val isReportBottomSheetVisible: Boolean = false,
    val isReportDialogVisible: Boolean = false,
    val selectedReportType: ReportType? = null,
) : UiState

sealed class TravelDetailUiIntent : UiIntent {
    data object JoinChat : TravelDetailUiIntent()
    data object ShowReportBottomSheet : TravelDetailUiIntent()
    data object HideReportBottomSheet : TravelDetailUiIntent()
    data object ShowReportDialog : TravelDetailUiIntent()
    data object HideReportDialog : TravelDetailUiIntent()
    data class SelectReportType(val reportType: ReportType) : TravelDetailUiIntent()
    data object SubmitReport : TravelDetailUiIntent()
}

sealed interface TravelDetailSideEffect : UiSideEffect {
    data class NavigateToChatRoom(val chatRoomId: Int) : TravelDetailSideEffect
    data object ShowReportSnackbar : TravelDetailSideEffect
}
