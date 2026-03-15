package com.multissue.wit.feature.mypage.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.feature.mypage.MyPageScreen

fun EntryProviderScope<NavKey>.myPageEntry(
    onBottomNavVisibilityChanged: (Boolean) -> Unit = {},
    onNavigateToAuth: () -> Unit = {},
) {
    // TODO SnackBar
    entry<MyPageNavKey> {
        MyPageScreen(
            onBottomNavVisibilityChanged = onBottomNavVisibilityChanged,
            onNavigateToAuth = onNavigateToAuth,
        )
    }
}