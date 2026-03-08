package com.multissue.wit.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.chat.state.ChatRoomInfoState

@Composable
fun ChatRoomContent(
    modifier: Modifier = Modifier,
    room: ChatRoomInfoState,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(WitTheme.colors.background)
            .noRippleClickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(WitTheme.colors.backgroundLighter),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = room.emoji,
                    style = WitTheme.typography.titleL
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = room.title,
                        style = WitTheme.typography.bodyL,
                        color = WitTheme.colors.text,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Text(
                        text = room.participantCount.toString(),
                        style = WitTheme.typography.titleS,
                        color = WitTheme.colors.disabledText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Text(
                    text = room.lastMessage,
                    style = WitTheme.typography.bodyM,
                    color = WitTheme.colors.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                // 위치 및 만나는 날짜 표시
                Text(
                    text = "${room.location} · ${room.meetingDate}",
                    style = WitTheme.typography.bodyXS,
                    color = WitTheme.colors.subText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = room.lastMessageTime,
                    style = WitTheme.typography.bodyXS,
                    color = WitTheme.colors.disabledText,
                )
                if (room.unreadCount > 0) {
                    UnreadBadge(count = room.unreadCount)
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
