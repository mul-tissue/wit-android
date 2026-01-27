package com.multissue.wit.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalWitColors =
    staticCompositionLocalOf<WitColors> { error("All colors must be Provided!") }

private val LocalWitTypography =
    staticCompositionLocalOf<WitTypography> { error("All typography must be Provided") }

@Composable
fun WitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: WitColors = if (darkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    },
    typography: WitTypography = WitTypography.defaultTypography(),
    content: @Composable () -> Unit
) {
    val provideColors = remember { colors.copy() }
    provideColors.update(colors)
    CompositionLocalProvider(
        LocalWitColors provides colors,
        LocalWitTypography provides typography,
    ) {
        MaterialTheme(content = content)
    }
}

object WitTheme {
    val colors: WitColors
        @Composable
        @ReadOnlyComposable
        get() = LocalWitColors.current

    val typography: WitTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalWitTypography.current
}
