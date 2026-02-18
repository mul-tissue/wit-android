package com.multissue.wit.feature.feed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.feed.navigation.FeedNavKey
import com.multissue.wit.feature.feed.state.FeedState
import com.multissue.wit.feature.feed.state.FeedUiIntent
import com.multissue.wit.feature.feed.state.FeedUiSideEffect
import com.multissue.wit.feature.feed.state.FeedUiState
import com.multissue.wit.feature.feed.state.ReactionType
import com.multissue.wit.feature.feed.state.UserState
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

    fun onReactionItemClick(
        type: ReactionType
    ) {
        setState {
            copy(reactionState = reactionState.copy(selectedReaction = type))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(key: FeedNavKey): FeedViewModel
    }

    fun testInitial() {
        setState {
            copy(
                user = UserState(
                    username = "parkparki",
                    userThumbnailUrl = "https://picsum.photos/id/60/400/400",
                ),
                feedState = FeedState(
                    imageUrl = "https://picsum.photos/id/27/300/400",
                    title = "언니랑 아이스크림 들고 파리 산책 🍦",
                    location = "Effeltower, Paris",
                    date = "April 24, 2024 4:52 PM",
                )
            )
        }
    }

    init {
        testInitial()
    }
}
