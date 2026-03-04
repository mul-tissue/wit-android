package com.multissue.wit.feature.travel.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun TravelDetailDescription(
    iconResource: Int,
    iconDescription: String,
    content: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = iconDescription,
            modifier = Modifier.size(18.dp),
            tint = WitTheme.colors.subText,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = content,
            style = WitTheme.typography.bodyM,
            color = WitTheme.colors.subText,
        )
    }
}
