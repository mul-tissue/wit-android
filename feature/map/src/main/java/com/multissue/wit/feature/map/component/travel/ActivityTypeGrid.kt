package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun ActivityTypeGrid(
    modifier: Modifier = Modifier,
    selectedActivityType: String,
    onActivityTypeSelected: (String) -> Unit
) {
    // TODO("API 연결 시 응답값으로 변경")
    val items = listOf(
        "식사" to "🍽️",
        "카페" to "☕",
        "술" to "🍺",
        "전시/미술관" to "🖼️",
        "쇼핑" to "🛍️",
        "운동" to "💪",
        "산책" to "🚶",
        "기타" to "🎸"
    )

    LazyHorizontalGrid(
        modifier = modifier,
        rows = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = items,
            key = { item ->
                "${item.first}-${item.second}"
            }
        ) { (name, icon) ->
            ActivityTypeItem(
                name = name,
                icon = icon,
                isSelected = name == selectedActivityType,
                onClick = { onActivityTypeSelected(name) }
            )
        }
    }
}

@Preview
@Composable
fun ActivityTypeGridPreview() {
    WitTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(WitTheme.colors.background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActivityTypeGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                selectedActivityType = "식사",
                onActivityTypeSelected = {}
            )
        }
    }
}
