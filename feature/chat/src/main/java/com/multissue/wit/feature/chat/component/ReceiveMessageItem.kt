package com.multissue.wit.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun ReceivedMessageItem(
    modifier: Modifier = Modifier,
    senderName: String,
    senderImageUrl: String,
    message: String,
    time: String,
    unreadCount: Int,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(WitTheme.colors.primaryDark),
            model = senderImageUrl,
            contentDescription = "프로필 이미지"
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = senderName,
                style = WitTheme.typography.bodyXS,
                color = WitTheme.colors.subText,
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 220.dp)
                        .background(
                            color = WitTheme.colors.backgroundLighter,
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 20.dp,
                            ),
                        )
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                ) {
                    Text(
                        text = message,
                        style = WitTheme.typography.titleS,
                        color = WitTheme.colors.text,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    if (unreadCount > 0) {
                        Text(
                            text = unreadCount.toString(),
                            style = WitTheme.typography.bodyXS,
                            color = WitTheme.colors.primaryDark,
                        )
                    }
                    if (time.isNotEmpty()) {
                        Text(
                            text = time,
                            style = WitTheme.typography.titleS,
                            fontSize = 9.sp,
                            color = WitTheme.colors.disabledText,
                        )
                    }
                }
            }
        }
    }
}
