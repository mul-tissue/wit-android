package com.multissue.wit.feature.map.component.travel

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun UploadStepProgressBar(
    modifier: Modifier = Modifier,
    currentPage: Int,
    totalSteps: Int,
) {
    val gap = 6.dp
    val density = LocalDensity.current

    var totalWidthPx by remember { mutableIntStateOf(0) }

    val segmentWidthDp = remember(totalWidthPx, totalSteps) {
        if (totalWidthPx == 0) 0.dp else {
            val totalWidthDp = with(density) { totalWidthPx.toDp() }
            (totalWidthDp - (gap * (totalSteps - 1))) / totalSteps
        }
    }

    val indicatorOffset by animateDpAsState(
        targetValue = (segmentWidthDp + gap) * currentPage,
        label = "stepIndicatorOffset",
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .onGloballyPositioned { coordinates ->
                totalWidthPx = coordinates.size.width
            },
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(gap),
        ) {
            repeat(totalSteps) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            color = WitTheme.colors.divider,
                            shape = RoundedCornerShape(2.dp)
                        ),
                )
            }
        }

        if (totalWidthPx > 0) {
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(segmentWidthDp)
                    .fillMaxHeight()
                    .background(
                        color = WitTheme.colors.primaryDark,
                        shape = RoundedCornerShape(2.dp)
                    ),
            )
        }
    }
}