package com.multissue.wit.feature.feed.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar
import com.multissue.wit.designsystem.component.topbar.WitContentTopAppBar
import com.multissue.wit.feature.feed.R

@Composable
fun MapTopAppBar(
    username: String,
    userThumbnail: String,
    isMine: Boolean,
    onBackButtonClicked: () -> Unit,
    onMoreButtonClicked: () -> Unit,
) {
    if (isMine) {
        WitCenterAlignedTopAppBar(
            modifier = Modifier.padding(horizontal = 8.dp),
            title = "my 피드",
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
    } else {
        WitContentTopAppBar(
            modifier = Modifier.padding(horizontal = 8.dp),
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
}