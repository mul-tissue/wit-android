package com.multissue.wit.feature.map.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.chat.navigation.navigateToChatRoom
import com.multissue.wit.feature.feed.navigation.navigateToFeed
import com.multissue.wit.feature.map.MapScreen
import com.multissue.wit.feature.travel.navigation.navigateToTravel
import kotlinx.coroutines.flow.Flow

fun EntryProviderScope<NavKey>.mapEntry(
    navigator: Navigator,
    navigateToMyPage: () -> Unit,
    centerButtonEvent: Flow<Unit>,
    onNavRailVisibilityChanged: (Boolean) -> Unit,
    onNavigateToUpload: () -> Unit = {},
) {
    // TODO SnackBar
    entry<MapNavKey> {
        MapScreen(
            navigateToMyPage = navigateToMyPage,
            onFeedItemClicked = navigator::navigateToFeed,
            onTravelItemClicked = navigator::navigateToTravel,
            onNavigateToUpload = onNavigateToUpload,
            centerButtonEvent = centerButtonEvent,
            onChatRoomNavigate = navigator::navigateToChatRoom,
            onNavRailVisibilityChanged = onNavRailVisibilityChanged,
        )
    }
}