package com.multissue.wit.feature.mypage.map.feed

import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.mypage.dummy.mapFeedDummyList
import com.multissue.wit.feature.mypage.state.map.feed.MapFeedUiIntent
import com.multissue.wit.feature.mypage.state.map.feed.MapFeedUiSideEffect
import com.multissue.wit.feature.mypage.state.map.feed.MapFeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapFeedViewModel @Inject constructor() :
    BaseViewModel<MapFeedUiState, MapFeedUiSideEffect, MapFeedUiIntent>(MapFeedUiState()) {

    override fun onIntent(intent: MapFeedUiIntent) {
        when (intent) {
            is MapFeedUiIntent.ClickFeedItem -> {
                val item = currentState.feedList.find { it.id == intent.id } ?: return
                postSideEffect(MapFeedUiSideEffect.NavigateToLocation(item.latLng))
            }
            is MapFeedUiIntent.ToggleLike -> {
                setState {
                    copy(
                        feedList = feedList.map {
                            if (it.id == intent.id) it.copy(
                                isLiked = !it.isLiked,
                                likeCount = if (it.isLiked) it.likeCount - 1 else it.likeCount + 1
                            ) else it
                        }
                    )
                }
            }
        }
    }

    init {
        // TODO: 선택된 마커 기반으로 피드 목록 가져오기
        setState {
            copy(
                selectedCityName = "삿포로",
                feedList = mapFeedDummyList,
            )
        }
    }
}
