package com.multissue.wit.core.ui.travel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun TravelActivityTypeBadge(
    activityType: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .border(
                width = 1.dp,
                color = WitTheme.colors.divider,
                shape = RoundedCornerShape(50)
            )
            .background(
                color = WitTheme.colors.background,
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            text = activityType,
            style = WitTheme.typography.bodyM,
            color = WitTheme.colors.subText
        )
    }
}
