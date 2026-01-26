package com.multissue.wit.feature.mypage.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.mypage.MyPageScreen

fun EntryProviderScope<NavKey>.myPageEntry(navigator: Navigator) {
    // TODO SnackBar
    entry<MyPageNavKey> {
        MyPageScreen(

        )
    }
}