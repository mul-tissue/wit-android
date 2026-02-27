package com.multissue.wit.feature.mypage.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.mypage.state.MyPageType

@Composable
fun MyPageTabRow(
    modifier: Modifier = Modifier,
    selectedTab: MyPageType,
    onTabSelected: (MyPageType) -> Unit,
) {
    val tabs = listOf(MyPageType.FEED, MyPageType.TRAVEL)
    val selectedIndex = tabs.indexOf(selectedTab)

    val animatedFraction by animateFloatAsState(
        targetValue = selectedIndex.toFloat() / tabs.size,
        animationSpec = tween(durationMillis = 250),
        label = "tabIndicatorOffset"
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .background(WitTheme.colors.containerColor)
    ) {
        val tabWidth = maxWidth / tabs.size

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEach { tab ->
                val isSelected = selectedTab == tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .noRippleClickable { onTabSelected(tab) }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (tab) {
                            MyPageType.FEED -> "피드"
                            MyPageType.TRAVEL -> "동행"
                            else -> ""
                        },
                        style = WitTheme.typography.titleM,
                        color = if (isSelected) WitTheme.colors.primary else WitTheme.colors.disabledText
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = tabWidth * animatedFraction * tabs.size)
                .width(tabWidth)
                .height(2.dp)
                .background(WitTheme.colors.primary)
        )
    }
}
