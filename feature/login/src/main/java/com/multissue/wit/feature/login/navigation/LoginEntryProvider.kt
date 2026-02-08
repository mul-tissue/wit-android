package com.multissue.wit.feature.login.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.feature.login.LoginScreen

fun EntryProviderScope<NavKey>.loginEntry(
    navigateToSignUp: () -> Unit
) {
    entry<LoginNavKey> {
        LoginScreen(
            navigateToSignUp = navigateToSignUp
        )
    }
}