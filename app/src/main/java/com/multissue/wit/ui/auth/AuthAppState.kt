package com.multissue.wit.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.multissue.wit.core.navigation.NavigationState
import com.multissue.wit.core.navigation.rememberNavigationState
import com.multissue.wit.feature.login.navigation.LoginNavKey
import com.multissue.wit.navigation.Auth_LEVEL_NAV_ITEMS
import com.multissue.wit.ui.main.NavigationTrackingSideEffect
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAuthAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AuthAppState {
    val navigationState = rememberNavigationState(LoginNavKey, Auth_LEVEL_NAV_ITEMS.keys)

    NavigationTrackingSideEffect(navigationState)

    return remember(
        navigationState,
        coroutineScope,
    ) {
        AuthAppState(
            navigationState = navigationState,
            coroutineScope = coroutineScope,
        )
    }
}

@Stable
class AuthAppState(
    val navigationState: NavigationState,
    coroutineScope: CoroutineScope,
) {
    // TODO 구현할 거 있으면
}