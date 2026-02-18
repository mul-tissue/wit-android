package com.multissue.wit.feature.signup.component.wheelpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.absoluteValue

@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    items: List<Int>,
    nonFocusedItemCount: Int= 6,
    selectedIndex: Int,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp),
    itemHeightDp: Dp = 44.dp,
    onItemSelected: (Int) -> Unit,
    content: @Composable (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val visibleItems = nonFocusedItemCount / 2 * 2 + 1

    val height = itemHeightDp * visibleItems
    val density = LocalDensity.current

    val itemHeightPx by remember {
        mutableIntStateOf(with(density) { itemHeightDp.roundToPx() })
    }

    val edgeOffsetPx = remember(visibleItems, itemHeightPx) {
        (with(density) { height.toPx() } - itemHeightPx) / 2
    }

    val edgeOffsetDp = remember(visibleItems) {
        with(density) { edgeOffsetPx.absoluteValue.toDp() }
    }

    val state = key(items.size) {
        rememberLazyListState(
            initialFirstVisibleItemIndex = calcStartIndex(items.size, selectedIndex)
        )
    }

    LaunchedEffect(selectedIndex, items.size) {
        val info     = state.layoutInfo
        val vpCenter = (info.viewportStartOffset + info.viewportEndOffset) / 2
        val currentInfiniteIdx = info.visibleItemsInfo
            .minByOrNull { abs(it.offset + it.size / 2 - vpCenter) }
            ?.index
            ?: state.firstVisibleItemIndex

        val currentIndex = if (items.isEmpty()) 0 else currentInfiniteIdx % items.size

        if (currentIndex != selectedIndex && items.isNotEmpty()) {
            val target = currentInfiniteIdx - currentIndex + selectedIndex
            scope.launch {
                state.animateScrollToItem(target) }
        }
    }

    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress }
            .distinctUntilChanged()
            .filter { isScrolling -> !isScrolling }
            .collect {
                val info = state.layoutInfo
                val vpCenter = (info.viewportStartOffset + info.viewportEndOffset) / 2
                val index = info.visibleItemsInfo
                    .minByOrNull { abs(it.offset + it.size / 2 - vpCenter) }
                    ?.index
                    ?: return@collect

                val finalIndex = index % items.size
                if (finalIndex in items.indices) {
                    onItemSelected(items[finalIndex])
                }
            }
    }

    Box(
        modifier = modifier
            .height(height / (curveRate / viewportCurveRate))

    ) {
        LazyColumn(
            modifier = modifier
                .requiredHeight(height),
            state = state,
            contentPadding = PaddingValues(vertical = edgeOffsetDp),
            flingBehavior = rememberSnapFlingBehavior(state)
        ) {
            items(
                count = InfiniteItemsCount,
                key = { index -> "${index}_ ${items[index % items.size]}" },
            ) { index ->
                val currentIndex = index % items.size

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(itemHeightDp)
                        .noRippleClickable {
                            scope.launch {
                                state.animateScrollToItem(index)
                            }
                        }
                        .padding(contentPadding)
                        .graphicsLayer {
                            graphic3DEffect(
                                index = index,
                                layoutInfo = state.layoutInfo
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    content(currentIndex)
                }
            }
        }
    }
}

private fun calcStartIndex(itemsSize: Int, index: Int): Int {
    if (itemsSize == 0) return 0
    return InfiniteItemsCount / 2 - (InfiniteItemsCount / 2) % itemsSize + index
}

@Preview
@Composable
fun WheelPickerPreview() {
    val list = buildList {
        repeat(10) {
            add(it)
        }
    }
    WitTheme {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(WitTheme.colors.background),
            verticalArrangement = Arrangement.Center
        ) {
            WheelPicker(
                items = list,
                selectedIndex = 4,
                onItemSelected = {},
                content = {
                    Text(
                        text = "Item ${list[it]}",
                        style = WitTheme.typography.titleXL,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    }
}