package com.multissue.wit.feature.travel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.travel.R

@Composable
fun TravelCompanionInfo(
    modifier: Modifier = Modifier,
    travelUserName: String,
    companionThumbnails: List<String>
) {
    if (travelUserName.isEmpty()) return

    Row(
        modifier = modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (companionThumbnails.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .height(24.dp)
                    .wrapContentWidth()
            ) {
                companionThumbnails.take(3).forEachIndexed { index, imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = (index * 12).dp)
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(WitTheme.colors.primary)
                            .border(1.5.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        val companionCount = companionThumbnails.size
        val text = if (companionCount > 0) {
            stringResource(R.string.travel_detail_companion_count, travelUserName, companionCount)
        } else {
            stringResource(R.string.travel_detail_companion_single, travelUserName)
        }

        Text(
            text = text,
            style = WitTheme.typography.bodyM,
            color = WitTheme.colors.text
        )
    }
}
