package com.multissue.wit.feature.upload.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable

@Composable
fun CameraControlRow(
    modifier: Modifier = Modifier,
    isFlashOn: Boolean,
    isCapturing: Boolean,
    onFlashButtonClicked: (Boolean) -> Unit,
    onRotateButtonClicked: () -> Unit,
    onCaptureButtonClicked: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlashButton(
            modifier = Modifier.size(48.dp),
            isFlashOn = isFlashOn,
            onClick = onFlashButtonClicked,
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .then(
                    if (!isCapturing) Modifier.noRippleClickable { onCaptureButtonClicked() }
                    else Modifier
                )
                .border(
                    width = 4.dp,
                    color = if (isCapturing) WitTheme.colors.gray200 else WitTheme.colors.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCapturing) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(fraction = 0.6f),
                    color = WitTheme.colors.primary,
                    strokeWidth = 3.dp
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(fraction = 0.8f)
                        .background(
                            color = WitTheme.colors.primary,
                            shape = CircleShape
                        )
                )
            }
        }
        RotateButton(
            modifier = Modifier.size(48.dp),
            onClick = onRotateButtonClicked,
        )
    }
}