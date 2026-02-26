package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.chip.WitSelectableChip
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R

@Composable
fun UploadConditionPage(
    maxParticipants: Int,
    ageOptions: List<String>,
    selectedAge: String,
    genderOptions: List<String>,
    selectedGender: String,
    onParticipantsSelected: (Int) -> Unit,
    onAgeSelected: (String) -> Unit,
    onGenderSelected: (String) -> Unit,
) {
    val participantOptions = remember { (1..5).toList() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // 인원 수
        Text(
            text = stringResource(R.string.upload_participants_label),
            style = WitTheme.typography.titleS,
            color = WitTheme.colors.subText,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false,
        ) {
            items(participantOptions) { count ->
                WitSelectableChip(
                    isSelected = count == maxParticipants,
                    text = stringResource(R.string.upload_participants_count, count),
                    paddingHorizontal = 15.dp,
                    style = WitTheme.typography.bodyL,
                    onClick = { onParticipantsSelected(count) },
                )
            }
        }

        // 나이
        Text(
            text = stringResource(R.string.travel_filter_age),
            style = WitTheme.typography.titleS,
            color = WitTheme.colors.subText,
        )
        LazyVerticalGrid(
            modifier = Modifier.wrapContentHeight(),
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false,
        ) {
            items(ageOptions) { age ->
                WitSelectableChip(
                    isSelected = age == selectedAge,
                    text = age,
                    paddingHorizontal = 15.dp,
                    style = WitTheme.typography.bodyL,
                    onClick = { onAgeSelected(age) },
                )
            }
        }

        // 성별
        Text(
            text = stringResource(R.string.travel_filter_gender),
            style = WitTheme.typography.titleS,
            color = WitTheme.colors.subText,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false,
        ) {
            items(genderOptions) { gender ->
                WitSelectableChip(
                    isSelected = gender == selectedGender,
                    text = gender,
                    paddingHorizontal = 15.dp,
                    style = WitTheme.typography.bodyL,
                    onClick = { onGenderSelected(gender) },
                )
            }
        }
    }
}
