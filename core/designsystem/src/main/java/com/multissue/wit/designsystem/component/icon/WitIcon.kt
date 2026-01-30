package com.multissue.wit.designsystem.component.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun WitIcon(
    modifier: Modifier = Modifier,
    iconRes: Int,
    contentDescription: String? = null,
    tint: Color = WitTheme.colors.iconTint
) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = iconRes),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Preview
@Composable
fun WitIconPreview() {
    WitTheme {
        WitIcon(
            modifier = Modifier.size(64.dp),
            iconRes = R.drawable.icon_close_chip,
            tint = WitTheme.colors.success
        )
    }
}