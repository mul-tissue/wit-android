package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.component.SpH
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarGridBox(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val today = LocalDate.now()

    val days = (1..daysInMonth).map { currentMonth.atDay(it) }
    val calendarDays = List(firstDayOfWeek) { null } + days

    val weekdays = listOf(
        stringResource(R.string.day_sun) to WitTheme.colors.error,
        stringResource(R.string.day_mon) to WitTheme.colors.subText,
        stringResource(R.string.day_tue) to WitTheme.colors.subText,
        stringResource(R.string.day_wed) to WitTheme.colors.subText,
        stringResource(R.string.day_thu) to WitTheme.colors.subText,
        stringResource(R.string.day_fri) to WitTheme.colors.subText,
        stringResource(R.string.day_sat) to WitTheme.colors.primaryDark
    )
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            weekdays.forEach { (name, color) ->
                Text(
                    modifier = Modifier.weight(1f),
                    text = name,
                    textAlign = TextAlign.Center,
                    style = WitTheme.typography.bodyS,
                    color = color
                )
            }
        }

        SpH(24.dp)

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            columns = GridCells.Fixed(7),
            userScrollEnabled = false
        ) {
            items(
                items = calendarDays
            ) { date ->
                if (date == null) {
                    Box(modifier = Modifier.aspectRatio(1f))
                } else {
                    val isPast = date.isBefore(today)
                    val isSelectedStart = date == startDate
                    val isSelectedEnd = date == endDate
                    val isInRange = startDate != null && endDate != null &&
                            date.isAfter(startDate) && date.isBefore(endDate)

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        // 날짜 선택 연결 Box
                        if (startDate != null && endDate != null) {
                            if (isInRange || isSelectedStart || isSelectedEnd) {

                                val shape = when {
                                    isSelectedStart -> RoundedCornerShape(
                                        topStart = 4.dp,
                                        bottomStart = 4.dp
                                    )

                                    isSelectedEnd -> RoundedCornerShape(
                                        topEnd = 4.dp,
                                        bottomEnd = 4.dp
                                    )

                                    else -> RoundedCornerShape(0.dp)
                                }

                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(
                                            color = WitTheme.colors.primaryDark,
                                            shape = shape
                                        )
                                )
                            }
                        }


                        // 날짜 선택 Box
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (isSelectedStart || isSelectedEnd) WitTheme.colors.primaryDark else Color.Transparent
                                )
                                .noRippleClickable(enabled = !isPast) { onDateSelected(date) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = date.dayOfMonth.toString(),
                                style = WitTheme.typography.titleM,
                                color = when {
                                    isInRange || isSelectedStart || isSelectedEnd -> WitTheme.colors.background
                                    isPast -> WitTheme.colors.border
                                    date.dayOfWeek.value == 7 -> WitTheme.colors.error
                                    date.dayOfWeek.value == 6 -> WitTheme.colors.primaryDark
                                    else -> WitTheme.colors.text
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CalendarGridBoxPreview() {
    WitTheme {
        CalendarGridBox(
            modifier = Modifier.background(WitTheme.colors.background),
            currentMonth = YearMonth.now(),
            startDate = LocalDate.of(2026, 3, 25),
            endDate = LocalDate.of(2026, 3, 27),
            onDateSelected = {},
        )
    }
}