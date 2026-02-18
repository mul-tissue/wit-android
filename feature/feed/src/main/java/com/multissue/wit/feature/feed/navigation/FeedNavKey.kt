package com.multissue.wit.feature.feed.navigation

import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class FeedNavKey(
    val feedId: Int? = null
) : NavKey

fun Navigator.navigateToFeed(
    feedId: Int,
) {
    navigate(FeedNavKey(feedId))
}
