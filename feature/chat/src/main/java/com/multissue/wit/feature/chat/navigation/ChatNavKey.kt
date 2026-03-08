package com.multissue.wit.feature.chat.navigation

import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
object ChatNavKey : NavKey

@Serializable
data class ChatRoomNavKey(
    val chatRoomId: Int
) : NavKey

fun Navigator.navigateToChatRoom(chatRoomId: Int) {
    navigate(ChatRoomNavKey(chatRoomId))
}
