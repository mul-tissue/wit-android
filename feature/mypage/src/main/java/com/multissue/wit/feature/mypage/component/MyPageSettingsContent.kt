package com.multissue.wit.feature.mypage.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun MyPageSettingsContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "설정",
            style = WitTheme.typography.titleM,
            color = WitTheme.colors.disabledText
        )
    }
}
