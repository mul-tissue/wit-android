package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable

@Composable
fun ActivityTypeItem(
    icon: String,
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .wrapContentHeight()
            .noRippleClickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .background(
                    color = WitTheme.colors.white200,
                    shape = CircleShape
                )
                .border(
                    width = 4.dp,
                    color = if (isSelected) WitTheme.colors.primaryDark else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                fontSize = 32.sp,
            )
        }
        Text(
            modifier = Modifier
                .offset(y = (-14).dp)
                .background(
                    color = if (isSelected) WitTheme.colors.primaryDark else WitTheme.colors.white200,
                    shape = RoundedCornerShape(50)
                )
                .padding(horizontal = 10.dp, vertical = 6.dp),
            text = name,
            style = WitTheme.typography.titleS.copy(
                color = if (isSelected) WitTheme.colors.background else WitTheme.colors.text,
            ),
            textAlign = TextAlign.Center,
            softWrap = false
        )
    }
}
