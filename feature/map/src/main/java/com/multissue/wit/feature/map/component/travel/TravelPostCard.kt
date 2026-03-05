package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.component.SpH

@Composable
fun TravelPostCard(
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
        TravelPostCardBadgeTitleRow(
            dayDiff = dayDiff,
            title = title,
        )
        SpH(14.dp)
        TravelPostCardInfoSection(
            participantsText = participantsText,
            meetingDateText = meetingDateText,
            locationName = locationName,
        )
        SpH(12.dp)
        TravelPostCardConditionChips(badges = badges)
        SpH(12.dp)
        TravelPostCardAuthorRow(
            name = authorName,
            imageUrl = authorImageUrl,
        )
        SpH(16.dp)
        HorizontalDivider(color = WitTheme.colors.divider)
        SpH(16.dp)
        Text(
            text = content,
            style = WitTheme.typography.bodyS,
            color = WitTheme.colors.text,
        )
        if (mapPreview != null || !locationAddress.isNullOrEmpty()) {
            SpH(100.dp)
            HorizontalDivider(
                color = WitTheme.colors.divider,
                thickness = 1.dp,
            )
            SpH(16.dp)
            TravelPostCardMapSection(
                locationAddress = locationAddress,
                mapPreview = mapPreview,
            )
        }
    }
}

@Composable
fun TravelPostCardBadgeTitleRow(
    dayDiff: Int,
    title: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TravelDayBadge(dayDiff = dayDiff)
        Text(
            text = title,
            style = WitTheme.typography.titleM,
            color = WitTheme.colors.text,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun TravelPostCardInfoSection(
    participantsText: String,
    meetingDateText: String,
    locationName: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TravelDescription(
            iconResource = R.drawable.icon_people,
            iconDescription = "인원",
            content = participantsText,
        )
        if (meetingDateText.isNotEmpty()) {
            TravelDescription(
                iconResource = R.drawable.icon_calendar,
                iconDescription = "만남 날짜",
                content = meetingDateText,
            )
        }
        if (locationName.isNotEmpty()) {
            TravelDescription(
                iconResource = R.drawable.icon_location,
                iconDescription = "만남 장소",
                content = locationName,
            )
        }
    }
}

@Composable
fun TravelPostCardConditionChips(
    badges: List<String>
) {
    if (badges.isEmpty()) return
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        badges.forEach { badge ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        color = WitTheme.colors.divider,
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = badge,
                    style = WitTheme.typography.bodyM,
                    color = WitTheme.colors.disabledText
                )
            }
        }
    }
}

@Composable
fun TravelPostCardAuthorRow(
    name: String,
    imageUrl: String?,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "작성자 프로필",
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(WitTheme.colors.primary),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = name,
            style = WitTheme.typography.titleS,
            color = WitTheme.colors.text,
        )
    }
}

@Composable
fun TravelPostCardMapSection(
    locationAddress: String?,
    mapPreview: (@Composable () -> Unit)?,
) {
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

@Preview(showBackground = true)
@Composable
private fun TravelPostCardPreview() {
    WitTheme {
        TravelPostCard(
            modifier = Modifier.padding(16.dp),
            title = "루브르 미술관 투어",
            dayDiff = 0,
            participantsText = "1 / 3",
            meetingDateText = "2025. 12. 26 오후 06:30",
            locationName = "루브르 미술관",
            badges = listOf("20대", "성별 무관", "5명 이내"),
            authorName = "작성자",
            authorImageUrl = null,
            content = "루브르 미술관 함께 투어할 동행 구해요 🧡\n혼자 보기엔 아쉬워서 같이 천천히 둘러보고 싶어요 😊\n작품 보며 편하게 이야기 나눌 분이면 좋겠어요 ✨",
            locationAddress = "Rue de Rivoli, 75001 Paris, France",
            mapPreview = {
                TravelPostCardMapThumbnail(
                    lat = 37.5326,
                    lng = 127.0
                )
            }
        )
    }
}
