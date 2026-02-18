package com.multissue.wit.feature.home.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.home.HomeScreen

fun EntryProviderScope<NavKey>.homeEntry(
    navigator: Navigator,
    navigateToMap: () -> Unit,
) {
    // TODO SnackBar
    entry<HomeNavKey> {
        HomeScreen(
            navigateToMap = navigateToMap
        )
    }
}