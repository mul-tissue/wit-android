package com.multissue.wit.feature.map.component.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.map.R

@Composable
fun FeedViewMoreBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(
                color = WitTheme.colors.white200,
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = (0.5).dp,
                        shape = CircleShape
                    )
                    .background(
                        color = WitTheme.colors.background,
                        shape = CircleShape
                    )
                    .size(24.dp)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.icon_next_arrow),
                    contentDescription = "더보기 아이콘",
                    tint = WitTheme.colors.disabledText
                )
            }
            Text(
                text = "더보기", // TODO String
                style = WitTheme.typography.bodyXS,
                color = WitTheme.colors.disabledText
            )
        }
    }
}