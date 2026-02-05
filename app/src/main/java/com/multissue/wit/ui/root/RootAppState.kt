package com.multissue.wit.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.multissue.wit.core.navigation.NavigationState
import com.multissue.wit.core.navigation.rememberNavigationState
import com.multissue.wit.navigation.ROOT_LEVEL_NAV_ITEMS
import com.multissue.wit.navigation.auth.AuthNavKey
import com.multissue.wit.ui.main.NavigationTrackingSideEffect
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberRootAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): RootAppState {
    val navigationState = rememberNavigationState(AuthNavKey, ROOT_LEVEL_NAV_ITEMS.keys)

    NavigationTrackingSideEffect(navigationState)

    return remember(
        navigationState,
        coroutineScope,
    ) {
        RootAppState(
            navigationState = navigationState,
            coroutineScope = coroutineScope,
        )
    }
}

@Stable
class RootAppState(
    val navigationState: NavigationState,
    coroutineScope: CoroutineScope,
) {
    // TODO 구현할 거 있으면
}