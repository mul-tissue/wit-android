package com.multissue.wit.feature.map.component.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.component.SpH
import com.multissue.wit.feature.map.dummy.feedDummyList
import com.multissue.wit.feature.map.dummy.placeDummyList
import com.multissue.wit.feature.map.state.FeedFilterType
import com.multissue.wit.feature.map.state.FeedItemState
import com.multissue.wit.feature.map.state.PlaceItemState

@Composable
fun FeedBottomSheetContent(
    modifier: Modifier = Modifier,
    title: String,
    placeList: List<PlaceItemState>,
    onFeedItemClicked: (FeedItemState) -> Unit,
    filterType: FeedFilterType,
    onFilterClicked: (FeedFilterType) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        FeedFilterChipRow(
            modifier = Modifier
                .height(44.dp),
            filterType = filterType,
            onFilterClicked = onFilterClicked,
        )
        SpH(16.dp)
        Text(
            text = when (filterType) {
                FeedFilterType.POPULAR -> "$title 핫플 모아보기 🔥"
                FeedFilterType.LIVE -> "실시간 $title 피드 📸"
            },
            style = WitTheme.typography.titleL
        )
        SpH(16.dp)
        LazyColumn(
            modifier = Modifier.height(440.dp),
        ) {
            items(
                count = placeList.size
            ) {
                SpH(10.dp)
                FeedListRow(
                    modifier = Modifier.height(240.dp),
                    filterType = filterType,
                    title = "${placeList[it].districtName}, ${placeList[it].cityName}",
                    titleThumbnail = placeList[it].titleThumbnailUrl,
                    feedList = feedDummyList,
                    onFeedItemClicked = onFeedItemClicked,
                    onViewMoreBoxClicked = {  }
                )
                SpH(10.dp)
            }
        }
        SpH(16.dp)
    }
}