package com.multissue.wit.feature.map.component.feed

import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.state.FeedItemState

@Composable
fun FeedThumbnail(
    modifier: Modifier = Modifier,
    feedItemState: FeedItemState,
    // TODO 좋아요 버튼 누르기?
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(3/4f)
                .clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = feedItemState.thumbnailUrl,
                contentDescription = null
            )
            LikeButtonRow(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart),
                isLiked = feedItemState.isLiked,
                onLikeButtonClicked = {  },
                likeCount = feedItemState.likeCount,
            )
        }
        Row(
            modifier = Modifier.height(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(CircleShape),
                model = feedItemState.userThumbnail,
                contentDescription = null
            )
            Text(
                text = feedItemState.username,
                style = WitTheme.typography.bodyM,
                color = WitTheme.colors.disabledText
            )
        }
    }
}