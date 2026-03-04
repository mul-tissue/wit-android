package com.multissue.wit.feature.map.component.travel

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R

@Composable
fun PostConfirmTopBar(
    modifier: Modifier = Modifier,
    title: String,
    showBack: Boolean = true,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
) {
    WitCenterAlignedTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = if (showBack) {
            {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(R.drawable.icon_back),
                        contentDescription = "뒤로 가기",
                        tint = WitTheme.colors.iconTint
                    )
                }
            }
        } else {
            {}
        },
        actions = {
            IconButton(onClick = onCloseClick) {
                Icon(
                    painter = painterResource(R.drawable.icon_close),
                    contentDescription = "닫기",
                    tint = WitTheme.colors.iconTint
                )
            }
        }
    )
}

@Preview
@Composable
private fun PostConfirmTopBarPreview() {
    WitTheme {
        PostConfirmTopBar(
            title = "게시 전 확인",
            onBackClick = {},
            onCloseClick = {}
        )
    }
}
