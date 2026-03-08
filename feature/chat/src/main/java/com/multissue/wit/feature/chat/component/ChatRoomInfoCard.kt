package com.multissue.wit.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.chat.R

@Composable
fun ChatRoomInfoCard(
    modifier: Modifier = Modifier,
    dateTime: String,
    location: String,
    currentCount: Int,
    maxCount: Int,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(elevation = 2.dp, shape = shape, spotColor = WitTheme.colors.pointColor)
            .background(color = WitTheme.colors.white100, shape = shape)
            .border(width = 2.dp, color = WitTheme.colors.primaryDark, shape = shape)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .noRippleClickable { onClick() },
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            InfoRow(
                iconRes = R.drawable.icon_date_room,
                iconDescription = "날짜",
                text = dateTime,
            )
            InfoRow(
                iconRes = R.drawable.icon_location_room,
                iconDescription = "위치",
                text = location,
            )
            InfoRow(
                iconRes = R.drawable.icon_people,
                iconDescription = "인원",
                text = "$currentCount / $maxCount",
            )
        }

        Icon(
            modifier = Modifier
                .size(18.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(R.drawable.icon_arrow_right),
            contentDescription = "더보기",
            tint = WitTheme.colors.disabledText,
        )
    }
}

@Composable
private fun InfoRow(
    modifier: Modifier = Modifier,
    iconRes: Int,
    iconDescription: String,
    text: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = iconDescription,
            modifier = Modifier.size(18.dp),
            tint = WitTheme.colors.primaryDark,
        )
        Text(
            text = text,
            style = WitTheme.typography.bodyM,
            color = WitTheme.colors.subText,
        )
    }
}