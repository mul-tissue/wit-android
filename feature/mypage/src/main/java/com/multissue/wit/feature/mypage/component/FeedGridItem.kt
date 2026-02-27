package com.multissue.wit.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.mypage.state.FeedItemState

@Composable
fun FeedGridItem(
    modifier: Modifier = Modifier,
    feedItemState: FeedItemState,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.noRippleClickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(WitTheme.colors.white200)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = feedItemState.thumbnailUrl,
                contentDescription = feedItemState.location,
                contentScale = ContentScale.Crop
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(com.multissue.wit.feature.mypage.R.drawable.icon_location),
                contentDescription = null,
                tint = WitTheme.colors.disabledText
            )
            Text(
                text = feedItemState.location,
                style = WitTheme.typography.bodyM,
                color = WitTheme.colors.disabledText
            )
        }
    }
}
