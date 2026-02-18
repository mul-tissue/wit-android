package com.multissue.wit.feature.feed.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.feed.FeedScreen
import com.multissue.wit.feature.feed.FeedViewModel

fun EntryProviderScope<NavKey>.feedEntry(navigator: Navigator) {
    // TODO SnackBar
    entry<FeedNavKey>(

    ) { key ->
        val viewModel = hiltViewModel<FeedViewModel, FeedViewModel.Factory> {
            it.create(key)
        }

        FeedScreen(
            viewModel = viewModel
        )
    }
}