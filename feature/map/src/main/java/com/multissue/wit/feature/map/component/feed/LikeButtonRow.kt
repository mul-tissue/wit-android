package com.multissue.wit.feature.map.component.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.map.R

@Composable
fun LikeButtonRow(
    modifier: Modifier = Modifier,
    isLiked: Boolean,
    onLikeButtonClicked: () -> Unit,
    likeCount: Int,
) {
    Row(
        modifier = modifier
            .noRippleClickable(
                onClick = onLikeButtonClicked
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = if (isLiked) painterResource(R.drawable.icon_like_filled)
                else painterResource(R.drawable.icon_like_normal),
            contentDescription = "좋아요 버튼",
            tint = if (isLiked) WitTheme.colors.primaryLighter
                else WitTheme.colors.white200
        )
        Text(
            text = likeCount.toString(),
            style = WitTheme.typography.bodyM,
            color = WitTheme.colors.white200
        )
    }
}