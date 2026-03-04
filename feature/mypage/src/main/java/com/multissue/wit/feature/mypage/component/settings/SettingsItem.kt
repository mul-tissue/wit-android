package com.multissue.wit.feature.mypage.component.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.mypage.component.SpW

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    iconTint: Color = WitTheme.colors.disabledText,
    label: String,
    labelColor: Color = WitTheme.colors.subText,
    trailing: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 26.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = iconTint,
        )
        SpW(12.dp)
        Text(
            modifier = Modifier.weight(1f),
            text = label,
            style = WitTheme.typography.titleM,
            color = labelColor,
        )
        if (trailing != null) {
            trailing()
        }
    }
}
