package com.multissue.wit.core.ui.travel.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.core.ui.R
import com.multissue.wit.designsystem.R as DesignR
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable

@Composable
fun UploadSheetHeader(
    modifier: Modifier = Modifier,
    title: String,
    trailingText: String? = null,
    showBack: Boolean,
    onBack: () -> Unit,
    onDismiss: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        if (showBack) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(40.dp)
                    .noRippleClickable { onBack() },
                painter = painterResource(DesignR.drawable.icon_back),
                contentDescription = "뒤로",
                tint = WitTheme.colors.subText,
            )
        }

        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = WitTheme.typography.titleL,
                color = WitTheme.colors.text,
            )
            if (trailingText != null) {
                Text(
                    text = trailingText,
                    style = WitTheme.typography.titleS,
                    color = WitTheme.colors.subText,
                )
            }
        }

        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(40.dp)
                .noRippleClickable { onDismiss() },
            painter = painterResource(R.drawable.icon_close),
            contentDescription = "닫기",
            tint = WitTheme.colors.subText,
        )
    }
}
