package com.multissue.wit.navigation.main

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.ui.main.WitApp
import com.multissue.wit.ui.main.rememberWitAppState

fun EntryProviderScope<NavKey>.mainEntry(
    onNavigateToAuth: () -> Unit,
) {
    // TODO SnackBar
    entry<MainNavKey> {
        val appState = rememberWitAppState()
        WitApp(
            appState = appState,
            onNavigateToAuth = onNavigateToAuth,
        )
    }
}