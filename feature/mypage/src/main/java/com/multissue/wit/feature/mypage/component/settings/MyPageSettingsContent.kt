package com.multissue.wit.feature.mypage.component.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multissue.wit.designsystem.component.dialog.WitDialog
import com.multissue.wit.designsystem.component.dialog.WitDialogDefaultLayout
import com.multissue.wit.designsystem.component.dialog.WitErrorDialog
import com.multissue.wit.designsystem.component.dialog.WitDialogMessage
import com.multissue.wit.designsystem.component.dialog.WitDialogOnlyTitle
import com.multissue.wit.designsystem.component.dialog.WitDialogRightButton
import com.multissue.wit.designsystem.component.dialog.WitDialogTitleOnlyLayout
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.mypage.R
import com.multissue.wit.feature.mypage.SettingsViewModel
import com.multissue.wit.feature.mypage.component.SpH
import com.multissue.wit.feature.mypage.state.UserInfoState
import com.multissue.wit.feature.mypage.state.settings.SettingsUiIntent
import com.multissue.wit.feature.mypage.state.settings.SettingsUiSideEffect

@Composable
fun MyPageSettingsContent(
    modifier: Modifier = Modifier,
    onNavigateToProfileEdit: () -> Unit = {},
    onNavigateToAuth: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                SettingsUiSideEffect.NavigateToProfileEdit -> onNavigateToProfileEdit()
                SettingsUiSideEffect.NavigateToAuth -> onNavigateToAuth()
            }
        }
    }

    MyPageSettingsContent(
        modifier = modifier,
        userInfoState = uiState.userInfo,
        pushNotificationEnabled = uiState.pushNotificationEnabled,
        showLogoutDialog = uiState.showLogoutDialog,
        showWithdrawDialog = uiState.showWithdrawDialog,
        showWithdrawCompleteDialog = uiState.showWithdrawCompleteDialog,
        errorMessage = uiState.errorMessage,
        onIntent = viewModel::onIntent,
    )
}

@Composable
internal fun MyPageSettingsContent(
    modifier: Modifier = Modifier,
    userInfoState: UserInfoState,
    pushNotificationEnabled: Boolean,
    showLogoutDialog: Boolean,
    showWithdrawDialog: Boolean,
    showWithdrawCompleteDialog: Boolean,
    errorMessage: String? = null,
    onIntent: (SettingsUiIntent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WitTheme.background.color),
    ) {
        SpH(20.dp)
        SettingsProfileSection(
            userInfoState = userInfoState,
            onClick = { onIntent(SettingsUiIntent.ClickProfileEdit) },
        )
        SpH(20.dp)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(WitTheme.colors.divider)
        )
        SpH(20.dp)
        SettingsItem(
            iconRes = R.drawable.icon_settings_profile,
            label = "프로필 수정",
            trailing = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.icon_chevron_right),
                    contentDescription = null,
                    tint = WitTheme.colors.disabledText,
                )
            },
            onClick = { onIntent(SettingsUiIntent.ClickProfileEdit) },
        )
        HorizontalDivider(color = WitTheme.colors.divider)
        SettingsItem(
            iconRes = com.multissue.wit.designsystem.R.drawable.icon_notification,
            label = "푸시 알람",
            trailing = {
                Switch(
                    checked = pushNotificationEnabled,
                    onCheckedChange = null,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = WitTheme.colors.white100,
                        checkedTrackColor = WitTheme.colors.primary,
                        uncheckedThumbColor = WitTheme.colors.white100,
                        uncheckedTrackColor = WitTheme.colors.gray200,
                        uncheckedBorderColor = WitTheme.colors.gray200,
                    ),
                )
            },
            onClick = { onIntent(SettingsUiIntent.TogglePushNotification) },
        )
        HorizontalDivider(color = WitTheme.colors.divider)
        SettingsItem(
            iconRes = R.drawable.icon_logout,
            label = "로그아웃",
            onClick = { onIntent(SettingsUiIntent.ClickLogout) },
        )
        HorizontalDivider(color = WitTheme.colors.divider)
        SettingsItem(
            iconRes = R.drawable.icon_withdraw,
            iconTint = WitTheme.colors.error,
            label = "회원 탈퇴",
            labelColor = WitTheme.colors.error,
            onClick = { onIntent(SettingsUiIntent.ClickWithdraw) },
        )
        HorizontalDivider(color = WitTheme.colors.divider)
    }

    WitDialog(
        showDialog = showLogoutDialog,
        title = "계정에서 로그아웃하시겠어요?",
        leftButtonText = "취소",
        rightButtonText = "로그아웃",
        rightButtonColor = WitTheme.colors.error,
        onLeftButtonClick = { onIntent(SettingsUiIntent.DismissLogoutDialog) },
        onRightButtonClick = { onIntent(SettingsUiIntent.ConfirmLogout) },
    ) {
        WitDialogTitleOnlyLayout()
    }

    WitDialog(
        showDialog = showWithdrawDialog,
        title = "정말 탈퇴하시겠어요?",
        message = "탈퇴 버튼 선택 시, 계정은\n삭제되며 복구되지 않습니다.",
        leftButtonText = "취소",
        rightButtonText = "탈퇴",
        rightButtonColor = WitTheme.colors.error,
        onLeftButtonClick = { onIntent(SettingsUiIntent.DismissWithdrawDialog) },
        onRightButtonClick = { onIntent(SettingsUiIntent.ConfirmWithdraw) },
    ) {
        WitDialogDefaultLayout(centerAlignedContent = true)
    }

    WitDialog(
        showDialog = showWithdrawCompleteDialog,
        title = "회원 탈퇴가 완료되었어요",
        message = "다시 찾아주실 때는 더욱 만족하실 수\n있도록 노력할게요.",
        leftButtonText = "",
        rightButtonText = "확인",
        rightButtonColor = WitTheme.colors.error,
        onLeftButtonClick = { onIntent(SettingsUiIntent.DismissWithdrawCompleteDialog) },
        onRightButtonClick = { onIntent(SettingsUiIntent.DismissWithdrawCompleteDialog) },
    ) {
        Column(
            modifier = Modifier.padding(top = 30.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            WitDialogOnlyTitle()
            SpH(8.dp)
            WitDialogMessage(centerAlignedContent = true)
            SpH(26.dp)
            WitDialogRightButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
            )
        }
    }

    WitErrorDialog(
        errorMessage = errorMessage,
        onDismiss = { onIntent(SettingsUiIntent.DismissErrorDialog) }
    )
}
