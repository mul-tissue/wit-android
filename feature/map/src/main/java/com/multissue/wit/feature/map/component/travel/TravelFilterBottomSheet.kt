package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelFilterBottomSheet(
    sheetState: SheetState,
    visible: Boolean,
    title: String,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.(CoroutineScope) -> Unit,
) {
    if (!visible) return

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = Modifier
            .padding(bottom = 30.dp)
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
        sheetGesturesEnabled = false,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
        ) {
            TravelSheetTitleRow(
                modifier = Modifier.padding(horizontal = 20.dp),
                title = title,
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                        onDismissRequest()
                    }
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = WitTheme.colors.background,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content(scope)
            }
        }
    }
}
