package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UploadActivityTypePage(
    selectedActivityType: String,
    onActivityTypeSelected: (String) -> Unit,
) {
    ActivityTypeGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        selectedActivityType = selectedActivityType,
        onActivityTypeSelected = onActivityTypeSelected,
    )
}
