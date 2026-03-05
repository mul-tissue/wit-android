package com.multissue.wit.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.mypage.component.feed.FeedGridItem
import com.multissue.wit.feature.mypage.component.travel.TravelGridItem
import com.multissue.wit.feature.mypage.state.feed.FeedItemState
import com.multissue.wit.feature.mypage.state.MyPageType
import com.multissue.wit.feature.mypage.state.MyPageUiIntent
import com.multissue.wit.feature.mypage.state.UserInfoState

@Composable
fun MyPageHomeContent(
    modifier: Modifier = Modifier,
    myPageType: MyPageType,
    userInfoState: UserInfoState,
    feedList: List<FeedItemState>,
    travelList: List<FeedItemState>,
    onIntent: (MyPageUiIntent) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .background(WitTheme.background.color),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            ProfileSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, bottom = 6.dp),
                userInfoState = userInfoState,
            )
        }
        stickyHeader {
            MyPageTabRow(
                selectedTab = myPageType,
                onTabSelected = { tab ->
                    when (tab) {
                        MyPageType.FEED -> onIntent(MyPageUiIntent.TabFeedPage)
                        MyPageType.TRAVEL -> onIntent(MyPageUiIntent.TabTravelPage)
                    }
                }
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier.height(0.dp))
        }

        when (myPageType) {
            MyPageType.FEED -> {
                items(
                    items = feedList,
                    key = { it.id }
                ) { feedItem ->
                    FeedGridItem(
                        modifier = Modifier.padding(
                            start = if (feedList.indexOf(feedItem) % 2 == 0) 20.dp else 0.dp,
                            end = if (feedList.indexOf(feedItem) % 2 != 0) 20.dp else 0.dp,
                        ),
                        feedItemState = feedItem,
                        onClick = { onIntent(MyPageUiIntent.ClickFeedItem(feedItem)) }
                    )
                }
            }
            MyPageType.TRAVEL -> {
                items(
                    items = travelList,
                    key = { it.id }
                ) { travelItem ->
                    TravelGridItem(
                        modifier = Modifier.padding(
                            start = if (travelList.indexOf(travelItem) % 2 == 0) 20.dp else 0.dp,
                            end = if (travelList.indexOf(travelItem) % 2 != 0) 20.dp else 0.dp,
                        ),
                        travelItemState = travelItem,
                        onClick = { onIntent(MyPageUiIntent.ClickTravelItem(travelItem)) }
                    )
                }
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
