package com.multissue.wit.feature.map.component.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.map.component.SpW
import com.multissue.wit.feature.map.state.FeedFilterType
import com.multissue.wit.feature.map.state.FeedItemState

@Composable
fun FeedListRow(
    modifier: Modifier = Modifier,
    filterType: FeedFilterType,
    title: String,
    titleThumbnail: String,
    feedList: List<FeedItemState>,
    onFeedItemClicked: (FeedItemState) -> Unit,
    onViewMoreBoxClicked: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape),
                model = titleThumbnail,
                contentDescription = "제목 썸네일"
            )
            Text(
                text = title,
                style = WitTheme.typography.titleM,
                color = WitTheme.colors.text
            )
        }
        LazyRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (filterType) {
                FeedFilterType.POPULAR -> {
                    items(feedList.size) {
                        FeedThumbnail(
                            modifier = Modifier
                                .fillMaxHeight()
                                .noRippleClickable { onFeedItemClicked(feedList[it]) },
                            feedItemState = feedList[it]
                        )
                    }
                }
                FeedFilterType.LIVE -> {
                    item {
                        FeedThumbnail(
                            modifier = Modifier
                                .fillMaxHeight()
                                .noRippleClickable { onFeedItemClicked(feedList[0]) },
                            feedItemState = feedList.firstOrNull() ?: FeedItemState()
                        )
                    }
                    item {
                        FeedViewMoreBox(
                            modifier = Modifier
                                .height(186 .dp)
                                .aspectRatio(3/4f),
                            onClick = onViewMoreBoxClicked
                        )
                    }
                }
            }
        }
    }
}