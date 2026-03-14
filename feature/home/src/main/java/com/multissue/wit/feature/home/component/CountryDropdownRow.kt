package com.multissue.wit.feature.home.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.home.R
import com.multissue.wit.feature.home.state.CountryState

@Composable
fun CountryDropdownRow(
    modifier: Modifier = Modifier,
    country: CountryState,
    isExpanded: Boolean,
    onToggle: () -> Unit,
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(300),
    )

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { onToggle() }
                .padding(horizontal = 4.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = country.flagEmoji,
                style = WitTheme.typography.titleM,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = country.name,
                style = WitTheme.typography.titleS,
                color = WitTheme.colors.text,
                modifier = Modifier.weight(1f),
            )
            Icon(
                painter = painterResource(R.drawable.icon_arrow_down),
                contentDescription = if (isExpanded) {
                    stringResource(R.string.home_country_collapse)
                } else {
                    stringResource(R.string.home_country_expand)
                },
                modifier = Modifier
                    .size(20.dp)
                    .rotate(rotationAngle),
                tint = WitTheme.colors.grayText,
            )
        }
        HorizontalDivider(color = WitTheme.colors.divider, thickness = 0.5.dp)
    }
}
