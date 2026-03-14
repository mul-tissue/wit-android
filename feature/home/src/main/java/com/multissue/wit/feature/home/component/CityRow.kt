package com.multissue.wit.feature.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.home.state.CityState

@Composable
fun CityRow(
    city: CityState,
    onClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { onClick() }
                .padding(start = 32.dp, top = 14.dp, bottom = 14.dp, end = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = city.name,
                style = WitTheme.typography.titleS,
                color = WitTheme.colors.text,
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(start = 32.dp),
            color = WitTheme.colors.divider,
            thickness = 0.5.dp,
        )
    }
}
