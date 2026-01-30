package com.multissue.wit.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class WitBackgroundTheme(
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified,
) {
    companion object {
        @Composable
        fun defaultBackground(darkTheme: Boolean): WitBackgroundTheme {
            return if (darkTheme) {
                WitBackgroundTheme(
                    color = black100,
                    tonalElevation = 0.dp
                )
            } else {
                WitBackgroundTheme(
                    color = white100,
                    tonalElevation = 0.dp
                )
            }
        }
    }

}