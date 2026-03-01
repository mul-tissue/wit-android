package com.multissue.wit.feature.mypage.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar

@Composable
fun MyPageSettingsTopAppBar(
    onBackButtonClicked: () -> Unit,
    onNotificationButtonClicked: () -> Unit,
) {
    WitCenterAlignedTopAppBar(
        title = "설정",
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(
                    painter = painterResource(R.drawable.icon_back),
                    contentDescription = "navigationIconContentDescription"
                )
            }
        },
        actions = {
            IconButton(onClick = onNotificationButtonClicked) {
                Icon(
                    painter = painterResource(R.drawable.icon_notification),
                    contentDescription = "알림 버튼"
                )
            }
        }
    )
}