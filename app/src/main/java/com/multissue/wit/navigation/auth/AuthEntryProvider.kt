package com.multissue.wit.navigation.auth

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.onboarding.OnboardingScreen

fun EntryProviderScope<NavKey>.authEntry(navigator: Navigator) {
    // TODO SnackBar
    entry<AuthNavKey> {
        OnboardingScreen(

        )
    }
}