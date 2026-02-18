package com.multissue.wit.feature.signup.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun NicknameDuplicateMessage(
    modifier: Modifier = Modifier,
    showMessage: Boolean,
    color: Color,
    message: String
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = showMessage,
        enter = slideIn {
            IntOffset(0, -it.height)
        } + fadeIn(),
        exit = fadeOut(
            animationSpec = tween(0)
        )
    ) {
        Text(
            text = message,
            style = WitTheme.typography.bodyXS,
            color = color
        )
    }
}