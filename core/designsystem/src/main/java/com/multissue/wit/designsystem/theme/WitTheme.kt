package com.multissue.wit.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalWitTypography =
    staticCompositionLocalOf<WitTypography> { error("All typography must be Provided") }

@Composable
fun WitTheme(
    typography: WitTypography = WitTypography.defaultTypography(),
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalWitTypography provides typography,
    ) {
        MaterialTheme(content = content)
    }
}

object WitTheme {
    val typography: WitTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalWitTypography.current
}
