package com.multissue.wit.designsystem.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = this.then(
    Modifier.clickable(
        enabled = enabled,
        indication = null,
        interactionSource = MutableInteractionSource(),
        onClick = onClick
    )
)