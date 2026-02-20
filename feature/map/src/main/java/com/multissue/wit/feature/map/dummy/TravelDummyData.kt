package com.multissue.wit.feature.map.dummy

import com.multissue.wit.feature.map.state.travel.TravelItemState

val travelDummyData = listOf(
    TravelItemState(
        id = 1,
        title = "루브르 미술관 투어",
        description = "Parprika님 외 2명이 함께해요",
        meetingDate = "2025. 12. 26 오후 1:30",
        activityType = "🎨 전시/미술",
        dayDiff = 0,
        location = "루브르 미술관",
        maxParticipants = 3,
        currentParticipants = 1,
        companionThumbnails = listOf(
            "https://picsum.photos/200",
            "https://picsum.photos/201"
        )
    ),
    TravelItemState(
        id = 2,
        title = "에펠탑 야경 산책",
        description = "야경 보면서 사진 찍으실 분!",
        meetingDate = "2025. 12. 27 오후 8:00",
        activityType = "🎨 전시/미술",
        dayDiff = 1,
        location = "에펠탑 근처",
        maxParticipants = 4,
        currentParticipants = 2,
        companionThumbnails = listOf(
            "https://picsum.photos/202",
            "https://picsum.photos/203"
        )
    ),
    TravelItemState(
        id = 3,
        title = "몽마르트르 언덕 피크닉",
        description = "와인이랑 치즈 사서 가요",
        meetingDate = "2025. 12. 28 오후 2:00",
        activityType = "🍺 술",
        dayDiff = 2,
        location = "몽마르트르 사크레쾨르 대성당 앞",
        maxParticipants = 6,
        currentParticipants = 3,
        companionThumbnails = listOf(
            "https://picsum.photos/204",
            "https://picsum.photos/205",
            "https://picsum.photos/206"
        )
    ),
    TravelItemState(
        id = 4,
        title = "파리 시내 자전거 투어",
        description = "시원하게 자전거 타실 분 구함",
        meetingDate = "2025. 12. 29 오전 10:00",
        activityType = "⚡️번개",
        dayDiff = 3,
        location = "파리 시청 앞 광장",
        maxParticipants = 5,
        currentParticipants = 1,
        companionThumbnails = listOf(
            "https://picsum.photos/207"
        )
    )
)