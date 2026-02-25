package com.multissue.wit.feature.upload

import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.upload.state.PageType
import com.multissue.wit.feature.upload.state.UploadUiIntent
import com.multissue.wit.feature.upload.state.UploadUiSideEffect
import com.multissue.wit.feature.upload.state.UploadUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(

) : BaseViewModel<UploadUiState, UploadUiSideEffect, UploadUiIntent>(UploadUiState()) {
    override fun onIntent(intent: UploadUiIntent) {
        when (intent) {
            UploadUiIntent.NavigateWrite -> { navigateToWritePage() }
            UploadUiIntent.NavigateCamera -> { navigateToCameraPage() }
            is UploadUiIntent.CapturePhoto -> {
                setState { copy(capturedImageUri = intent.uri, capturedLocation = intent.location, capturedAt = intent.capturedAt) }
                if (intent.uri != null) {
                    navigateToWritePage()
                }
            }
            is UploadUiIntent.ContentTextChange -> {
                setState { copy(contentText = intent.text) }
            }

            UploadUiIntent.DoneButtonClick -> { onDoneButtonClicked() }
            UploadUiIntent.SheetCloseButtonClick -> { dismissNoticeBottomSheet() }
            UploadUiIntent.SheetConfirmButtonClick -> { onConfirmButtonClicked() }
        }
    }

    fun navigateToWritePage() {
        setState { copy(pageType = PageType.WRITE) }
    }

    fun navigateToCameraPage() {
        setState { copy(pageType = PageType.CAMERA, capturedImageUri = null) }
    }

    fun onDoneButtonClicked() {
        setState { copy(noticeBottomSheetState = true) }
    }

    fun onConfirmButtonClicked() {
        setState { copy(noticeBottomSheetState = false) } //TODO
    }

    fun dismissNoticeBottomSheet() {
        setState { copy(noticeBottomSheetState = false) }
    }
}
