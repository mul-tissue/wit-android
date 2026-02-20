package com.multissue.wit.feature.feed.component

import android.R
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable

@Composable
fun SelectableRow(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val checkColor by animateColorAsState(
        targetValue = if (isPressed) WitTheme.colors.subText.copy(alpha = 0.5f) else WitTheme.colors.background
    )

    Row(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(16.dp)
            )
            .noRippleClickable(interactionSource = interactionSource) {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .border(
                    width = 1.dp,
                    color = WitTheme.colors.subText,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(fraction = 0.66f)
                        .background(
                            color = WitTheme.colors.subText,
                            shape = CircleShape
                        )
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(fraction = 0.66f)
                        .background(
                            color = checkColor,
                            shape = CircleShape
                        )
                )
            }
        }

        Text(
            text = text,
            style = WitTheme.typography.bodyS,
            color = WitTheme.colors.subText
        )
    }
}