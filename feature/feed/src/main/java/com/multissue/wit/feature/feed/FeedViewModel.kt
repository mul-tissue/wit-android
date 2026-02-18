package com.multissue.wit.feature.feed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.feed.navigation.FeedNavKey
import com.multissue.wit.feature.feed.state.FeedUiIntent
import com.multissue.wit.feature.feed.state.FeedUiSideEffect
import com.multissue.wit.feature.feed.state.FeedUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel(assistedFactory = FeedViewModel.Factory::class)
class FeedViewModel @AssistedInject constructor(
    private val savedStateHandle: SavedStateHandle,
    @Assisted val key: FeedNavKey
) : BaseViewModel<FeedUiState, FeedUiSideEffect, FeedUiIntent>(FeedUiState()) {
    private val selectedFeedIdKey = "selectedFeedIdKey"

    private val selectedFeedId = savedStateHandle.getStateFlow(
        key = selectedFeedIdKey,
        initialValue = key.feedId
    )

    override fun onIntent(intent: FeedUiIntent) {
        when(intent) {
            FeedUiIntent.Empty -> {  }
            FeedUiIntent.Loading -> {  }
            is FeedUiIntent.FeedDetail -> {
                setState { intent.feedUiState }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(key: FeedNavKey): FeedViewModel
    }
}
