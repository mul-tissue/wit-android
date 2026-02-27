package com.multissue.wit.feature.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.mypage.state.UserInfoState

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    userInfoState: UserInfoState,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            model = userInfoState.profileImageUrl,
            contentDescription = "프로필 이미지",
            contentScale = ContentScale.Crop
        )
        SpH(8.dp)
        Text(
            text = userInfoState.name,
            style = WitTheme.typography.titleXL,
            color = WitTheme.colors.subText
        )
        SpH(2.dp)
        Text(
            text = "${userInfoState.age} · ${userInfoState.gender}",
            style = WitTheme.typography.titleS,
            color = WitTheme.colors.disabledText
        )
    }
}
