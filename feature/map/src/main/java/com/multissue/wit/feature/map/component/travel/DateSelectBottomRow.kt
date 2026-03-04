package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.component.SpH
import com.multissue.wit.feature.map.component.SpW

@Composable
fun DateSelectBottomRow(
    modifier: Modifier = Modifier,
    title: String,
    isEnabled: Boolean,
    onReset: () -> Unit,
    onComplete: () -> Unit
) {
    Column(
        modifier = modifier
    ) {

        HorizontalDivider(
            color = WitTheme.colors.divider
        )

        SpH(12.dp)

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = WitTheme.typography.bodyM,
            color = WitTheme.colors.subText,
            textAlign = TextAlign.Center
        )

        SpH(20.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .border(
                        width = 1.dp,
                        color = WitTheme.colors.border,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onReset() }
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(R.drawable.icon_reload),
                        contentDescription = "날짜 선택 초기화",
                        tint = WitTheme.colors.disabledText
                    )
                    SpW(4.dp)
                    Text(
                        text = stringResource(R.string.travel_reset),
                        style = WitTheme.typography.titleS,
                        color = WitTheme.colors.disabledText
                    )
                }
            }

            WitButton(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                title = stringResource(R.string.travel_select_done),
                enabled = isEnabled,
                onClick = {
                    onComplete()
                }
            )
        }
    }
}