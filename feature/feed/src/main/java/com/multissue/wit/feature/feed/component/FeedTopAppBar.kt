package com.multissue.wit.feature.feed.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar
import com.multissue.wit.designsystem.component.topbar.WitContentTopAppBar
import com.multissue.wit.feature.feed.R

@Composable
fun MapTopAppBar(
    username: String,
    userThumbnail: String,
    onBackButtonClicked: () -> Unit,
    onMoreButtonClicked: () -> Unit,
) {
    WitContentTopAppBar(
        content = {
            TitleUserRow(
                username = username,
                userThumbnail = userThumbnail,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(
                    painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_back),
                    contentDescription = "navigationIconContentDescription"
                )
            }
        },
        actions = {
            IconButton(onClick = onMoreButtonClicked) {
                Icon(
                    painter = painterResource(R.drawable.icon_more),
                    contentDescription = "알림 버튼"
                )
            }
        }
    )
}