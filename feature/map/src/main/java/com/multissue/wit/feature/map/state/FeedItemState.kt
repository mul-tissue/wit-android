package com.multissue.wit.feature.map.state

data class FeedItemState(
    val thumbnailUrl: String = "",
    val username: String = "",
    val userThumbnail: String = "",
    val isLiked: Boolean = false,
    val likeCount: Int = 0,
)