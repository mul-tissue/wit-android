package com.multissue.wit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.multissue.wit.core.designsystem.component.navigation.WitNavItem
import com.multissue.wit.core.designsystem.component.navigation.WitNavigationRail
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.core.navigation.toEntries
import com.multissue.wit.feature.chat.navigation.chatEntry
import com.multissue.wit.feature.home.navigation.homeEntry
import com.multissue.wit.feature.login.navigation.LoginNavKey
import com.multissue.wit.navigation.TOP_LEVEL_NAV_ITEMS

@Composable
fun WitApp(
    appState: WitAppState,
    modifier: Modifier = Modifier,
) {
    // TODO THEMES
    WitApp(
        appState = appState
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun WitApp(
    appState: WitAppState,
) {
    val navigator = remember { Navigator(appState.navigationState) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .navigationBarsPadding(),
        ) {
            // TODO TopBar
            Box( // Display Main
                modifier = Modifier
                    .weight(1f)
                    .consumeWindowInsets(
                        WindowInsets(0, 0, 0, 0)
                    )
            ) {
                val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

                val entryProvider = entryProvider {
                    homeEntry(navigator)
                    chatEntry(navigator)
                }

                NavDisplay(
                    entries = appState.navigationState.toEntries(entryProvider),
                    sceneStrategy = listDetailStrategy,
                    onBack = { navigator.goBack() },
                )
            }
            WitNavigationRail(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp) //TODO
                    .background(color = Color.White), //TODO
                onCenterButtonClicked = { navigator.navigate(LoginNavKey) },
                navItems = {
                    TOP_LEVEL_NAV_ITEMS.forEach { (navKey, navItem) ->
//                        val hasUnread = unreadNavKeys.contains(navKey) //TODO
                        val selected = navKey == appState.navigationState.currentTopLevelKey
                        WitNavItem(
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(2f),
//                                .then(if (hasUnread) Modifier.notificationDot() else Modifier), //TODO
                            selected = selected,
                            onClick = { navigator.navigate(navKey) },
                            icon = {
                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    imageVector = navItem.unselectedIcon,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            },
                            selectedIcon = {
                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    imageVector = navItem.selectedIcon,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            },
                        )
                    }
                }
            )
        }
    }
}