package com.multissue.wit.designsystem.component.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun WitBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        color = WitTheme.background.color,
        tonalElevation = WitTheme.background.tonalElevation,
        modifier = modifier.fillMaxSize(),
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            content()
        }
    }
}

@Composable
fun WitGradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val backgroundGradient = Brush.linearGradient(
        colorStops = arrayOf(
            0.0f to WitTheme.colors.primaryLighter,
            0.52f to WitTheme.colors.gradientColor2,
            0.96f to WitTheme.colors.gradientColor3,
            1.0f to WitTheme.colors.gradientColor3,
        )
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundGradient),
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            content()
        }
    }
}

@Preview
@Composable
private fun WitBackgroundPreview() {
    WitTheme {
        WitBackground {

        }
    }
}

@Preview
@Composable
private fun WitGradientBackgroundPreview() {
    WitTheme {
        WitGradientBackground {

        }
    }
}