package com.multissue.wit.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun SentMessageItem(
    message: String,
    time: String,
    unreadCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,
    ) {
        Column(
            modifier = Modifier.padding(end = 4.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.End,
        ) {
            if (unreadCount > 0) {
                Text(
                    text = unreadCount.toString(),
                    style = WitTheme.typography.bodyXS,
                    color = WitTheme.colors.primary,
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

        Box(
            modifier = Modifier
                .widthIn(max = 220.dp)
                .background(
                    color = WitTheme.colors.primaryDark,
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomStart = 20.dp
                    ),
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
        ) {
            Text(
                text = message,
                style = WitTheme.typography.titleS,
                color = WitTheme.colors.white100,
            )
        }
    }
}