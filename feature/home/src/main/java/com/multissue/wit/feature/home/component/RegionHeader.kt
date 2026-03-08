package com.multissue.wit.feature.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.home.state.RegionState

@Composable
fun RegionHeader(
    region: RegionState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = region.emoji,
            style = WitTheme.typography.titleM,
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = region.name,
            style = WitTheme.typography.titleM.copy(fontWeight = FontWeight.Bold),
            color = WitTheme.colors.text,
        )
    }
}