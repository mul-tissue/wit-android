package com.multissue.wit.feature.feed.state

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

data class FeedUiState(
    val user: UserState = UserState(),
    val reactionState: ReactionState = ReactionState(),
    val feedState: FeedState = FeedState(),
    val reportState: ReportState = ReportState()
): UiState

interface FeedUiSideEffect: UiSideEffect {
    data object OnDeleteSuccess: FeedUiSideEffect
    data object OnReportSuccess: FeedUiSideEffect
}

sealed interface FeedUiIntent: UiIntent {
    data object Loading : FeedUiIntent

    data class FeedDetail(
        val feedUiState: FeedUiState
    ) : FeedUiIntent

    data object Empty : FeedUiIntent
    data object ClickMoreButton: FeedUiIntent
    data object DismissReportBottomSheet: FeedUiIntent
    data object ClickReportButton: FeedUiIntent
    data object ClickDeleteButton: FeedUiIntent
    data object ClickDialogCancelButton: FeedUiIntent
    data object ClickDialogDeleteButton: FeedUiIntent
    data object ClickDialogReportButton: FeedUiIntent
    data class ClickReportType(
        val type: ReportType
    ): FeedUiIntent
}