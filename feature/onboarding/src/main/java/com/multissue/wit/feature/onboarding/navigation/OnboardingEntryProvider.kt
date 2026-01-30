package com.multissue.wit.feature.onboarding.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.onboarding.OnboardingScreen

fun EntryProviderScope<NavKey>.onboardingEntry(navigator: Navigator) {
    // TODO SnackBar
    entry<OnboardingNavKey> {
        OnboardingScreen(

        )
    }
}