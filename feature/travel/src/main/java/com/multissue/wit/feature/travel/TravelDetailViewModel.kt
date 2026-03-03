package com.multissue.wit.feature.travel

import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.travel.navigation.TravelNavKey
import com.multissue.wit.feature.travel.state.ReportType
import com.multissue.wit.feature.travel.state.TravelDetailSideEffect
import com.multissue.wit.feature.travel.state.TravelDetailUiIntent
import com.multissue.wit.feature.travel.state.TravelDetailUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = TravelDetailViewModel.Factory::class)
class TravelDetailViewModel @AssistedInject constructor(
    @Assisted private val navKey: TravelNavKey,
) : BaseViewModel<TravelDetailUiState, TravelDetailSideEffect, TravelDetailUiIntent>(
    initialState = TravelDetailUiState()
) {
    val travelId: Int = navKey.travelId

    init {
        fetchTravelDetail()
    }

    override fun onIntent(intent: TravelDetailUiIntent) {
        when (intent) {
            is TravelDetailUiIntent.JoinChat -> onJoinChat()
            is TravelDetailUiIntent.ShowReportBottomSheet -> onShowReportBottomSheet()
            is TravelDetailUiIntent.HideReportBottomSheet -> onHideReportBottomSheet()
            is TravelDetailUiIntent.ShowReportDialog -> onShowReportDialog()
            is TravelDetailUiIntent.HideReportDialog -> onHideReportDialog()
            is TravelDetailUiIntent.SelectReportType -> onSelectReportType(intent.reportType)
            is TravelDetailUiIntent.SubmitReport -> onSubmitReport()
        }
    }

    private fun onShowReportBottomSheet() {
        setState { copy(isReportBottomSheetVisible = true) }
    }

    private fun onHideReportBottomSheet() {
        setState { copy(isReportBottomSheetVisible = false) }
    }

    private fun onShowReportDialog() {
        setState { copy(isReportBottomSheetVisible = false, isReportDialogVisible = true) }
    }

    private fun onHideReportDialog() {
        setState { copy(isReportDialogVisible = false, selectedReportType = null) }
    }

    private fun onSelectReportType(reportType: ReportType) {
        setState { copy(selectedReportType = reportType) }
    }

    private fun onSubmitReport() {
        if (currentState.selectedReportType == null) return
        // TODO: 신고 API 호출
        setState { copy(isReportDialogVisible = false, selectedReportType = null) }
        postSideEffect(TravelDetailSideEffect.ShowReportSnackbar)
    }

    private fun fetchTravelDetail() {
        // TODO: API 연결
        setState {
            copy(
                title = "루브르 미술관 함께 투어할 동행 구해요",
                content = "1월 말 파리 여행 중 루브르 미술관을 같이 둘러볼 동행을 구합니다. 혼자 보기엔 아쉬워서 천천히 작품 보며 이야기 나눌 분이면 좋아요. 부담 없이 편한 분위기로 관람하고 싶어요!",
                activityType = "전시/미술관",
                meetingDate = "3월 25일 · 06:00 PM",
                dayDiff = 3,
                location = "루브르 미술관",
                maxParticipants = 5,
                currentParticipants = 1,
                ageCondition = "20대",
                genderCondition = "남자",
                authorName = "여행자",
                authorAvatarUrl = "",
                travelUserName = "Parprika",
                companionThumbnails = listOf("", ""),
                lat = 48.8606,
                lng = 2.3376,
                address = "Rue de Rivoli, 75001 Paris, France",
                isLoading = false,
            )
        }
    }

    private fun onJoinChat() {
        // TODO: 채팅 참여 API 호출
        postSideEffect(TravelDetailSideEffect.NavigateToChatRoom(travelId))
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: TravelNavKey): TravelDetailViewModel
    }
}
