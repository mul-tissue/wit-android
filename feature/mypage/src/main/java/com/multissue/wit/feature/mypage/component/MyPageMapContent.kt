package com.multissue.wit.feature.mypage.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun MyPageMapContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "지도",
            style = WitTheme.typography.titleM,
            color = WitTheme.colors.disabledText
        )
    }
}
