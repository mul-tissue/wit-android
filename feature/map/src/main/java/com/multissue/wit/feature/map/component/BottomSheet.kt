package com.multissue.wit.feature.map.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBottomSheetScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    sheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 60.dp, // 최소화 높이
        sheetContainerColor = WitTheme.colors.white100,
        sheetShadowElevation = 8.dp,
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle(
                modifier = Modifier.clickable(enabled = false){},
                height = 3.dp,
                color = WitTheme.colors.gray200
            )
        },
        sheetContent = sheetContent
    ) { paddingValues ->
        content(paddingValues)
    }
}