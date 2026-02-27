package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.component.SpH

@Composable
fun PostConfirmTitle(
    modifier: Modifier = Modifier,
    title: String,
    textCount: Int = 0,
    maxLength: Int= 0,
    showDivider: Boolean = true,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        PostConfirmSectionLabel(
            title = title,
            textCount = textCount,
            maxLength = maxLength
        )
        SpH(16.dp)
        content()
    }
    if (showDivider) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = WitTheme.colors.divider
        )
    }
}

@Preview
@Composable
private fun PostConfirmTitlePreview() {
    WitTheme {
        PostConfirmTitle(
            title = "위치",
            textCount = 24,
            maxLength = 30
        ) {
            PostConfirmInfoRow(
                iconRes = R.drawable.icon_location,
                iconDescription = "위치",
                text = "Rue de Rivoli, 75001 Paris, France"
            )
        }
    }
}
