package com.multissue.wit.ui.main

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.core.navigation.toEntries
import com.multissue.wit.designsystem.component.navigation.WitNavItem
import com.multissue.wit.designsystem.component.navigation.WitNavigationRail
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.chat.navigation.chatEntry
import com.multissue.wit.feature.feed.navigation.feedEntry
import com.multissue.wit.feature.home.navigation.homeEntry
import com.multissue.wit.feature.map.navigation.MapNavKey
import com.multissue.wit.feature.map.navigation.mapEntry
import com.multissue.wit.feature.mypage.navigation.myPageEntry
import com.multissue.wit.feature.travel.navigation.travelEntry
import com.multissue.wit.feature.upload.navigation.UploadNavKey
import com.multissue.wit.feature.upload.navigation.uploadEntry
import com.multissue.wit.navigation.MAIN_LEVEL_NAV_ITEMS
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map

@Composable
fun WitApp(
    appState: WitAppState,
) {
    // TODO THEMES
    WitApp(
        appState = appState,
        witAppViewModel = hiltViewModel()
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun WitApp(
    appState: WitAppState,
    witAppViewModel: WitAppViewModel,
) {
    val navigator = remember { Navigator(appState.navigationState) }
    var showNavRail by remember { mutableStateOf(true) }
    val centerButtonEvent = remember(witAppViewModel) {
        witAppViewModel.sideEffect
            .filterIsInstance<WitAppSideEffect.OpenMapSheet>()
            .map { } // Map 모듈과 의존성이 없어 Unit으로 이벤트만 받기
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(padding)
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
                    homeEntry(
                        navigator,
                        navigateToMap = { navigator.navigate(MapNavKey) }
                    )
                    chatEntry(navigator)
                    mapEntry(
                        navigator = navigator,
                        centerButtonEvent = centerButtonEvent,
                        onNavRailVisibilityChanged = { showNavRail = it },
                        onNavigateToUpload = { navigator.navigate(UploadNavKey) },
                    )
                    myPageEntry(navigator)
                    uploadEntry(navigator)
                    feedEntry(navigator)
                    travelEntry(navigator)
                }

                NavDisplay(
                    entries = appState.navigationState.toEntries(entryProvider),
                    sceneStrategy = listDetailStrategy,
                    onBack = { navigator.goBack() },
                )
            }

            if (appState.navigationState.currentKey != UploadNavKey && showNavRail) {
                WitNavigationRail(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp) //TODO
                        .background(color = Color.White), //TODO
//                        .padding(bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()),
                    onCenterButtonClicked = {
                        if (appState.navigationState.currentKey == MapNavKey) {
                            witAppViewModel.onIntent(WitAppUiIntent.CenterButtonClicked)
                        } else {
                            navigator.navigate(UploadNavKey)
                        }
                    },
                    navItems = {
                        MAIN_LEVEL_NAV_ITEMS.forEach { (navKey, navItem) ->
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
                                        painter = painterResource(navItem.unselectedIconId),
                                        contentDescription = null,
                                        tint = WitTheme.colors.primary
                                    )
                                },
                                selectedIcon = {
                                    Icon(
                                        modifier = Modifier.size(28.dp),
                                        painter = painterResource(navItem.selectedIconId),
                                        contentDescription = null,
                                        tint = WitTheme.colors.primary
                                    )
                                },
                            )
                        }
                    }
                )
            }
        }
    }
}