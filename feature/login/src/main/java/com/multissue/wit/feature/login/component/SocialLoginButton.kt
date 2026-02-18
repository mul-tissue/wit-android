package com.multissue.wit.feature.login.component

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun SocialLoginButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors,
    icon: @Composable () -> Unit,
    title: String,
    onClick: () -> Unit,
) {
    WitButton(
        modifier = modifier,
        title = title,
        colors = colors,
        textStyle = WitTheme.typography.bodyM,
        leadingIcon = icon
    ) {
        onClick()
    }
}