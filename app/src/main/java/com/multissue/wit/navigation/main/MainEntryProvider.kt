package com.multissue.wit.navigation.main

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.onboarding.OnboardingScreen
import com.multissue.wit.ui.main.WitApp
import com.multissue.wit.ui.main.rememberWitAppState

fun EntryProviderScope<NavKey>.mainEntry(navigator: Navigator) {
    // TODO SnackBar
    entry<MainNavKey> {
        val appState = rememberWitAppState()
        WitApp(appState)
    }
}