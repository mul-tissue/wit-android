package com.multissue.wit.core.ui.travel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.core.ui.R
import com.multissue.wit.designsystem.component.spacer.SpH
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar
import com.multissue.wit.designsystem.theme.WitTheme
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarDateSelectionScreen(
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onReset: () -> Unit,
    onComplete: () -> Unit,
    onBack: () -> Unit
) {
    val currentMonth = remember { YearMonth.now() }
    val months = remember {
        (0..5).map { currentMonth.plusMonths(it.toLong()) }
    }
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

    Column(
        modifier = Modifier.fillMaxSize()
            .background(WitTheme.colors.background)
            .padding(bottom = 20.dp)
    ) {
        WitCenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(R.drawable.icon_close),
                        contentDescription = "닫기",
                        tint = WitTheme.colors.text
                    )
                }
            }
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(months) { month ->
                Text(
                    text = stringResource(
                        R.string.travel_calendar_year_month_format,
                        month.year,
                        month.monthValue
                    ),
                    style = WitTheme.typography.titleL,
                    color = WitTheme.colors.text
                )
                SpH(20.dp)
                CalendarGridBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    currentMonth = month,
                    startDate = startDate,
                    endDate = endDate,
                    onDateSelected = onDateSelected
                )
                if (month != months.last()) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 20.dp),
                        color = WitTheme.colors.divider
                    )
                }
            }
        }
        DateSelectBottomRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            title = selectionText,
            isEnabled = startDate != null,
            onReset = onReset,
            onComplete = onComplete
        )
    }
}

@Preview
@Composable
private fun CalendarDateSelectionScreenPreview() {
    WitTheme {
        CalendarDateSelectionScreen(
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(3),
            onDateSelected = {},
            onReset = {},
            onComplete = {},
            onBack = {}
        )
    }
}
