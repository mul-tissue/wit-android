package com.multissue.wit.feature.mypage.state.profileedit

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

enum class NicknameCheckState {
    NONE, AVAILABLE, UNAVAILABLE
}

data class ProfileEditUiState(
    val profileImageUrl: String = "",
    val nickname: String = "",
    val nicknameCheckState: NicknameCheckState = NicknameCheckState.NONE,
    val showPhotoBottomSheet: Boolean = false,
) : UiState

interface ProfileEditUiSideEffect : UiSideEffect

sealed interface ProfileEditUiIntent : UiIntent {
    data class UpdateNickname(val nickname: String) : ProfileEditUiIntent
    data object ClickNicknameCheck : ProfileEditUiIntent
    data object ClickSave : ProfileEditUiIntent
    data object ClickProfileImage : ProfileEditUiIntent
    data object DismissPhotoBottomSheet : ProfileEditUiIntent
    data object ClickSelectFromAlbum : ProfileEditUiIntent
    data object ClickDeleteProfilePhoto : ProfileEditUiIntent
}
