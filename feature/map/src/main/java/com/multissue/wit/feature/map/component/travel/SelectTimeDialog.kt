package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.dialog.WitDialog
import com.multissue.wit.designsystem.component.dialog.WitDialogLeftButton
import com.multissue.wit.designsystem.component.dialog.WitDialogRightButton
import com.multissue.wit.designsystem.component.dialog.WitDialogTitle
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.state.travel.AmPm
import com.multissue.wit.feature.map.state.travel.PickerHour
import com.multissue.wit.feature.map.state.travel.PickerMinute
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlin.math.abs

private val ITEM_HEIGHT = 44.dp
private const val VISIBLE_ITEMS = 3

@Composable
fun SelectTimeDialog(
    showDialog: Boolean,
    selectedAmPm: AmPm = AmPm.AM,
    selectedHour: PickerHour = PickerHour.NINE,
    selectedMinute: PickerMinute = PickerMinute.ZERO,
    isUndecided: Boolean = false,
    onDismiss: () -> Unit,
    onTimeSelected: (amPm: AmPm, hour: PickerHour, minute: PickerMinute) -> Unit,
    onUndecidedSelected: () -> Unit,
) {
    var amPm by remember(selectedAmPm) { mutableStateOf(selectedAmPm) }
    var hour by remember(selectedHour) { mutableStateOf(selectedHour) }
    var minute by remember(selectedMinute) { mutableStateOf(selectedMinute) }
    var undecided by remember(isUndecided) { mutableStateOf(isUndecided) }

    val amPmItems = remember { AmPm.entries.map { it.name } }
    val hourItems = remember { PickerHour.entries.map { it.displayText } }
    val minuteItems = remember { PickerMinute.entries.map { it.displayText } }

    WitDialog(
        showDialog = showDialog,
        title = stringResource(R.string.travel_time_select),
        leftButtonText = stringResource(R.string.travel_close),
        rightButtonText = stringResource(R.string.travel_select),
        rightButtonColor = WitTheme.colors.primaryDark,
        onLeftButtonClick = onDismiss,
        onRightButtonClick = {
            if (undecided) onUndecidedSelected()
            else onTimeSelected(amPm, hour, minute)
        },
        bodyContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ITEM_HEIGHT * VISIBLE_ITEMS)
                    .padding(horizontal = 24.dp)
                    .alpha(if (undecided) 0.3f else 1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TimePickerColumn(
                        modifier = Modifier.weight(1f),
                        isUndecided = undecided,
                        items = hourItems,
                        selectedIndex = hour.ordinal,
                        onItemSelected = { hour = PickerHour.entries[it] },
                    )
                    TimePickerColumn(
                        modifier = Modifier.weight(1f),
                        isUndecided = undecided,
                        items = minuteItems,
                        selectedIndex = minute.ordinal,
                        onItemSelected = { minute = PickerMinute.entries[it] },
                    )
                    TimePickerColumn(
                        modifier = Modifier.weight(1f),
                        isUndecided = undecided,
                        items = amPmItems,
                        selectedIndex = amPm.ordinal,
                        onItemSelected = { amPm = AmPm.entries[it] },
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .align(Alignment.TopStart)
                        .offset(y = ITEM_HEIGHT)
                        .background(WitTheme.colors.divider)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .align(Alignment.TopStart)
                        .offset(y = ITEM_HEIGHT * 2)
                        .background(WitTheme.colors.divider)
                )
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(top = 24.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                WitDialogTitle(
                    modifier = Modifier.align(Alignment.Center)
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .noRippleClickable { undecided = !undecided },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (undecided) WitTheme.colors.primaryDark
                                else WitTheme.colors.disabledButton
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_checked),
                            contentDescription = null,
                            tint = WitTheme.colors.iconTintConverse,
                        )
                    }
                    Text(
                        text = stringResource(R.string.travel_time_tbc),
                        style = WitTheme.typography.titleM,
                        color = WitTheme.colors.text,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            bodyContent()
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                WitDialogLeftButton(
                    modifier = Modifier
                        .height(42.dp)
                        .weight(1f)
                )
                WitDialogRightButton(
                    modifier = Modifier
                        .height(42.dp)
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TimePickerColumn(
    modifier: Modifier = Modifier,
    isUndecided: Boolean,
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)

    val centeredIndex by remember {
        derivedStateOf {
            val info = listState.layoutInfo
            if (info.visibleItemsInfo.isEmpty()) return@derivedStateOf 0
            val vpCenter = (info.viewportStartOffset + info.viewportEndOffset) / 2
            info.visibleItemsInfo
                .minByOrNull { abs(it.offset + it.size / 2 - vpCenter) }
                ?.index
                ?: 0
        }
    }

    LaunchedEffect(selectedIndex) {
        if (centeredIndex != selectedIndex) {
            listState.animateScrollToItem(selectedIndex)
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .distinctUntilChanged()
            .filter { !it }
            .collect {
                val idx = centeredIndex
                if (idx in items.indices) {
                    onItemSelected(idx)
                }
            }
    }

    LazyColumn(
        modifier = modifier.height(ITEM_HEIGHT * VISIBLE_ITEMS),
        state = listState,
        contentPadding = PaddingValues(vertical = ITEM_HEIGHT),
        flingBehavior = rememberSnapFlingBehavior(listState),
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = !isUndecided
    ) {
        items(items.size) { index ->
            val isSelected = index == centeredIndex
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ITEM_HEIGHT),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = items[index],
                    style = WitTheme.typography.titleXL,
                    color = if (isSelected) WitTheme.colors.text else WitTheme.colors.gray600,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SelectTimeDialogPreview() {
    WitTheme {
        SelectTimeDialog(
            showDialog = true,
            onDismiss = {},
            onTimeSelected = { _, _, _ -> },
            onUndecidedSelected = {},
        )
    }
}

@Preview
@Composable
private fun SelectTimeDialogUndecidedPreview() {
    WitTheme {
        SelectTimeDialog(
            showDialog = true,
            isUndecided = true,
            onDismiss = {},
            onTimeSelected = { _, _, _ -> },
            onUndecidedSelected = {},
        )
    }
}
