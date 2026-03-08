package com.multissue.wit.feature.chat.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.chat.state.ChatRoomInfoState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private val RevealWidth = 72.dp

@Composable
fun ChatRoomItem(
    modifier: Modifier = Modifier,
    room: ChatRoomInfoState,
    onDelete: () -> Unit,
    onClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val revealWidthPx = with(LocalDensity.current) { RevealWidth.toPx() }
    val offsetX = remember { Animatable(0f) }
    val isRevealed = offsetX.value < 0f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clipToBounds(),
    ) {
        Box(
            modifier = Modifier
                .width(RevealWidth)
                .fillMaxHeight()
                .background(WitTheme.colors.error)
                .align(Alignment.CenterEnd)
                .noRippleClickable {
                    onDelete()
                    coroutineScope.launch { offsetX.animateTo(0f) }
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "나가기",
                style = WitTheme.typography.bodyM,
                color = WitTheme.colors.white100,
            )
        }

        ChatRoomContent(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val target = (offsetX.value + delta).coerceIn(-revealWidthPx, 0f)
                        coroutineScope.launch { offsetX.snapTo(target) }
                    },
                    onDragStopped = {
                        coroutineScope.launch {
                            if (offsetX.value < -revealWidthPx * 0.4f) {
                                offsetX.animateTo(-revealWidthPx, animationSpec = spring())
                            } else {
                                offsetX.animateTo(0f, animationSpec = spring())
                            }
                        }
                    },
                ),
            room = room,
            onClick = {
                if (isRevealed) {
                    coroutineScope.launch { offsetX.animateTo(0f) }
                } else {
                    onClick()
                }
            },
        )
    }
}
