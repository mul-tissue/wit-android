package com.multissue.wit.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.addFocusCleaner
import com.multissue.wit.feature.chat.component.ChatMessageInput
import com.multissue.wit.feature.chat.component.ChatRoomInfoCard
import com.multissue.wit.feature.chat.component.ChatRoomTopBar
import com.multissue.wit.feature.chat.component.DateDividerChip
import com.multissue.wit.feature.chat.component.ReceivedMessageItem
import com.multissue.wit.feature.chat.component.SentMessageItem
import com.multissue.wit.feature.chat.component.SystemNoticeMessage
import com.multissue.wit.feature.chat.state.ChatRoomMessageItem
import com.multissue.wit.feature.chat.state.ChatRoomUiIntent
import com.multissue.wit.feature.chat.state.ChatRoomUiState

@Composable
fun ChatRoomScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatRoomViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ChatRoomScreen(
        modifier = modifier,
        state = state,
        onIntent = viewModel::onIntent,
        onBackClick = onBackClick,
    )
}

@Composable
internal fun ChatRoomScreen(
    modifier: Modifier = Modifier,
    state: ChatRoomUiState,
    onIntent: (ChatRoomUiIntent) -> Unit,
    onBackClick: () -> Unit,
) {
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WitTheme.colors.background)
            .addFocusCleaner(focusManager)
            .imePadding()
    ) {
        ChatRoomTopBar(
            emoji = state.roomInfo.emoji,
            title = state.roomInfo.title,
            participantCount = state.roomInfo.participantCount,
            onBackClick = {
                onIntent(ChatRoomUiIntent.NavigateBack)
                onBackClick()
            },
        )

        ChatRoomInfoCard(
            dateTime = state.roomInfo.dateTime,
            location = state.roomInfo.location,
            currentCount = state.roomInfo.currentCount,
            maxCount = state.roomInfo.maxCount,
            onClick = { onIntent(ChatRoomUiIntent.NavigateToRoomInfo) },
            modifier = Modifier.padding(vertical = 12.dp),
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(state.messages) { item ->
                when (item) {
                    is ChatRoomMessageItem.DateDivider -> {
                        DateDividerChip(
                            dateText = item.dateText,
                            isToday = item.isToday,
                        )
                    }
                    is ChatRoomMessageItem.SystemNotice -> {
                        SystemNoticeMessage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 62.dp, vertical = 6.dp),
                            text = item.text
                        )
                    }
                    is ChatRoomMessageItem.ReceivedMessage -> {
                        ReceivedMessageItem(
                            senderName = item.senderName,
                            senderImageUrl = item.senderImageUrl,
                            message = item.message,
                            time = item.time,
                            unreadCount = item.unreadCount,
                        )
                    }
                    is ChatRoomMessageItem.SentMessage -> {
                        SentMessageItem(
                            message = item.message,
                            time = item.time,
                            unreadCount = item.unreadCount,
                        )
                    }
                }
            }
        }

        ChatMessageInput(
            inputText = state.inputText,
            onInputChange = { onIntent(ChatRoomUiIntent.UpdateInputText(it)) },
            onSendClick = { onIntent(ChatRoomUiIntent.SendMessage) },
        )
    }
}
