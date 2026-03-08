package com.multissue.wit.feature.chat

import androidx.lifecycle.viewModelScope
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.chat.dummy.chatRoomsDummy
import com.multissue.wit.feature.chat.state.ChatSideEffect
import com.multissue.wit.feature.chat.state.ChatUiIntent
import com.multissue.wit.feature.chat.state.ChatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : BaseViewModel<ChatUiState, ChatSideEffect, ChatUiIntent>(
    initialState = ChatUiState()
) {
    init {
        loadDummyData()
    }

    override fun onIntent(intent: ChatUiIntent) {
        when (intent) {
            is ChatUiIntent.EnterChatRoom -> postSideEffect(ChatSideEffect.NavigateToChatRoom(intent.chatRoomId))
            is ChatUiIntent.RequestDeleteChatRoom -> {
                setState { copy(showDeleteDialog = true, deletingChatRoomId = intent.chatRoomId) }
            }
            ChatUiIntent.ConfirmDeleteChatRoom -> {
                val roomId = currentState.deletingChatRoomId ?: return
                deleteChatRoom(roomId)
                setState { copy(showDeleteDialog = false, deletingChatRoomId = null) }
            }
            ChatUiIntent.DismissDeleteDialog -> {
                setState { copy(showDeleteDialog = false, deletingChatRoomId = null) }
            }
        }
    }

    private fun loadDummyData() {
        viewModelScope.launch {
            setState { copy(chatRooms = chatRoomsDummy) }
        }
    }

    private fun deleteChatRoom(chatRoomId: Int) {
        setState { copy(chatRooms = chatRooms.filter { it.id != chatRoomId }) }
    }
}
