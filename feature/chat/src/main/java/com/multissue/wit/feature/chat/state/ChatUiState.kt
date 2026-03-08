package com.multissue.wit.feature.chat.state

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

data class ChatUiState(
    val chatRooms: List<ChatRoomInfoState> = emptyList(),
    val showDeleteDialog: Boolean = false,
    val deletingChatRoomId: Int? = null,
) : UiState

sealed interface ChatSideEffect : UiSideEffect {
    data class NavigateToChatRoom(val chatRoomId: Int) : ChatSideEffect
}

sealed interface ChatUiIntent : UiIntent {
    data class EnterChatRoom(val chatRoomId: Int) : ChatUiIntent
    data class RequestDeleteChatRoom(val chatRoomId: Int) : ChatUiIntent
    data object ConfirmDeleteChatRoom : ChatUiIntent
    data object DismissDeleteDialog : ChatUiIntent
}
