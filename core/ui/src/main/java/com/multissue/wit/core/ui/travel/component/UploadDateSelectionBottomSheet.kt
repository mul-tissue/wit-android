package com.multissue.wit.core.ui.travel.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.core.ui.R
import com.multissue.wit.designsystem.component.spacer.SpH
import com.multissue.wit.designsystem.theme.WitTheme
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadDateSelectionBottomSheet(
    visible: Boolean,
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onOpenOtherDate: () -> Unit,
    onReset: () -> Unit,
    onDismiss: () -> Unit,
    onComplete: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var composedVisible by remember { mutableStateOf(visible) }
    LaunchedEffect(visible) {
        if (visible) {
            composedVisible = true
        } else {
            scope.launch {
                sheetState.hide()
                composedVisible = false
            }
        }
    }

    TravelFilterBottomSheet(
        visible = composedVisible,
        sheetState = sheetState,
        title = stringResource(
            R.string.travel_calendar_year_month_format,
            currentMonth.year,
            currentMonth.monthValue
        ),
        onClick = { onOpenOtherDate() },
        paddingHorizontal = 0.dp,
        onDismissRequest = onDismiss
    ) { _ ->
        SpH(24.dp)

        CalendarGridBox(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 24.dp),
            currentMonth = currentMonth,
            startDate = selectedDate,
            endDate = null,
            onDateSelected = onDateSelected
        )

        val selectionText = if (selectedDate != null) {
            stringResource(
                R.string.travel_date_single_format,
                selectedDate.monthValue, selectedDate.dayOfMonth
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
            isEnabled = selectedDate != null,
            onReset = onReset,
            onComplete = onComplete,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UploadDateSelectionBottomSheetPreview() {
    WitTheme {
        UploadDateSelectionBottomSheet(
            visible = true,
            currentMonth = YearMonth.now(),
            selectedDate = LocalDate.now(),
            onOpenOtherDate = {},
            onDateSelected = {},
            onReset = {},
            onDismiss = {},
            onComplete = {}
        )
    }
}
