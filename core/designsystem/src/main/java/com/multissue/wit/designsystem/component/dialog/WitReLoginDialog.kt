package com.multissue.wit.designsystem.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun WitReLoginDialog(
    visible: Boolean,
    onConfirm: () -> Unit,
) {
    if (!visible) return

    Dialog(onDismissRequest = {}) {
        Surface(
            modifier = Modifier.wrapContentHeight(),
            shape = RoundedCornerShape(12.dp),
            color = WitTheme.colors.background,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(top = 30.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.relogin_dialog_title),
                    style = WitTheme.typography.titleL,
                    color = WitTheme.colors.text,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.relogin_dialog_message),
                    style = WitTheme.typography.bodyXS,
                    color = WitTheme.colors.subText,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(26.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WitTheme.colors.primaryDark,
                        contentColor = WitTheme.colors.buttonText
                    ),
                    onClick = onConfirm
                ) {
                    Text(
                        text = stringResource(R.string.relogin_dialog_confirm),
                        style = WitTheme.typography.titleM
                    )
                }
            }
        }
    }
}
