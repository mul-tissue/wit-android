package com.multissue.wit.feature.travel.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.feature.travel.TravelDetailScreen
import com.multissue.wit.feature.travel.TravelDetailViewModel

fun EntryProviderScope<NavKey>.travelEntry(navigator: Navigator) {
    entry<TravelNavKey> { key ->
        val viewModel = hiltViewModel<TravelDetailViewModel, TravelDetailViewModel.Factory> {
            it.create(key)
        }
        TravelDetailScreen(
            viewModel = viewModel,
            onBackClick = { navigator.goBack() },
            onChatRoomNavigate = { /* TODO: 채팅방 네비게이션 */ },
        )
    }
}
