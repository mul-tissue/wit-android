/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.multissue.wit.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.multissue.wit.core.navigation.NavigationState
import com.multissue.wit.core.navigation.rememberNavigationState
import com.multissue.wit.core.ui.TrackDisposableJank
import com.multissue.wit.feature.home.navigation.HomeNavKey
import com.multissue.wit.feature.onboarding.navigation.OnboardingNavKey
import com.multissue.wit.navigation.MAIN_LEVEL_NAV_ITEMS
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberWitAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): WitAppState {
    val navigationState = rememberNavigationState(HomeNavKey, MAIN_LEVEL_NAV_ITEMS.keys)

    NavigationTrackingSideEffect(navigationState)

    return remember(
        navigationState,
        coroutineScope,
    ) {
        WitAppState(
            navigationState = navigationState,
            coroutineScope = coroutineScope,
        )
    }
}

@Stable
class WitAppState(
    val navigationState: NavigationState,
    coroutineScope: CoroutineScope,
) {
    /* TODO 구현
    /**
     * The top level nav keys that have unread news resources.
     */
    val topLevelNavKeysWithUnreadResources: StateFlow<Set<NavKey>> =
        userNewsResourceRepository.observeAllForFollowedTopics()
            .combine(userNewsResourceRepository.observeAllBookmarked()) { forYouNewsResources, bookmarkedNewsResources ->
                setOfNotNull(
                    ForYouNavKey.takeIf { forYouNewsResources.any { !it.hasBeenViewed } },
                    BookmarksNavKey.takeIf { bookmarkedNewsResources.any { !it.hasBeenViewed } },
                )
            }
            .stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(5_000),
                initialValue = emptySet(),
            )
     */
}

/**
 * Stores information about navigation events to be used with JankStats
 */
@Composable
fun NavigationTrackingSideEffect(navigationState: NavigationState) {
    TrackDisposableJank(navigationState.currentKey) { metricsHolder ->
        metricsHolder.state?.putState("Navigation", navigationState.currentKey.toString())
        onDispose {}
    }
}
