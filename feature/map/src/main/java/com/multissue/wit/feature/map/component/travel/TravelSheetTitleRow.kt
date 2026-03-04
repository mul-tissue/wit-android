package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.icon.WitIcon
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.map.R

@Composable
fun TravelSheetTitleRow(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    onDismiss: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center)
                .noRippleClickable {
                    onClick()
                },
            text = title,
            style = WitTheme.typography.titleL
        )

        if (onDismiss != null) {
            WitIcon(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(44.dp)
                    .noRippleClickable {
                        onDismiss()
                    },
                iconRes = R.drawable.icon_close,
                contentDescription = "나가기",
                tint = WitTheme.colors.subText
            )
        }
    }
}

@Preview
@Composable
private fun TravelSheetTitleRowPreview() {
    WitTheme {
        TravelSheetTitleRow(
            modifier = Modifier.background(WitTheme.colors.background),
            title = "활동 유형",
            onClick = {},
            onDismiss = {}
        )
    }
}