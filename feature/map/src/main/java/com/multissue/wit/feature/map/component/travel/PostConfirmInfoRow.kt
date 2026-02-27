package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.icon.WitIcon
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R

@Composable
fun PostConfirmInfoRow(
    modifier: Modifier = Modifier,
    iconRes: Int,
    iconDescription: String,
    text: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        WitIcon(
            modifier = Modifier.size(18.dp),
            iconRes = iconRes,
            contentDescription = iconDescription,
            tint = WitTheme.colors.primaryDark
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = WitTheme.typography.bodyS,
            color = WitTheme.colors.text
        )
    }
}

@Preview
@Composable
private fun PostConfirmInfoRowPreview() {
    WitTheme {
        PostConfirmInfoRow(
            iconRes = R.drawable.icon_location,
            iconDescription = "위치",
            text = "Rue de Rivoli, 75001 Paris, France"
        )
    }
}
