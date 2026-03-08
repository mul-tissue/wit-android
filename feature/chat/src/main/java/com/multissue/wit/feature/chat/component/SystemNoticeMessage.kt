package com.multissue.wit.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun SystemNoticeMessage(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = WitTheme.colors.backgroundLighter,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(horizontal = 16.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = WitTheme.typography.bodyXS,
                color = WitTheme.colors.subText,
                textAlign = TextAlign.Center,
            )
        }
    }
}