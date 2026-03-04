package com.multissue.wit.feature.upload.state

import android.net.Uri
import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

data class UploadUiState(
    val pageType: PageType = PageType.CAMERA,
    val capturedImageUri: Uri? = null,
    val capturedLocation: String? = null,
    val capturedAt: Long? = null,
    val contentText: String = "",
    val noticeBottomSheetState: Boolean = false,
): UiState

interface UploadUiSideEffect : UiSideEffect

sealed interface UploadUiIntent: UiIntent {
    data object NavigateCamera: UploadUiIntent
    data object NavigateWrite: UploadUiIntent
    data class CapturePhoto(
        val uri: Uri?,
        val location: String?,
        val capturedAt: Long
    ): UploadUiIntent
    data class ContentTextChange(val text: String): UploadUiIntent
    data object DoneButtonClick: UploadUiIntent
    data object SheetCloseButtonClick: UploadUiIntent
    data object SheetConfirmButtonClick: UploadUiIntent
}
