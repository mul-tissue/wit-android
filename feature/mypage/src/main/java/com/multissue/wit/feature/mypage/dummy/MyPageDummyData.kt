package com.multissue.wit.feature.mypage.dummy

import com.google.android.gms.maps.model.LatLng
import com.multissue.wit.feature.mypage.state.feed.FeedItemState
import com.multissue.wit.feature.mypage.state.map.feed.MapFeedItem

val feedDummyList = listOf(
    FeedItemState(
        id = "feed_1",
        thumbnailUrl = "https://picsum.photos/id/27/400/400",
        location = "삿포로",
    ),
    FeedItemState(
        id = "feed_2",
        thumbnailUrl = "https://picsum.photos/id/28/400/400",
        location = "오사카",
    ),
    FeedItemState(
        id = "feed_3",
        thumbnailUrl = "https://picsum.photos/id/29/400/400",
        location = "도쿄",
    ),
    FeedItemState(
        id = "feed_4",
        thumbnailUrl = "https://picsum.photos/id/30/400/400",
        location = "홍콩",
    ),
    FeedItemState(
        id = "feed_5",
        thumbnailUrl = "https://picsum.photos/id/31/400/400",
        location = "방콕",
    ),
    FeedItemState(
        id = "feed_6",
        thumbnailUrl = "https://picsum.photos/id/32/400/400",
        location = "교토",
    ),
    FeedItemState(
        id = "feed_7",
        thumbnailUrl = "https://picsum.photos/id/33/400/400",
        location = "후쿠오카",
    ),
    FeedItemState(
        id = "feed_8",
        thumbnailUrl = "https://picsum.photos/id/34/400/400",
        location = "나고야",
    ),
    FeedItemState(
        id = "feed_9",
        thumbnailUrl = "https://picsum.photos/id/35/400/400",
        location = "싱가포르",
    ),
    FeedItemState(
        id = "feed_10",
        thumbnailUrl = "https://picsum.photos/id/36/400/400",
        location = "타이베이",
    ),
    FeedItemState(
        id = "feed_11",
        thumbnailUrl = "https://picsum.photos/id/37/400/400",
        location = "다낭",
    ),
    FeedItemState(
        id = "feed_12",
        thumbnailUrl = "https://picsum.photos/id/38/400/400",
        location = "세부",
    ),
)

val mapFeedDummyList = listOf(
    MapFeedItem(
        id = "map_feed_1",
        thumbnailUrl = "https://picsum.photos/id/27/300/400",
        isLiked = false,
        likeCount = 132,
        date = "April 24, 2024",
        location = "시부야구, 도쿄",
        latLng = LatLng(35.6580, 139.7016),
    ),
    MapFeedItem(
        id = "map_feed_2",
        thumbnailUrl = "https://picsum.photos/id/28/300/400",
        isLiked = true,
        likeCount = 132,
        date = "April 24, 2024",
        location = "시부야구, 도쿄",
        latLng = LatLng(35.6595, 139.7004),
    ),
    MapFeedItem(
        id = "map_feed_3",
        thumbnailUrl = "https://picsum.photos/id/29/300/400",
        isLiked = false,
        likeCount = 56,
        date = "March 15, 2024",
        location = "신주쿠구, 도쿄",
        latLng = LatLng(35.6938, 139.7034),
    ),
    MapFeedItem(
        id = "map_feed_4",
        thumbnailUrl = "https://picsum.photos/id/30/300/400",
        isLiked = false,
        likeCount = 89,
        date = "March 20, 2024",
        location = "미나토구, 도쿄",
        latLng = LatLng(35.6581, 139.7514),
    ),
    MapFeedItem(
        id = "map_feed_5",
        thumbnailUrl = "https://picsum.photos/id/31/300/400",
        isLiked = false,
        likeCount = 44,
        date = "February 10, 2024",
        location = "신주쿠구, 도쿄",
        latLng = LatLng(35.6960, 139.7021),
    ),
    MapFeedItem(
        id = "map_feed_6",
        thumbnailUrl = "https://picsum.photos/id/32/300/400",
        isLiked = true,
        likeCount = 71,
        date = "January 5, 2024",
        location = "미나토구, 도쿄",
        latLng = LatLng(35.6562, 139.7489),
    ),
)

val travelDummyList = listOf(
    FeedItemState(
        id = "travel_1",
        thumbnailUrl = "https://picsum.photos/id/40/400/400",
        location = "파리",
    ),
    FeedItemState(
        id = "travel_2",
        thumbnailUrl = "https://picsum.photos/id/41/400/400",
        location = "런던",
    ),
    FeedItemState(
        id = "travel_3",
        thumbnailUrl = "https://picsum.photos/id/42/400/400",
        location = "바르셀로나",
    ),
    FeedItemState(
        id = "travel_4",
        thumbnailUrl = "https://picsum.photos/id/43/400/400",
        location = "로마",
    ),
)
