package com.multissue.wit.feature.signup.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.feature.signup.SignupRoute

fun EntryProviderScope<NavKey>.signupEntry(
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
) {
    entry<SignupNavKey> {
        SignupRoute(
            navigateToLogin = navigateToLogin,
            navigateToHome = navigateToMain
        )
    }
}