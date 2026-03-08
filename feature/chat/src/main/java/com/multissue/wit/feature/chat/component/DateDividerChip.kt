package com.multissue.wit.feature.chat.component
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun DateDividerChip(
    modifier: Modifier = Modifier,
    dateText: String,
    isToday: Boolean,
) {
    val shape = RoundedCornerShape(50)

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        if (isToday) {
            Box(
                modifier = Modifier
                    .widthIn(100.dp)
                    .background(color = WitTheme.colors.primaryDark, shape = shape)
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = dateText,
                    style = WitTheme.typography.bodyXS,
                    color = WitTheme.colors.white100,
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .widthIn(100.dp)
                    .background(color = WitTheme.colors.primaryDark, shape = shape)
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = dateText,
                    style = WitTheme.typography.bodyXS,
                    color = WitTheme.colors.white100,
                )
            }
        }
    }
}