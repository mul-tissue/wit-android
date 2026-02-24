package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.feature.map.component.SpH
import com.multissue.wit.feature.map.component.SpW
import com.multissue.wit.feature.map.dummy.travelDummyData

@Composable
fun TravelBottomSheetContent(
    modifier: Modifier = Modifier,
    activityType: String,
    ageAndGender: String,
    selectDate: String,
    onActivityFilterClick: () -> Unit,
    onAgeAndGenderFilterClick: () -> Unit,
    onDateFilterClick: () -> Unit,
    onActivityClear: () -> Unit,
    onAgeGenderClear: () -> Unit,
    onDateClear: () -> Unit,
    onReloadClick: () -> Unit,
    onChatClick: (Int) -> Unit,
    onItemClick: (Int) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TravelFilterChipRow(
                modifier = Modifier.weight(1f),
                activityType = activityType,
                ageAndGender = ageAndGender,
                selectDate = selectDate,
                onActivityFilterClick = onActivityFilterClick,
                onAgeAndGenderFilterClick = onAgeAndGenderFilterClick,
                onDateFilterClick = onDateFilterClick,
                onActivityClear = onActivityClear,
                onAgeGenderClear = onAgeGenderClear,
                onDateClear = onDateClear
            )

            SpW(8.dp)

            ReloadCircleButton(
                modifier = Modifier.size(34.dp)
            ) {
                onReloadClick()
            }
        }

        SpH(10.dp)

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(travelDummyData) { travelItem ->
                TravelListItem(
                    travelItem = travelItem,
                    onChatClick = onChatClick,
                    onItemClicked = onItemClick
                )
            }
        }
        SpH(16.dp)
    }
}
