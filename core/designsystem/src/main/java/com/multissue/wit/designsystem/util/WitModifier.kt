package com.multissue.wit.designsystem.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit
): Modifier = this.then(
    Modifier.clickable(
        enabled = enabled,
        indication = null,
        interactionSource = interactionSource,
        onClick = onClick
    )
)