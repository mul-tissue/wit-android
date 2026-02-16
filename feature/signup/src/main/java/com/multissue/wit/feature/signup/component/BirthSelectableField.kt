package com.multissue.wit.feature.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.signup.R

@Composable
fun BirthSelectableField(
    modifier: Modifier = Modifier,
    birthDate: String,
    onSelectBirth: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    onSelectBirth()
                }
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                text = birthDate,
                style = WitTheme.typography.titleXL,
                color = WitTheme.colors.subText
            )

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.icon_calendar),
                contentDescription = "",
                tint = WitTheme.colors.subText
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = WitTheme.colors.disabledText
        )
    }

}