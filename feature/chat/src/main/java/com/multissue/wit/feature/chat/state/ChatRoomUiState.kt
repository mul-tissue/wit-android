package com.multissue.wit.feature.chat.state

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

data class ChatRoomUiState(
    val isLoading: Boolean = false,
    val roomInfo: ChatRoomInfoState = ChatRoomInfoState(),
    val messages: List<ChatRoomMessageItem> = emptyList(),
    val inputText: String = "",
) : UiState

data class ChatRoomInfoState(
    val id: Int = 0,
    val emoji: String = "",
    val title: String = "",
    val lastMessage: String = "",
    val lastMessageTime: String = "",
    val unreadCount: Int = 0,
    val participantCount: Int = 0,
    val dateTime: String = "",
    val location: String = "",
    val meetingDate: String = "", // 만나는 날짜 추가
    val currentCount: Int = 0,
    val maxCount: Int = 0,
    val profileImageUrl: String = "",
)

sealed class ChatRoomMessageItem {
    data class DateDivider(
        val dateText: String,
        val isToday: Boolean,
    ) : ChatRoomMessageItem()

    data class SystemNotice(
        val text: String,
    ) : ChatRoomMessageItem()

    data class ReceivedMessage(
        val id: Int,
        val senderName: String,
        val senderImageUrl: String,
        val message: String,
        val time: String,
        val unreadCount: Int = 0,
    ) : ChatRoomMessageItem()

    data class SentMessage(
        val id: Int,
        val message: String,
        val time: String,
        val unreadCount: Int = 0,
    ) : ChatRoomMessageItem()
}

sealed class ChatRoomUiIntent : UiIntent {
    data class UpdateInputText(val text: String) : ChatRoomUiIntent()
    object SendMessage : ChatRoomUiIntent()
    object NavigateBack : ChatRoomUiIntent()
    object NavigateToRoomInfo : ChatRoomUiIntent()
}

sealed interface ChatRoomSideEffect : UiSideEffect {
    object NavigateBack : ChatRoomSideEffect
    object NavigateToRoomInfo : ChatRoomSideEffect
}
