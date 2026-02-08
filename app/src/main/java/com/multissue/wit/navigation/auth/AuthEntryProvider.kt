package com.multissue.wit.navigation.auth

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.onboarding.OnboardingScreen
import com.multissue.wit.ui.auth.AuthApp
import com.multissue.wit.ui.auth.rememberAuthAppState
import com.multissue.wit.ui.main.WitApp
import com.multissue.wit.ui.main.rememberWitAppState

fun EntryProviderScope<NavKey>.authEntry(navigator: Navigator) {
    // TODO SnackBar
    entry<AuthNavKey> {
        val authState = rememberAuthAppState()
        AuthApp(
            authState =  authState
        )
    }
}