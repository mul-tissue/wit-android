package com.multissue.wit.feature.upload.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multissue.wit.designsystem.util.noRippleClickable

@Composable
fun ZoomLevelRow(
    modifier: Modifier = Modifier,
    currentZoom: Float,
    onZoomSelected: (Float) -> Unit,
) {
    Row(
        modifier = modifier.padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ZoomChip(
            label = "1x",
            isSelected = currentZoom < 1.5f,
            onClick = { onZoomSelected(1f) }
        )
        ZoomChip(
            label = "2x",
            isSelected = currentZoom >= 1.5f,
            onClick = { onZoomSelected(2f) }
        )
    }
}

@Composable
private fun ZoomChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color.White.copy(alpha = 0.9f)
        else Color.White.copy(alpha = 0.2f),
        animationSpec = tween(150)
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else Color.White,
        animationSpec = tween(150)
    )

    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color = backgroundColor, shape = CircleShape)
            .noRippleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}