package com.multissue.wit.feature.mypage

import androidx.lifecycle.viewModelScope
import com.multissue.wit.core.domain.exception.toUserMessage
import com.multissue.wit.core.domain.usecase.auth.LogoutUseCase
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.mypage.state.UserInfoState
import com.multissue.wit.feature.mypage.state.settings.SettingsUiIntent
import com.multissue.wit.feature.mypage.state.settings.SettingsUiSideEffect
import com.multissue.wit.feature.mypage.state.settings.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
) :
    BaseViewModel<SettingsUiState, SettingsUiSideEffect, SettingsUiIntent>(SettingsUiState()) {

    override fun onIntent(intent: SettingsUiIntent) {
        when (intent) {
            SettingsUiIntent.ClickProfileEdit -> {
                postSideEffect(SettingsUiSideEffect.NavigateToProfileEdit)
            }
            SettingsUiIntent.TogglePushNotification -> {
                setState { copy(pushNotificationEnabled = !pushNotificationEnabled) }
            }
            SettingsUiIntent.ClickLogout -> {
                setState { copy(showLogoutDialog = true) }
            }
            SettingsUiIntent.DismissLogoutDialog -> {
                setState { copy(showLogoutDialog = false) }
            }
            SettingsUiIntent.ConfirmLogout -> {
                setState { copy(showLogoutDialog = false) }
                viewModelScope.launch {
                    logoutUseCase()
                        .onSuccess { postSideEffect(SettingsUiSideEffect.NavigateToAuth) }
                        .onFailure { setState { copy(errorMessage = it.toUserMessage()) } }
                }
            }
            SettingsUiIntent.ClickWithdraw -> {
                setState { copy(showWithdrawDialog = true) }
            }
            SettingsUiIntent.DismissWithdrawDialog -> {
                setState { copy(showWithdrawDialog = false) }
            }
            SettingsUiIntent.ConfirmWithdraw -> {
                setState { copy(showWithdrawDialog = false, showWithdrawCompleteDialog = true) }
                // TODO 회원 탈퇴 API 호출
            }
            SettingsUiIntent.DismissWithdrawCompleteDialog -> {
                setState { copy(showWithdrawCompleteDialog = false) }
                // TODO 탈퇴 완료 후 로그인 화면 이동
            }
            SettingsUiIntent.DismissErrorDialog -> {
                setState { copy(errorMessage = null) }
            }
        }
    }

    init {
        // TODO: repository에서 유저 정보 가져오기
        setState {
            copy(
                userInfo = UserInfoState(
                    profileImageUrl = "https://picsum.photos/id/60/400/400",
                    name = "parkparki",
                    age = "20대",
                    gender = "여자",
                )
            )
        }
    }
}
