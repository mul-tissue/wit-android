package com.multissue.wit.feature.mypage

import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.mypage.state.profileedit.NicknameCheckState
import com.multissue.wit.feature.mypage.state.profileedit.ProfileEditUiIntent
import com.multissue.wit.feature.mypage.state.profileedit.ProfileEditUiSideEffect
import com.multissue.wit.feature.mypage.state.profileedit.ProfileEditUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor() :
    BaseViewModel<ProfileEditUiState, ProfileEditUiSideEffect, ProfileEditUiIntent>(ProfileEditUiState()) {

    override fun onIntent(intent: ProfileEditUiIntent) {
        when (intent) {
            is ProfileEditUiIntent.UpdateNickname -> {
                setState { copy(nickname = intent.nickname, nicknameCheckState = NicknameCheckState.NONE) }
            }
            ProfileEditUiIntent.ClickNicknameCheck -> {
                // TODO: 닉네임 중복 확인 API 호출
                val isAvailable = currentState.nickname.length in 2..12
                        && currentState.nickname != "parkparki"
                setState {
                    copy(nicknameCheckState = if (isAvailable) NicknameCheckState.AVAILABLE else NicknameCheckState.UNAVAILABLE)
                }
            }
            ProfileEditUiIntent.ClickSave -> {
                // TODO: 프로필 저장 API 호출
            }
            ProfileEditUiIntent.ClickProfileImage -> {
                setState { copy(showPhotoBottomSheet = true) }
            }
            ProfileEditUiIntent.DismissPhotoBottomSheet -> {
                setState { copy(showPhotoBottomSheet = false) }
            }
            ProfileEditUiIntent.ClickSelectFromAlbum -> {
                setState { copy(showPhotoBottomSheet = false) }
                postSideEffect(ProfileEditUiSideEffect.LaunchPhotoPicker)
            }
            ProfileEditUiIntent.ClickDeleteProfilePhoto -> {
                setState { copy(showPhotoBottomSheet = false, profileImageUrl = "", selectedImageUri = null) }
            }
            is ProfileEditUiIntent.SelectImage -> {
                setState { copy(selectedImageUri = intent.uri) }
            }
        }
    }

    init {
        // TODO: repository에서 현재 유저 정보 가져오기
        setState {
            copy(
                profileImageUrl = "https://picsum.photos/id/60/400/400",
                nickname = "parkparki",
            )
        }
    }
}
