package com.multissue.wit.feature.upload.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticeBottomSheet(
    visible: Boolean,
    onConfirmButtonClicked: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (!visible) return

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        sheetGesturesEnabled = false,
        containerColor = WitTheme.colors.background,
        contentColor = WitTheme.colors.text,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        contentWindowInsets = { BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom) },
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(28.dp)
                    .height(2.dp)
                    .background(WitTheme.colors.disabledText),
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(26.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "수정 불가 안내",
                style = WitTheme.typography.titleXL.copy(
                    textAlign = TextAlign.Center
                )
            )
            SpH(28.dp)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "등록 후에는 수정할 수 없어요. 계속할까요?",
                style = WitTheme.typography.titleM,
                color = WitTheme.colors.subText
            )
            SpH(32.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                WitButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    onClick = onDismiss,
                    textStyle = WitTheme.typography.titleM.copy(
                        color = WitTheme.colors.text
                    ),
                    title = "닫기",
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WitTheme.colors.disabledButton,
                        contentColor = WitTheme.colors.buttonText,
                        disabledContainerColor = WitTheme.colors.disabledButton,
                        disabledContentColor = WitTheme.colors.background
                    ),
                )
                WitButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    onClick = onConfirmButtonClicked,
                    textStyle = WitTheme.typography.titleM.copy(
                        color = WitTheme.colors.background
                    ),
                    title = "등록하기",
                )
            }
        }
    }
}