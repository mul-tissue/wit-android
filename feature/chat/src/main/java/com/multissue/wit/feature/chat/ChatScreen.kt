package com.multissue.wit.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multissue.wit.designsystem.component.dialog.WitDialog
import com.multissue.wit.designsystem.component.dialog.WitDialogTitleOnlyLayout
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.chat.component.ChatEmptyContent
import com.multissue.wit.feature.chat.component.ChatRoomItem
import com.multissue.wit.feature.chat.state.ChatSideEffect
import com.multissue.wit.feature.chat.state.ChatUiIntent
import com.multissue.wit.feature.chat.state.ChatUiState

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
    onEnterChatRoom: (Int) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is ChatSideEffect.NavigateToChatRoom -> onEnterChatRoom(effect.chatRoomId)
            }
        }
    }

    ChatScreen(
        modifier = modifier,
        state = state,
        onIntent = viewModel::onIntent,
    )
}

@Composable
internal fun ChatScreen(
    modifier: Modifier = Modifier,
    state: ChatUiState,
    onIntent: (ChatUiIntent) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
            .background(WitTheme.colors.background)
    ) {
        WitCenterAlignedTopAppBar(
            title = stringResource(R.string.chat_title),
        )

        if (state.chatRooms.isEmpty()) {
            ChatEmptyContent()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp),
            ) {
                items(state.chatRooms, key = { it.id }) { room ->
                    ChatRoomItem(
                        room = room,
                        onDelete = { onIntent(ChatUiIntent.RequestDeleteChatRoom(room.id)) },
                        onClick = { onIntent(ChatUiIntent.EnterChatRoom(room.id)) },
                    )
                }
            }
        }
    }

    WitDialog(
        showDialog = state.showDeleteDialog,
        title = stringResource(R.string.chat_exit_dialog_title),
        leftButtonText = stringResource(R.string.chat_exit_dialog_cancel),
        rightButtonText = stringResource(R.string.chat_exit_dialog_exit),
        rightButtonColor = WitTheme.colors.error,
        onLeftButtonClick = { onIntent(ChatUiIntent.DismissDeleteDialog) },
        onRightButtonClick = { onIntent(ChatUiIntent.ConfirmDeleteChatRoom) }
    ) {
        WitDialogTitleOnlyLayout()
    }
}
