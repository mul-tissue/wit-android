package com.multissue.wit.feature.feed.state
data class ReactionState(
    val selectedReaction: ReactionType? = null,
    val heartReactionCount: Int = 0,
    val greatReactionCount: Int = 0,
    val awesomeReactionCount: Int = 0,
    val likeReactionCount: Int = 0,
    val fireReactionCount: Int = 0,
)