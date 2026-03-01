package com.multissue.wit.feature.mypage.component.profileedit

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar

@Composable
fun ProfileEditTopAppBar(
    onBackButtonClicked: () -> Unit,
) {
    WitCenterAlignedTopAppBar(
        title = "프로필 수정",
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(
                    painter = painterResource(R.drawable.icon_back),
                    contentDescription = "뒤로가기",
                )
            }
        },
    )
}
