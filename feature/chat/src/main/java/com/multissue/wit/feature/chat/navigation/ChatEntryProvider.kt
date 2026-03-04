package com.multissue.wit.feature.chat.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.chat.ChatRoomScreen
import com.multissue.wit.feature.chat.ChatRoomViewModel
import com.multissue.wit.feature.chat.ChatScreen

fun EntryProviderScope<NavKey>.chatEntry(navigator: Navigator) {
    entry<ChatNavKey> {
        ChatScreen(
            onEnterChatRoom = { chatRoomId -> navigator.navigateToChatRoom(chatRoomId) },
        )
    }

    entry<ChatRoomNavKey> { key ->
        val viewModel = hiltViewModel<ChatRoomViewModel, ChatRoomViewModel.Factory> { factory ->
            factory.create(key)
        }

        ChatRoomScreen(
            viewModel = viewModel,
            onBackClick = { navigator.goBack() },
        )
    }
}
