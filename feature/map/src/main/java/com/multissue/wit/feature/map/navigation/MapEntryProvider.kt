package com.multissue.wit.feature.map.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.map.MapScreen

fun EntryProviderScope<NavKey>.mapEntry(navigator: Navigator) {
    // TODO SnackBar
    entry<MapNavKey> {
        MapScreen(

        )
    }
}