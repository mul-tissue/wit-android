package com.multissue.wit.feature.feed.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.feed.R

@Composable
fun FeedContentColumn(
    modifier: Modifier = Modifier,
    title: String,
    location: String,
    date: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            text = title,
            style = WitTheme.typography.titleM,
            color = WitTheme.colors.text
        )
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_location),
                    contentDescription = "위치 아이콘",
                    tint = WitTheme.colors.disabledText
                )
                Text(
                    text = location,
                    style = WitTheme.typography.bodyM,
                    color = WitTheme.colors.disabledText
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_date),
                    contentDescription = "날짜 아이콘",
                    tint = WitTheme.colors.disabledText
                )
                Text(
                    text = date,
                    style = WitTheme.typography.bodyM,
                    color = WitTheme.colors.disabledText
                )
            }
        }
    }
}