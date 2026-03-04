package com.multissue.wit.feature.mypage.component.map.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.mypage.R
import com.multissue.wit.feature.mypage.state.map.feed.MapFeedItem

@Composable
fun MapFeedItem(
    modifier: Modifier = Modifier,
    item: MapFeedItem,
    onLikeClicked: () -> Unit,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.noRippleClickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        // 사진 + 오버레이
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = item.thumbnailUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )

            // 좋아요 버튼 (좌하단)
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart)
                    .noRippleClickable { onLikeClicked() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = if (item.isLiked) painterResource(R.drawable.icon_like_filled)
                              else painterResource(R.drawable.icon_like_normal),
                    contentDescription = "좋아요",
                    tint = Color.Unspecified,
                )
                if (item.likeCount > 0) {
                    Text(
                        text = item.likeCount.toString(),
                        style = WitTheme.typography.bodyM,
                        color = WitTheme.colors.white100,
                    )
                }
            }
        }

        // 날짜
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(R.drawable.icon_calendar),
                contentDescription = null,
                tint = WitTheme.colors.disabledText,
            )
            Text(
                text = item.date,
                style = WitTheme.typography.bodyM,
                color = WitTheme.colors.disabledText,
            )
        }

        // 위치
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(R.drawable.icon_location),
                contentDescription = null,
                tint = WitTheme.colors.disabledText,
            )
            Text(
                text = item.location,
                style = WitTheme.typography.bodyM,
                color = WitTheme.colors.disabledText,
            )
        }
    }
}
