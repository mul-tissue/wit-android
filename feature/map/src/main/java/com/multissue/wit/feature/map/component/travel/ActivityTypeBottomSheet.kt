package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.component.SpH
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityTypeBottomSheet(
    visible: Boolean,
    selectedActivityType: String,
    onActivityTypeSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    onComplete: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    TravelFilterBottomSheet(
        visible = visible,
        sheetState = sheetState,
        title = stringResource(R.string.travel_filter_activity_type),
        onDismissRequest = onDismiss
    ) {
        ActivityTypeGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            selectedActivityType = selectedActivityType,
            onActivityTypeSelected = onActivityTypeSelected
        )

        SpH(18.dp)

        WitButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = selectedActivityType.isNotEmpty(),
            title = stringResource(R.string.travel_select_done)
        ) {
            it.launch {
                sheetState.hide()
                onComplete()
            }
        }
    }
}

@Preview
@Composable
private fun ActivityTypeBottomSheetPreview() {
    WitTheme {
        ActivityTypeBottomSheet(
            visible = true,
            selectedActivityType = "",
            onActivityTypeSelected = {},
            onDismiss = {},
            onComplete = {}
        )
    }
}
