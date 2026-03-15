package com.multissue.wit.feature.mypage.state.settings

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState
import com.multissue.wit.feature.mypage.state.UserInfoState

data class SettingsUiState(
    val userInfo: UserInfoState = UserInfoState(),
    val pushNotificationEnabled: Boolean = true,
    val showLogoutDialog: Boolean = false,
    val showWithdrawDialog: Boolean = false,
    val showWithdrawCompleteDialog: Boolean = false,
    val errorMessage: String? = null,
) : UiState

sealed interface SettingsUiSideEffect : UiSideEffect {
    data object NavigateToProfileEdit : SettingsUiSideEffect
    data object NavigateToAuth : SettingsUiSideEffect
}

sealed interface SettingsUiIntent : UiIntent {
    data object ClickProfileEdit : SettingsUiIntent
    data object TogglePushNotification : SettingsUiIntent
    data object ClickLogout : SettingsUiIntent
    data object DismissLogoutDialog : SettingsUiIntent
    data object ConfirmLogout : SettingsUiIntent
    data object ClickWithdraw : SettingsUiIntent
    data object DismissWithdrawDialog : SettingsUiIntent
    data object ConfirmWithdraw : SettingsUiIntent
    data object DismissWithdrawCompleteDialog : SettingsUiIntent
    data object DismissErrorDialog : SettingsUiIntent
}
