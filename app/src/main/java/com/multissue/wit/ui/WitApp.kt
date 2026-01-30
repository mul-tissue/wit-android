package com.multissue.wit.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.core.navigation.toEntries
import com.multissue.wit.feature.chat.navigation.chatEntry
import com.multissue.wit.feature.home.navigation.homeEntry

@Composable
fun WitApp(
    appState: WitAppState,
    modifier: Modifier = Modifier,
) {

}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun WitApp(
    appState: WitAppState,
) {
    val navigator = remember { Navigator(appState.navigationState) }

    WitNavigationSuiteScaffold(appState) {
        // TODO TOP BAR
        Box(
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
    }
}