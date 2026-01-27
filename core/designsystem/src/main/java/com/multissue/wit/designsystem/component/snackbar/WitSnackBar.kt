package com.multissue.wit.designsystem.component.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.theme.WitTheme
import kotlinx.coroutines.launch

data class WitSnackBarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = true,
    override val duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    val contentAlignment: Alignment = Alignment.BottomCenter,
    @DrawableRes val leadingIconRes: Int? = null,
    val leadingIconContentDescription: String? = null,
) : SnackbarVisuals

@Composable
fun WitSnackBarHost(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState,
) {
    SnackbarHost(
        modifier = modifier
            .safeDrawingPadding(),
        hostState = hostState,
        snackbar = { snackbarData ->
            val witVisuals = snackbarData.visuals as? WitSnackBarVisuals

            Snackbar(
                modifier = modifier.padding(24.dp)
                    .border(
                        width = 1.dp,
                        color = WitTheme.colors.primary,
                        shape = RoundedCornerShape(8.dp)
                    ),
                containerColor = WitTheme.colors.white100,
                contentColor = WitTheme.colors.text,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    witVisuals?.leadingIconRes?.run {
                        Image(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(this),
                            contentDescription = witVisuals.leadingIconContentDescription
                        )
                    }
                    Text(
                        text = witVisuals?.message.orEmpty(),
                        style = WitTheme.typography.bodyM
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun WitSnackBarPreview() {
    WitTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        Scaffold(
            snackbarHost = {
                WitSnackBarHost(
                    hostState = snackbarHostState,
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WitTheme.colors.white100)
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                WitSnackBarVisuals(
                                    message = "This is Test SnackBar",
                                    leadingIconRes = R.drawable.icon_round_check_blue
                                )
                            )
                        }
                    }
                ) {
                    Text("Click")
                }
            }
        }
    }
}