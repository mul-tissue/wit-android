package com.multissue.wit.feature.travel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.travel.R

@Composable
fun TravelDetailCard(
    modifier: Modifier = Modifier,
    title: String,
    dayDiff: Int = 0,
    participantsText: String,
    meetingDateText: String,
    locationName: String,
    badges: List<String> = emptyList(),
    authorName: String,
    authorImageUrl: String? = null,
    content: String,
    locationAddress: String? = null,
    mapPreview: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = WitTheme.colors.spotColor,
            )
            .background(
                color = WitTheme.colors.background,
                shape = RoundedCornerShape(12.dp),
            )
            .border(
                width = 1.dp,
                color = WitTheme.colors.divider,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TravelDetailDayBadge(dayDiff = dayDiff)
            Text(
                text = title,
                style = WitTheme.typography.titleM,
                color = WitTheme.colors.text,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TravelDetailDescription(
                iconResource = R.drawable.icon_people,
                iconDescription = "인원",
                content = participantsText,
            )
            if (meetingDateText.isNotEmpty()) {
                TravelDetailDescription(
                    iconResource = R.drawable.icon_calendar,
                    iconDescription = "만남 날짜",
                    content = meetingDateText,
                )
            }
            if (locationName.isNotEmpty()) {
                TravelDetailDescription(
                    iconResource = R.drawable.icon_location,
                    iconDescription = "만남 장소",
                    content = locationName,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (badges.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                badges.forEach { badge ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = WitTheme.colors.divider)
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    ) {
                        Text(
                            text = badge,
                            style = WitTheme.typography.bodyM,
                            color = WitTheme.colors.disabledText,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AsyncImage(
                model = authorImageUrl,
                contentDescription = "작성자 프로필",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(WitTheme.colors.primary),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = authorName,
                style = WitTheme.typography.titleS,
                color = WitTheme.colors.text,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = WitTheme.colors.divider)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = content,
            style = WitTheme.typography.bodyS,
            color = WitTheme.colors.text,
        )

        if (mapPreview != null || !locationAddress.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(100.dp))
            HorizontalDivider(color = WitTheme.colors.divider, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (mapPreview != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .clip(RoundedCornerShape(8.dp)),
                    ) {
                        mapPreview()
                    }
                }
                if (!locationAddress.isNullOrEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_location),
                            contentDescription = "주소",
                            modifier = Modifier.size(14.dp),
                            tint = WitTheme.colors.subText,
                        )
                        Text(
                            text = locationAddress,
                            style = WitTheme.typography.bodyM,
                            color = WitTheme.colors.subText,
                        )
                    }
                }
            }
        }
    }
}
