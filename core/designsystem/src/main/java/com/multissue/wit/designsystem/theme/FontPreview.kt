package com.multissue.wit.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
internal fun FontPreview() {
    WitTheme {
        Column(
            Modifier.background(color = Color.White),
        ) {
            Text("Title XXL", style = WitTheme.typography.titleXXL)
            Text("Title XL", style = WitTheme.typography.titleXL)
            Text("Title L", style = WitTheme.typography.titleL)
            Text("Title M", style = WitTheme.typography.titleM)
            Text("Title S", style = WitTheme.typography.titleS)
            Text("Body L", style = WitTheme.typography.bodyL)
            Text("Body M", style = WitTheme.typography.bodyM)
            Text("Body S", style = WitTheme.typography.bodyS)
            Text("Body XS", style = WitTheme.typography.bodyXS)
        }
    }
}
