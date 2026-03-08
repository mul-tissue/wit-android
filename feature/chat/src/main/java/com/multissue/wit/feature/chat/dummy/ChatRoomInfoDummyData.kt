package com.multissue.wit.feature.chat.dummy

import com.multissue.wit.feature.chat.state.ChatRoomInfoState
import com.multissue.wit.feature.chat.state.ChatRoomMessageItem

val chatRoomInfoDummy = ChatRoomInfoState(
    emoji = "🍺",
    title = "센강 근처 와인",
    participantCount = 3,
    dateTime = "2025. 12. 26 오후 1:30",
    location = "프랑스 파리",
    currentCount = 3,
    maxCount = 5,
)

val chatRoomMessagesDummy: List<ChatRoomMessageItem> = listOf(
    ChatRoomMessageItem.DateDivider(dateText = "Today", isToday = true),
    ChatRoomMessageItem.SystemNotice(text = "12월 26일 센강 근처 와인에\nPaprika님이 입장했어요"),
    ChatRoomMessageItem.ReceivedMessage(
        id = 1,
        senderName = "Paprika",
        senderImageUrl = "",
        message = "안녕하세요!",
        time = "오후 3:19",
        unreadCount = 0,
    ),
    ChatRoomMessageItem.SentMessage(
        id = 2,
        message = "안녕하세요!",
        time = "",
        unreadCount = 0,
    ),
    ChatRoomMessageItem.SentMessage(
        id = 3,
        message = "센 강 근처 Le Baron Rouge 어떠세요?",
        time = "오후 3:45",
        unreadCount = 0,
    ),
    ChatRoomMessageItem.ReceivedMessage(
        id = 4,
        senderName = "Paprika",
        senderImageUrl = "",
        message = "좋아요",
        time = "오후 3:19",
        unreadCount = 0,
    ),
    ChatRoomMessageItem.ReceivedMessage(
        id = 5,
        senderName = "Julkka",
        senderImageUrl = "",
        message = "오 너무 좋아요!! 나머지 메뉴는 만나서 정하는 거 어떠신가용?",
        time = "오후 00:00",
        unreadCount = 0,
    ),
    ChatRoomMessageItem.SentMessage(
        id = 6,
        message = "네 그럼 거기서 8시에 뵐게요",
        time = "오후 3:45",
        unreadCount = 1,
    ),
    ChatRoomMessageItem.ReceivedMessage(
        id = 7,
        senderName = "Paprika",
        senderImageUrl = "",
        message = "네 그럼 거기서 뵐게요 !!",
        time = "오후 3:19",
        unreadCount = 3,
    ),
)