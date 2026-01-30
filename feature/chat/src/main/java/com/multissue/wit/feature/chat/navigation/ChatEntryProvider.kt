package com.multissue.wit.feature.chat.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.chat.ChatScreen

fun EntryProviderScope<NavKey>.chatEntry(navigator: Navigator) {
    // TODO SnackBar
    entry<ChatNavKey> {
        ChatScreen(

        )
    }
}