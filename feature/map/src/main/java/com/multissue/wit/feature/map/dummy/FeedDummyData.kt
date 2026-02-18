package com.multissue.wit.feature.map.dummy

import com.multissue.wit.feature.map.state.FeedItemState
import com.multissue.wit.feature.map.state.PlaceItemState

val placeDummyList = listOf(
    PlaceItemState(
        cityName = "도쿄",
        districtName = "시부야구",
        titleThumbnailUrl = "https://picsum.photos/id/160/400/400"
    ),
    PlaceItemState(
        cityName = "도쿄",
        districtName = "다이토구",
        titleThumbnailUrl = "https://picsum.photos/id/161/400/400"

    ),
    PlaceItemState(
        cityName = "도쿄",
        districtName = "신주쿠구",
        titleThumbnailUrl = "https://picsum.photos/id/162/400/400"
    ),
    PlaceItemState(
        cityName = "도쿄",
        districtName = "미나토구",
        titleThumbnailUrl = "https://picsum.photos/id/163/400/400"
    )
)

val feedDummyList = listOf(
    FeedItemState(
        thumbnailUrl = "https://picsum.photos/id/27/300/400",
        username = "parkparki",
        userThumbnail = "https://picsum.photos/id/60/400/400",
        likeCount = 132
    ),
    FeedItemState(
        thumbnailUrl = "https://picsum.photos/id/28/300/400",
        username = "make",
        userThumbnail = "https://picsum.photos/id/61/400/400",
    ),
    FeedItemState(
        thumbnailUrl = "https://picsum.photos/id/29/300/400",
        username = "tree",
        userThumbnail = "https://picsum.photos/id/62/400/400",
        isLiked = true,
        likeCount = 67
    ),
    FeedItemState(
        thumbnailUrl = "https://picsum.photos/id/30/300/400",
        username = "noda",
        userThumbnail = "https://picsum.photos/id/63/400/400",
    ),
    FeedItemState(
        thumbnailUrl = "https://picsum.photos/id/31/300/400",
        username = "clan",
        userThumbnail = "https://picsum.photos/id/64/400/400",
    ),
)