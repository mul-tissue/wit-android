package com.multissue.wit.feature.signup.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.signup.R
import com.multissue.wit.feature.signup.component.wheelpicker.WheelPicker
import com.multissue.wit.feature.signup.util.DateUtils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectDateDialog(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    selectedYear: Int,
    selectedMonth: Int,
    selectedDay: Int,
    onDismissRequest: () -> Unit,
    onDateSelected: (year: Int, month: Int, day: Int) -> Unit
) {
    val currentYear = DateUtils.currentYear()

    var year by remember { mutableIntStateOf(selectedYear) }
    var month by remember { mutableIntStateOf(selectedMonth) }
    var day by remember { mutableIntStateOf(selectedDay) }

    val yearList = remember { (currentYear - 100..currentYear).toList() }
    val monthList = remember { (1..12).toList() }
    val dayList = remember(year, month) { (1..DateUtils.daysInMonth(year, month)).toList() }

    LaunchedEffect(dayList) {
        if (day > dayList.last()) {
            day = dayList.last()
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(WitTheme.colors.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(R.string.birth_date_title),
                    style = WitTheme.typography.titleL
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(36.dp)
                            .padding(horizontal = 32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(WitTheme.colors.white200),
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 42.dp, vertical = 18.dp),

                    ) {
                        WheelPicker(
                            modifier = Modifier.weight(1.2f),
                            selectedIndex = yearList.indexOf(year),
                            items = yearList,
                            onItemSelected = {
                                year = it
                            },
                            content = {
                                Text(
                                    text = stringResource(R.string.birth_format_date_year, yearList[it]),
                                    style = WitTheme.typography.titleXL,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                        WheelPicker(
                            modifier = Modifier.weight(1f),
                            selectedIndex = monthList.indexOf(month),
                            items = monthList,
                            onItemSelected = {
                                month = it
                            },
                            content = {
                                Text(
                                    text = stringResource(R.string.birth_format_date_month, monthList[it]),
                                    style = WitTheme.typography.titleXL,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                        WheelPicker(
                            modifier = Modifier.weight(1f),
                            selectedIndex = dayList.indexOf(day),
                            items = dayList,
                            onItemSelected = {
                                day = it
                            },
                            content = {
                                Text(
                                    text = stringResource(R.string.birth_format_date_day, dayList[it]),
                                    style = WitTheme.typography.titleXL,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    WitButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        title = stringResource(R.string.button_close),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = WitTheme.colors.text
                        )
                    ) {
                        onDismissRequest()
                    }

                    WitButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        title = stringResource(R.string.button_select),
                    ) {
                        onDateSelected(year, month, day)
                        onDismissRequest()
                    }
                }
            }
        }
    }
}