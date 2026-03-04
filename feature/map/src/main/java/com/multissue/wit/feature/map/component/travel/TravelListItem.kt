package com.multissue.wit.feature.map.component.travel

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.dummy.travelDummyData
import com.multissue.wit.feature.map.state.travel.TravelItemState

@Composable
fun TravelListItem(
    modifier: Modifier = Modifier,
    travelItem: TravelItemState,
    onChatClick: (Int) -> Unit,
    onItemClicked: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = WitTheme.colors.spotColor
            )
            .background(
                color = WitTheme.colors.white100,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = WitTheme.colors.divider,
                shape = RoundedCornerShape(12.dp)
            )
            .noRippleClickable {
                onItemClicked(travelItem.id)
            }
            .padding(18.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TravelDayBadge(
                    dayDiff = travelItem.dayDiff
                )

                Spacer(modifier = Modifier.width(6.dp))

                TravelActivityTypeBadge(
                    activityType = travelItem.activityType
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = travelItem.title,
                style = WitTheme.typography.titleM,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                TravelDescription(
                    iconResource = R.drawable.icon_people,
                    iconDescription = "참가자 수",
                    content = stringResource(R.string.travel_participant, travelItem.currentParticipants, travelItem.maxParticipants)
                )
                TravelDescription(
                    iconResource = R.drawable.icon_calendar,
                    iconDescription = "만남 날짜",
                    content = travelItem.meetingDate
                )
                TravelDescription(
                    iconResource = R.drawable.icon_location,
                    iconDescription = "만남 장소",
                    content = travelItem.location
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.wrapContentWidth()
            ) {
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .wrapContentWidth()
                ) {
                    travelItem.companionThumbnails.forEachIndexed { index, imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "동행 사람들 이미지",
                            modifier = Modifier
                                .padding(start = (index * 10).dp)
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(WitTheme.colors.primary)    // TODO("placeholder 이미지 나오면 background 제거")
                                .border(1.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = travelItem.description,
                    style = WitTheme.typography.bodyM,
                    color = WitTheme.colors.subText,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .wrapContentSize()
                .background(
                    color = WitTheme.colors.primaryDark,
                    shape = RoundedCornerShape(50)
                )
                .noRippleClickable {
                    onChatClick(travelItem.id)
                }
                .padding(vertical = 8.dp, horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_chat),
                contentDescription = "채팅",
                modifier = Modifier.size(20.dp),
                tint = WitTheme.colors.white100
            )
        }
    }
}

@Preview
@Composable
private fun TravelListItemPreview() {
    WitTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TravelListItem(
                travelItem = travelDummyData[0],
                onItemClicked = {},
                onChatClick = {}
            )
        }
    }
}