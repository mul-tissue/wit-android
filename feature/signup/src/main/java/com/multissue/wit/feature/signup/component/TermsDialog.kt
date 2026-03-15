package com.multissue.wit.feature.signup.component

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.multissue.wit.designsystem.component.background.WitBackground

@Composable
fun TermsDialog(
    contentUrl: String,
    onDismiss: () -> Unit,
) {
    if (contentUrl.isEmpty()) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        WitBackground(
            modifier = Modifier.fillMaxSize()
        ) {
            TermsDialogContent(
                onDismiss = onDismiss,
                content = {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            WebView(context).apply {
                                webViewClient = WebViewClient()
                                loadUrl(contentUrl)
                            }
                        }
                    )
                }
            )
        }
    }
}
