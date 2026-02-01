package com.multissue.wit.feature.upload.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.upload.UploadScreen

fun EntryProviderScope<NavKey>.uploadEntry(navigator: Navigator) {
    // TODO SnackBar
    entry<UploadNavKey> {
        UploadScreen(

        )
    }
}