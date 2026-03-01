package com.multissue.wit.feature.mypage.state

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState
import com.multissue.wit.feature.mypage.state.feed.FeedItemState

data class MyPageUiState(
    val myPageNav: MyPageNav = MyPageNav.HOME,
    val userInfo: UserInfoState = UserInfoState(),
    val myPageType: MyPageType = MyPageType.FEED,
    val feedList: List<FeedItemState> = emptyList(),
    val travelList: List<FeedItemState> = emptyList(),
): UiState

interface MyPageUiSideEffect : UiSideEffect

sealed interface MyPageUiIntent: UiIntent {
    data object TabFeedPage: MyPageUiIntent
    data object TabTravelPage: MyPageUiIntent
    data class ClickFeedItem(val feedItemState: FeedItemState): MyPageUiIntent
    data object ClickSettingsButton: MyPageUiIntent
    data object ClickBackButton: MyPageUiIntent
}
