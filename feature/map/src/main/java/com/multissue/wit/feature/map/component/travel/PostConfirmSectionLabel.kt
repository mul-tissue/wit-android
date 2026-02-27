package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun PostConfirmSectionLabel(
    modifier: Modifier = Modifier,
    title: String,
    textCount: Int,
    maxLength: Int,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = WitTheme.typography.titleS,
            color = WitTheme.colors.subText
        )
        Spacer(modifier = Modifier.weight(1f))
        if (maxLength != 0) {
            Text(
                text = "($textCount/$maxLength)",
                style = WitTheme.typography.bodyM,
                color = WitTheme.colors.disabledText
            )
        }
    }
}

@Preview
@Composable
private fun PostConfirmSectionLabelPreview() {
    WitTheme {
        PostConfirmSectionLabel(
            title = "제목",
            textCount = 24,
            maxLength = 30
        )
    }
}