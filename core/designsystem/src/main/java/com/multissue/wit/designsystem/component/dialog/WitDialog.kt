package com.multissue.wit.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun WitDialog(
    showDialog: Boolean,
    title: String,
    message: String? = null,
    rightButtonText: String,
    rightButtonColor: Color,
    leftButtonText: String,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    bodyContent: @Composable () -> Unit = { },
    content: @Composable WitDialogScope.() -> Unit,
) {
    if (!showDialog) return

    val scope = remember(title, message, rightButtonText, rightButtonColor, leftButtonText) {
        DefaultWitDialogScope(
            title = title,
            message = message,
            bodyContent = { bodyContent() },
            rightButtonText = rightButtonText,
            rightButtonColor = rightButtonColor,
            leftButtonText = leftButtonText,
            onLeftButtonClick = { onLeftButtonClick() },
            onRightButtonClick = { onRightButtonClick() }
        )
    }
    Dialog(
        onDismissRequest = onLeftButtonClick
    ) {
        Surface(
            modifier = Modifier.wrapContentHeight(),
            shape = RoundedCornerShape(12.dp),
            color = WitTheme.colors.background,
            shadowElevation = 2.dp
        ) {
            scope.content()
        }
    }
}

@Composable
fun WitDialogScope.WitDialogTitle(
    modifier: Modifier = Modifier
) {
    val dialogTitle = title ?: return
    Text(
        modifier = modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = WitTheme.typography.titleL,
        color = WitTheme.colors.text,
        text = dialogTitle
    )
}

@Composable
fun WitDialogScope.WitDialogMessage(
    modifier: Modifier = Modifier
) {
    val dialogMessage = message ?: return
    Text(
        modifier = modifier
            .fillMaxWidth(),
        style = WitTheme.typography.bodyXS,
        color = WitTheme.colors.subText,
        text = dialogMessage
    )
}

@Composable
fun WitDialogScope.WitDialogRightButton(
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = rightButtonColor,
            contentColor = WitTheme.colors.buttonText
        ),
        onClick = onRightButtonClick
    ) {
        Text(
            text = rightButtonText,
            style = WitTheme.typography.titleM
        )
    }
}

@Composable
fun WitDialogScope.WitDialogLeftButton(
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = WitTheme.colors.disabledButton,
            contentColor = WitTheme.colors.disabledButtonText
        ),
        onClick = onLeftButtonClick
    ) {
        Text(
            text = leftButtonText,
            style = WitTheme.typography.titleM
        )
    }
}

@Composable
fun WitDialogScope.WitDialogDefaultLayout() {
    Column(
        modifier = Modifier
        .padding(top = 24.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WitDialogTitle()
            WitDialogMessage()
            bodyContent()
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WitDialogLeftButton(
                modifier = Modifier
                    .height(42.dp)
                    .weight(1f)
            )
            WitDialogRightButton(
                modifier = Modifier
                    .height(42.dp)
                    .weight(1f)
            )
        }
    }
}

data class WitDialogPreviewData(
    val title: String,
    val message: String? = null,
    val leftButtonText: String,
    val rightButtonText: String,
)

class WitDialogPreviewParameterProvider: PreviewParameterProvider<WitDialogPreviewData> {
    override val values: Sequence<WitDialogPreviewData> = sequenceOf(
        WitDialogPreviewData(
            title = "다이얼로그 타이틀",
            leftButtonText = "닫기",
            rightButtonText = "수정하기"
        ),
        WitDialogPreviewData(
            title = "다이얼로그 타이틀",
            message = "다이얼로그 메시지",
            leftButtonText = "닫기",
            rightButtonText = "수정하기"
        ),
    )
}

@Preview
@Composable
fun WitBaseDialogPreview(
    @PreviewParameter(WitDialogPreviewParameterProvider::class) data: WitDialogPreviewData
) {
    WitTheme {
        WitDialog(
            showDialog = true,
            title = data.title,
            message = data.message,
            leftButtonText = data.leftButtonText,
            rightButtonText = data.rightButtonText,
            rightButtonColor = WitTheme.colors.primaryDark,
            onLeftButtonClick = {},
            onRightButtonClick = {}
        ) {
            WitDialogDefaultLayout()
        }
    }
}

@Preview
@Composable
fun WitBodyContentDialogPreview(
    @PreviewParameter(WitDialogPreviewParameterProvider::class) data: WitDialogPreviewData
) {
    WitTheme {
        WitDialog(
            showDialog = true,
            title = data.title,
            message = data.message,
            leftButtonText = data.leftButtonText,
            rightButtonText = data.rightButtonText,
            rightButtonColor = WitTheme.colors.error,
            bodyContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(WitTheme.colors.gray600)
                )
            },
            onLeftButtonClick = {},
            onRightButtonClick = {}
        ) {
            WitDialogDefaultLayout()
        }
    }
}