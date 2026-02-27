package com.multissue.wit.feature.travel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.travel.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelReportBottomSheet(
    visible: Boolean,
    onCancel: () -> Unit,
    onReport: () -> Unit
) {
    if (!visible) return
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onDismissRequest = onCancel,
        sheetState = sheetState,
        containerColor = WitTheme.colors.background,
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
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(26.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onReport() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.travel_detail_reported_button),
                    textAlign = TextAlign.Center,
                    style = WitTheme.typography.titleM,
                    color = WitTheme.colors.error
                )
            }
        }
    }
}

@Preview
@Composable
private fun OptionBottomSheetPreview() {
    WitTheme {
        TravelReportBottomSheet(
            visible = true,
            onCancel = {},
            onReport = {}
        )
    }
}