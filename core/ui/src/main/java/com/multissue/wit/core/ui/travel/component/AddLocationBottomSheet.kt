package com.multissue.wit.core.ui.travel.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.core.ui.R
import com.multissue.wit.designsystem.component.spacer.SpH
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLocationBottomSheet(
    visible: Boolean,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    onSkipClick: () -> Unit,
    onAddLocationClick: () -> Unit,
) {
    if (!visible) return

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onDismissRequest = {},
        sheetState = sheetState,
        containerColor = WitTheme.colors.background,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false,
            shouldDismissOnClickOutside = false
        ),
        contentWindowInsets = { BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom) },
        sheetGesturesEnabled = false,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            UploadSheetHeader(
                title = stringResource(R.string.location_sheet_title),
                showBack = true,
                onBack = {
                    scope.launch {
                        sheetState.hide()
                        onBackClick()
                    }
                },
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                        onCloseClick()
                    }
                }
            )

            SpH(22.dp)

            Text(
                text = stringResource(R.string.location_sheet_placeholder),
                style = WitTheme.typography.titleM,
                color = WitTheme.colors.subText
            )

            SpH(32.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                WitButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    title = stringResource(R.string.location_sheet_skip),
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            onSkipClick()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WitTheme.colors.disabledButton,
                        contentColor = WitTheme.colors.text
                    )
                )

                WitButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    title = stringResource(R.string.location_sheet_add),
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            onAddLocationClick()
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun AddLocationBottomSheetPreview() {
    WitTheme {
        AddLocationBottomSheet(
            visible = true,
            onBackClick = {},
            onCloseClick = {},
            onSkipClick = {},
            onAddLocationClick = {}
        )
    }
}
