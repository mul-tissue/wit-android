package com.multissue.wit.designsystem.component.selectable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable

enum class WitSelectType {
    Feed,
    Travel
}

@Composable
fun WitTextSwitch(
    modifier: Modifier = Modifier,
    selected: WitSelectType,
    leftText: String,
    rightText: String,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
) {
    val animatedFloat by animateFloatAsState(
        targetValue = when (selected) {
            WitSelectType.Feed -> -1f
            WitSelectType.Travel -> 1f
        },
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    )
    val leftTextColor by animateColorAsState(
        targetValue = when (selected) {
            WitSelectType.Feed -> WitTheme.colors.primaryDark
            WitSelectType.Travel -> WitTheme.colors.white100
        },
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    )
    val rightTextColor by animateColorAsState(
        targetValue = when (selected) {
            WitSelectType.Feed -> WitTheme.colors.white100
            WitSelectType.Travel -> WitTheme.colors.primaryDark
        },
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    )
    Box(
        modifier = modifier
            .background(
                color = WitTheme.colors.primary,
                shape = RoundedCornerShape(50)
            )
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .align(BiasAlignment(animatedFloat, 0f))
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
                .clip(RoundedCornerShape(50))
                .background(WitTheme.colors.white100)
        )
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f)
                    .noRippleClickable {
                        onLeftClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = leftText, color = leftTextColor, style = WitTheme.typography.titleM)
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f)
                    .noRippleClickable {
                        onRightClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = rightText, color = rightTextColor, style = WitTheme.typography.titleM)
            }
        }
    }
}

@Preview
@Composable
fun WitSelectableRowPreview() {
    var selected by remember { mutableStateOf(WitSelectType.Feed) }
    WitTheme {
        WitTextSwitch(
            modifier = Modifier
                .width(118.dp)
                .height(44.dp),
            selected = selected,
            leftText = "피드",
            rightText = "동행",
            onLeftClick = {
                selected = WitSelectType.Feed
            },
            onRightClick = {
                selected = WitSelectType.Travel
            }
        )
    }
}