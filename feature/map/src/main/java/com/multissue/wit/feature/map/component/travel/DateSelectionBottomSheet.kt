package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.component.SpH
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectionBottomSheet(
    visible: Boolean,
    currentMonth: YearMonth,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onOpenOtherDate: () -> Unit,
    onReset: () -> Unit,
    onDismiss: () -> Unit,
    onComplete: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    TravelFilterBottomSheet(
        visible = visible,
        sheetState = sheetState,
        title = stringResource(
            R.string.travel_calendar_year_month_format,
            currentMonth.year,
            currentMonth.monthValue
        ),
        onClick = {
            onOpenOtherDate()
        },
        paddingHorizontal = 0.dp,
        onDismissRequest = onDismiss
    ) { scope ->
        SpH(24.dp)

        CalendarGridBox(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 24.dp),
            currentMonth = currentMonth,
            startDate = startDate,
            endDate = endDate,
            onDateSelected = onDateSelected
        )

        val selectionText = if (startDate != null && endDate != null) {
            stringResource(
                R.string.travel_date_range_format,
                startDate.monthValue, startDate.dayOfMonth,
                endDate.monthValue, endDate.dayOfMonth
            )
        } else if (startDate != null) {
            stringResource(
                R.string.travel_date_single_format,
                startDate.monthValue, startDate.dayOfMonth
            )
        } else {
            stringResource(R.string.travel_filter_date_title_placeholder)
        }

        SpH(24.dp)

        DateSelectBottomRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            title = selectionText,
            isEnabled = startDate != null,
            onReset = onReset,
            onComplete = {
                scope.launch {
                    sheetState.hide()
                    onComplete()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DateSelectionBottomSheetPreview() {
    WitTheme {
        DateSelectionBottomSheet(
            visible = true,
            currentMonth = YearMonth.now(),
            startDate = LocalDate.of(2026, 3, 25),
            endDate = LocalDate.of(2026, 3, 27),
            onOpenOtherDate = {},
            onDateSelected = {},
            onReset = {},
            onDismiss = {},
            onComplete = {}
        )
    }
}
