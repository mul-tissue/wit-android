package com.multissue.wit.feature.feed.component

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.theme.blueDarker
import com.multissue.wit.designsystem.util.noRippleClickable

@SuppressLint("RestrictedApi")
@Composable
fun ReactionItem(
    modifier: Modifier = Modifier,
    emoji: String,
    count: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val baseColor = blueDarker
    val progress by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        label = "gradientProgress"
    )
    val gradientBrush = Brush.radialGradient(
        0f to baseColor.copy(alpha = lerp(0.5f, 0f, 1f - progress)),
        0.38f to baseColor.copy(alpha = lerp(0.79f, 0f, 1f - progress)),
        0.76f to baseColor.copy(alpha = lerp(0.9f, 0f, 1f - progress)),
        0.88f to baseColor.copy(alpha = lerp(0.87f, 0f, 1f - progress)),
        1f to baseColor.copy(alpha = lerp(0.8f, 0f, 1f - progress)),
    )

    val itemTextColor by animateColorAsState(
        targetValue = if (selected) WitTheme.colors.white100
            else WitTheme.colors.disabledText
    )

    Box(
        modifier = if (selected) {
            modifier
                .size(64.dp)
                .background(
                    brush = gradientBrush,
                    shape = CircleShape
                )
                .noRippleClickable { onClick() }
        } else {
            modifier
                .size(64.dp)
                .background(
                    color = WitTheme.colors.white200,
                    shape = CircleShape
                )
                .noRippleClickable { onClick() }
        },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = emoji,
                style = WitTheme.typography.titleXXL.copy(fontSize = 22.sp)
            )
            Text(
                text = count,
                style = WitTheme.typography.bodyM,
                color = itemTextColor
            )
        }
    }
}