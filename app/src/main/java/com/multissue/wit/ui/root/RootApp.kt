package com.multissue.wit.ui.root

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.multissue.wit.core.navigation.toEntries
import com.multissue.wit.navigation.auth.authEntry
import com.multissue.wit.navigation.main.mainEntry

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RootApp(
    appState: RootAppState
) {
    val navigator = appState.navigator
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    val entryProvider = entryProvider {
        authEntry(navigator)
        mainEntry(navigator)
    }

    NavDisplay(
        entries = appState.navigationState.toEntries(entryProvider),
        sceneStrategy = listDetailStrategy,
        onBack = { navigator.goBack() },
    )
}