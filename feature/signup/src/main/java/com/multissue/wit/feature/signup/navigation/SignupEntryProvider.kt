package com.multissue.wit.feature.signup.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.signup.SignupScreen

fun EntryProviderScope<NavKey>.signupEntry(navigator: Navigator) {
    // TODO SnackBar
    entry<SignupNavKey> {
        SignupScreen(

        )
    }
}