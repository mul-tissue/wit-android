package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.chip.WitFilterChip
import com.multissue.wit.feature.map.R

@Composable
fun TravelFilterChipRow(
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
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WitFilterChip(
            modifier = Modifier.fillMaxHeight(),
            isSelected = activityType.isNotEmpty(),
            text = activityType.ifEmpty { stringResource(R.string.travel_activity_type_placeholder) },
            onClick = onActivityFilterClick,
            onClear = onActivityClear
        )
        WitFilterChip(
            modifier = Modifier.fillMaxHeight(),
            isSelected = ageAndGender.isNotEmpty(),
            text = ageAndGender.ifEmpty { stringResource(R.string.travel_age_gender_placeholder) },
            onClick = onAgeAndGenderFilterClick,
            onClear = onAgeGenderClear
        )
        WitFilterChip(
            modifier = Modifier.fillMaxHeight(),
            isSelected = selectDate.isNotEmpty(),
            text = selectDate.ifEmpty { stringResource(R.string.travel_date_placeholder) },
            onClick = onDateFilterClick,
            onClear = onDateClear
        )
    }
}
