package com.multissue.wit.feature.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.chat.dummy.chatRoomInfoDummy
import com.multissue.wit.feature.chat.dummy.chatRoomMessagesDummy
import com.multissue.wit.feature.chat.navigation.ChatRoomNavKey
import com.multissue.wit.feature.chat.state.ChatRoomMessageItem
import com.multissue.wit.feature.chat.state.ChatRoomSideEffect
import com.multissue.wit.feature.chat.state.ChatRoomUiIntent
import com.multissue.wit.feature.chat.state.ChatRoomUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ChatRoomViewModel.Factory::class)
class ChatRoomViewModel @AssistedInject constructor(
    private val savedStateHandle: SavedStateHandle,
    @Assisted val key: ChatRoomNavKey
) : BaseViewModel<ChatRoomUiState, ChatRoomSideEffect, ChatRoomUiIntent>(
    initialState = ChatRoomUiState()
) {
    init {
        loadDummyData()
    }

    override fun onIntent(intent: ChatRoomUiIntent) {
        when (intent) {
            is ChatRoomUiIntent.UpdateInputText -> setState { copy(inputText = intent.text) }
            is ChatRoomUiIntent.SendMessage -> sendMessage()
            is ChatRoomUiIntent.NavigateBack -> postSideEffect(ChatRoomSideEffect.NavigateBack)
            is ChatRoomUiIntent.NavigateToRoomInfo -> postSideEffect(ChatRoomSideEffect.NavigateToRoomInfo)
        }
    }

    private fun loadDummyData() {
        viewModelScope.launch {
            // TODO: Use key.chatRoomId to load real data
            setState {
                copy(
                    roomInfo = chatRoomInfoDummy,
                    messages = chatRoomMessagesDummy,
                )
            }
        }
    }

    private fun sendMessage() {
        val text = currentState.inputText.trim()
        if (text.isEmpty()) return

        // TODO("나중에 실제로 API로 변경")
        val newMessage = ChatRoomMessageItem.SentMessage(
            id = currentState.messages.size + 1,
            message = text,
            time = "방금",
            unreadCount = 0,
        )
        setState {
            copy(
                messages = messages + newMessage,
                inputText = "",
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(key: ChatRoomNavKey): ChatRoomViewModel
    }
}
