package com.multissue.wit.feature.feed.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun BottomSheetButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = WitTheme.typography.titleM,
            color = WitTheme.colors.error
        )
    }
}