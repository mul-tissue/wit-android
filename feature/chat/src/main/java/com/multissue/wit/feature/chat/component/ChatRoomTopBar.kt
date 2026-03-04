package com.multissue.wit.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable

@Composable
fun ChatRoomTopBar(
    emoji: String,
    title: String,
    participantCount: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(WitTheme.colors.background)
            .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable { onBackClick() },
                painter = painterResource(R.drawable.icon_back),
                contentDescription = "뒤로가기",
                tint = WitTheme.colors.iconTint,
            )

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(WitTheme.colors.backgroundLighter)
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emoji,
                    textAlign = TextAlign.Center,
                    style = WitTheme.typography.titleL,
                )
            }
        }

        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = title,
                style = WitTheme.typography.titleXL,
                color = WitTheme.colors.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = participantCount.toString(),
                style = WitTheme.typography.titleM,
                color = WitTheme.colors.disabledText,
            )

        }
    }
}