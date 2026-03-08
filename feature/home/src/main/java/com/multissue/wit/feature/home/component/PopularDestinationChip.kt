package com.multissue.wit.feature.home.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.home.state.PopularDestinationState

@Composable
fun PopularDestinationChip(
    modifier: Modifier = Modifier,
    destination: PopularDestinationState,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = WitTheme.colors.border,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = destination.flagEmoji,
            fontSize = 10.sp,
            maxLines = 1,
            style = WitTheme.typography.titleS,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = destination.name,
            style = WitTheme.typography.titleS,
            fontSize = 10.sp,
            maxLines = 1,
            color = WitTheme.colors.text,
        )
    }
}
