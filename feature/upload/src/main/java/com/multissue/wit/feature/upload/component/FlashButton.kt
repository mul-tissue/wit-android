package com.multissue.wit.feature.upload.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.theme.yellow200
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.upload.R

@Composable
fun FlashButton(
    modifier: Modifier = Modifier,
    isFlashOn: Boolean,
    onClick: (Boolean) -> Unit,
) {
    Box(
        modifier = modifier
            .noRippleClickable { onClick(!isFlashOn) },
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = isFlashOn,
            animationSpec = tween(durationMillis = 300),
            label = "flashIconCrossfade"
        ) { flashOn ->
            Icon(
                painter = painterResource(
                    id = if (flashOn) {
                        R.drawable.icon_flash_on
                    } else {
                        R.drawable.icon_flash_off
                    }
                ),
                contentDescription = if (flashOn) "플래시 켜짐" else "플래시 꺼짐",
                tint = if (isFlashOn) yellow200 else WitTheme.colors.white100
            )
        }
    }
}
