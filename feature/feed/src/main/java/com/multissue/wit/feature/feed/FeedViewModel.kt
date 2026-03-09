package com.multissue.wit.feature.feed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.multissue.wit.core.domain.exception.WitException
import com.multissue.wit.core.domain.usecase.feed.GetDistrictFeedsUseCase
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.feed.navigation.FeedNavKey
import com.multissue.wit.feature.feed.state.FeedState
import com.multissue.wit.feature.feed.state.FeedUiIntent
import com.multissue.wit.feature.feed.state.FeedUiSideEffect
import com.multissue.wit.feature.feed.state.FeedUiState
import com.multissue.wit.feature.feed.state.ReactionType
import com.multissue.wit.feature.feed.state.ReportType
import com.multissue.wit.feature.feed.state.UserState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = FeedViewModel.Factory::class)
class FeedViewModel @AssistedInject constructor(
    private val savedStateHandle: SavedStateHandle,
    @Assisted val key: FeedNavKey,
    private val getDistrictFeedsUseCase: GetDistrictFeedsUseCase,
) : BaseViewModel<FeedUiState, FeedUiSideEffect, FeedUiIntent>(FeedUiState()) {
    private val selectedFeedIdKey = "selectedFeedIdKey"

    private val selectedFeedId = savedStateHandle.getStateFlow(
        key = selectedFeedIdKey,
        initialValue = key.feedId
    )

    private fun loadDistrictFeeds(districtId: String) {
        viewModelScope.launch {
            getDistrictFeedsUseCase(districtId = districtId)
                .onSuccess { feeds ->
                    // TODO: map to UiState
                }
                .onFailure { throwable ->
                    when (throwable) {
                        is WitException.HttpException -> { /* throwable.code */ }
                        is WitException.NetworkException -> { /* network error */ }
                        else -> { }
                    }
                }
        }
    }

    override fun onIntent(intent: FeedUiIntent) {
        when(intent) {
            FeedUiIntent.Empty -> {  }
            FeedUiIntent.Loading -> {  }
            is FeedUiIntent.FeedDetail -> {
                setState { intent.feedUiState }
            }
            FeedUiIntent.ClickMoreButton -> { onMoreButtonClick() }
            FeedUiIntent.DismissReportBottomSheet -> { dismissReportBottonSheet() }
            FeedUiIntent.ClickReportButton -> { onReportButtonClick() }
            FeedUiIntent.ClickDeleteButton -> { onDeleteButtonClick() }
            FeedUiIntent.ClickDialogCancelButton -> { dismissDialog() }
            FeedUiIntent.ClickDialogDeleteButton -> { onDeleteConfirmButtonClick() }
            FeedUiIntent.ClickDialogReportButton -> { onReportConfirmButtonClick() }
            is FeedUiIntent.ClickReportType -> {
                onSelectReportType(intent.type)
            }
        }
    }

    fun onMoreButtonClick() {
        setState { copy(reportState = reportState.copy(reportBottomSheet = true)) }
    }

    fun dismissReportBottonSheet() {
        setState { copy(reportState = reportState.copy(reportBottomSheet = false)) }
    }

    fun onReportButtonClick() {
        setState { copy(reportState = reportState.copy(reportDialog = true)) }
    }

    fun onDeleteButtonClick() {
        setState { copy(reportState = reportState.copy(deleteDialog = true)) }
    }

    fun onDeleteConfirmButtonClick() {
        setState {
            copy(reportState = reportState.copy(deleteDialog = false, reportBottomSheet = false))
        }
        postSideEffect(FeedUiSideEffect.OnDeleteSuccess) //TODO
    }

    fun onReportConfirmButtonClick() {
        setState {
            copy(reportState = reportState.copy(reportDialog = false, reportBottomSheet = false))
        }
        postSideEffect(FeedUiSideEffect.OnReportSuccess) //TODO
    }

    fun dismissDialog() {
        setState { copy(reportState = reportState.copy(deleteDialog = false, reportDialog = false)) }
    }

    fun onSelectReportType(
        type: ReportType
    ) {
        setState { copy(reportState = reportState.copy(selectedReportType = type)) }
    }

    fun onReactionItemClick(
        type: ReactionType
    ) {
        if (uiState.value.reactionState.selectedReaction == type) {
            setState {
                copy(
                    reactionState = reactionState.copy(
                        selectedReaction = null,
                    )
                )
            }
        } else {
            setState {
                copy(
                    reactionState = reactionState.copy(
                        selectedReaction = type,
                    )
                )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(key: FeedNavKey): FeedViewModel
    }

    fun testInitial() {
        setState {
            copy(
                user = UserState(
                    username = "parkparki",
                    userThumbnailUrl = "https://picsum.photos/id/60/400/400",
                ),
                feedState = FeedState(
                    imageUrl = "https://picsum.photos/id/27/300/400",
                    title = "언니랑 아이스크림 들고 파리 산책 🍦",
                    location = "Effeltower, Paris",
                    date = "April 24, 2024 4:52 PM",
                )
            )
        }
    }

    init {
        testInitial()
    }
}
