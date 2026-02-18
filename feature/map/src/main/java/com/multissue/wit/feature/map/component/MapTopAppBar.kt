package com.multissue.wit.feature.map.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar

@Composable
fun MapTopAppBar(
    onBackButtonClicked: () -> Unit,
    onNotificationButtonClicked: () -> Unit,
    onProfileButtonClicked: () -> Unit,
) {
    WitCenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(
                    painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_back),
                    contentDescription = "navigationIconContentDescription"
                )
            }
        },
        actions = {
            IconButton(onClick = onNotificationButtonClicked) {
                Icon(
                    painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_notification),
                    contentDescription = "알림 버튼"
                )
            }
            IconButton(onClick = onProfileButtonClicked) {
                Icon(
                    painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_profile),
                    contentDescription = "프로필 버튼"
                )
            }
        }
    )
}