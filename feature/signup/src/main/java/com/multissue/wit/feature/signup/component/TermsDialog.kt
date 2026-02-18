package com.multissue.wit.feature.signup.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.multissue.wit.designsystem.component.background.WitBackground

@Composable
fun TermsDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            )
        ) {
            WitBackground(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TermsDialogContent(
                    onDismiss = onDismiss,
                    content = {

                    }
                )
            }
        }
    }
}