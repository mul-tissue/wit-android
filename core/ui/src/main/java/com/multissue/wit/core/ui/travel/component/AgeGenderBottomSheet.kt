package com.multissue.wit.core.ui.travel.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.core.ui.R
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.component.chip.WitSelectableChip
import com.multissue.wit.designsystem.component.spacer.SpH
import com.multissue.wit.designsystem.theme.WitTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeGenderBottomSheet(
    visible: Boolean,
    ageOptions: List<String>,
    genderOptions: List<String>,
    selectedAge: String,
    selectedGender: String,
    onAgeSelected: (String) -> Unit,
    onGenderSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    onComplete: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    TravelFilterBottomSheet(
        visible = visible,
        title = stringResource(R.string.travel_filter_age_gender),
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.travel_filter_age),
                style = WitTheme.typography.titleS,
                color = WitTheme.colors.subText
            )
            SpH(12.dp)
            LazyVerticalGrid(
                modifier = Modifier
                    .wrapContentSize(),
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false
            ) {
                items(
                    items = ageOptions,
                    key = { ageOption -> ageOption }
                ) { age ->
                    WitSelectableChip(
                        isSelected = age == selectedAge,
                        text = age,
                        paddingHorizontal = 15.dp,
                        style = WitTheme.typography.bodyL,
                        onClick = { onAgeSelected(age) }
                    )
                }
            }

            SpH(24.dp)

            Text(
                text = stringResource(R.string.travel_filter_gender),
                style = WitTheme.typography.titleS,
                color = WitTheme.colors.subText
            )
            SpH(12.dp)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false
            ) {
                items(
                    items = genderOptions,
                    key = { genderOption -> genderOption }
                ) { gender ->
                    WitSelectableChip(
                        isSelected = gender == selectedGender,
                        paddingHorizontal = 15.dp,
                        style = WitTheme.typography.bodyL,
                        text = gender,
                        onClick = { onGenderSelected(gender) }
                    )
                }
            }
        }

        SpH(32.dp)

        WitButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
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
private fun AgeGenderBottomSheetPreview() {
    WitTheme {
        AgeGenderBottomSheet(
            visible = true,
            ageOptions = listOf("20대", "30대", "40대", "50대", "60대+", "무관"),
            genderOptions = listOf("남자", "여자", "무관"),
            selectedAge = "20대",
            selectedGender = "무관",
            onAgeSelected = {},
            onGenderSelected = {},
            onDismiss = {},
            onComplete = {}
        )
    }
}
