package com.multissue.wit.feature.chat.navigation

import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class ChatNavKey(
    val chatRoomId: Int? = null,
) : NavKey

fun Navigator.navigateToChatRoom(chatRoomId: Int) {
    navigate(ChatNavKey(chatRoomId))
}
