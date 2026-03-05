package com.multissue.wit.feature.mypage

import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.mypage.dummy.feedDummyList
import com.multissue.wit.feature.mypage.dummy.travelDummyList
import com.multissue.wit.feature.mypage.state.MyPageNav
import com.multissue.wit.feature.mypage.state.MyPageType
import com.multissue.wit.feature.mypage.state.MyPageUiIntent
import com.multissue.wit.feature.mypage.state.MyPageUiSideEffect
import com.multissue.wit.feature.mypage.state.MyPageUiState
import com.multissue.wit.feature.mypage.state.UserInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

) : BaseViewModel<MyPageUiState, MyPageUiSideEffect, MyPageUiIntent>(MyPageUiState()) {
    override fun onIntent(intent: MyPageUiIntent) {
        when (intent) {
            MyPageUiIntent.TabFeedPage -> {
                setState { copy(myPageType = MyPageType.FEED) }
            }
            MyPageUiIntent.TabTravelPage -> {
                setState { copy(myPageType = MyPageType.TRAVEL) }
            }
            is MyPageUiIntent.ClickFeedItem -> {
                setState {
                    copy(
                        myPageNav = MyPageNav.MAP,
                        selectedCity = intent.feedItemState.location,
                    )
                }
            }
            is MyPageUiIntent.ClickTravelItem -> {
                setState {
                    copy(
                        myPageNav = MyPageNav.MAP,
                        selectedCity = intent.travelItemState.location,
                    )
                }
            }
            MyPageUiIntent.ClickSettingsButton -> {
                setState { copy(myPageNav = MyPageNav.SETTINGS) }
            }
            MyPageUiIntent.ClickBackButton -> {
                setState {
                    copy(
                        myPageNav = when (myPageNav) {
                            MyPageNav.PROFILE_EDIT -> MyPageNav.SETTINGS
                            else -> MyPageNav.HOME
                        }
                    )
                }
            }
            MyPageUiIntent.NavigateToProfileEdit -> {
                setState { copy(myPageNav = MyPageNav.PROFILE_EDIT) }
            }
        }
    }

    fun testInitial() {
        setState {
            copy(
                userInfo = UserInfoState(
                    profileImageUrl = "https://picsum.photos/id/60/400/400",
                    name = "parkparki",
                    age = "20대",
                    gender = "여자",
                ),
                feedList = feedDummyList,
                travelList = travelDummyList,
            )
        }
    }

    init {
        testInitial()
    }
}
