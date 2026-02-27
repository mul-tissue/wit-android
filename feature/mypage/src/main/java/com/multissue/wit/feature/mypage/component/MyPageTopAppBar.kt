package com.multissue.wit.feature.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar
import com.multissue.wit.designsystem.util.noRippleClickable

@Composable
fun MyPageTopAppBar(
    onLogoButtonClicked: () -> Unit,
    onNotificationButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
) {
    WitCenterAlignedTopAppBar(
        navigationIcon = {
            Image(
                modifier = Modifier.padding(start = 16.dp).noRippleClickable { onLogoButtonClicked() },
                painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_appbar_logo),
                contentDescription = "navigationIconContentDescription"
            )
        },
        actions = {
            IconButton(onClick = onNotificationButtonClicked) {
                Icon(
                    painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_notification),
                    contentDescription = "알림 버튼"
                )
            }
            IconButton(onClick = onSettingsButtonClicked) {
                Icon(
                    painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_settings),
                    contentDescription = "프로필 버튼"
                )
            }
        }
    )
}