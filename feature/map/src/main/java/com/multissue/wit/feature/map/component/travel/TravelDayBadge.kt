package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R

@Composable
fun TravelDayBadge(
    dayDiff: Int
) {
    val text = if (dayDiff == 0) {
        stringResource(R.string.travel_day_badge)
    } else {
        stringResource(R.string.travel_day_badge_diff, dayDiff)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(WitTheme.colors.primaryLighter)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = WitTheme.typography.bodyM,
            color = WitTheme.colors.primaryDark
        )
    }
}

@Preview
@Composable
private fun TravelDayBadgePreview() {
    WitTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TravelDayBadge(dayDiff = 0)
            TravelDayBadge(dayDiff = 1)
        }
    }
}