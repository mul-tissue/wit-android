package com.multissue.wit.feature.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.multissue.wit.designsystem.component.searchbar.WitSearchBar
import com.multissue.wit.designsystem.component.selectable.WitSelectType
import com.multissue.wit.designsystem.component.selectable.WitTextSwitch
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar
import com.multissue.wit.feature.map.component.MapBottomSheetScaffold
import com.multissue.wit.feature.map.component.MapTest
import com.multissue.wit.feature.map.component.feed.FeedBottomSheetContent
import com.multissue.wit.feature.map.dummy.placeDummyList
import com.multissue.wit.feature.map.state.FeedFilterType
import com.multissue.wit.feature.map.util.permission.LocationPermission

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel(),
    onFeedItemClicked: (feedId: Int) -> Unit,
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MapScreen(
    modifier: Modifier = Modifier,
    onFeedItemClicked: (feedId: Int) -> Unit,
) {
    var filter by remember { mutableStateOf(FeedFilterType.POPULAR) }

    LocationPermission {
        var selected by remember { mutableStateOf(WitSelectType.Feed) }
        var searchText by remember { mutableStateOf("") }

        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberStandardBottomSheetState(
                initialValue = SheetValue.PartiallyExpanded,
                confirmValueChange = { newValue ->
                    newValue != SheetValue.Hidden
                }
            )
        )

        MapBottomSheetScaffold(
            modifier = Modifier,
            scaffoldState = scaffoldState,
            sheetContent = {
                FeedBottomSheetContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    title = "${placeDummyList[0].cityName} 핫플 모아보기 🔥",
                    placeList = placeDummyList,
                    onFeedItemClicked = { onFeedItemClicked(it.id) },
                    filterType = filter,
                    onFilterClicked = { filter = it },
                )
            }
        ) { paddingValues ->
            Column(
                modifier = modifier,
            ) {
                WitCenterAlignedTopAppBar(
                    title = "",
                    navigationIcon = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_back),
                                contentDescription = "navigationIconContentDescription"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_notification),
                                contentDescription = ""
                            )
                        }
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_profile),
                                contentDescription = ""
                            )
                        }
                    }
                )

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    MapTest(
                        locationButtonPadding = paddingValues.calculateBottomPadding()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 6.dp)
                            .height(44.dp)
                    ) {
                        WitTextSwitch(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(118.dp),
                            selected = selected,
                            leftText = "피드",
                            rightText = "동행",
                            onLeftClick = {
                                selected = WitSelectType.Feed
                            },
                            onRightClick = {
                                selected = WitSelectType.Travel
                            }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        WitSearchBar(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            value = searchText,
                            placeholder = "어디로 떠날까요?",
                            onValueChange = { searchText = it },
                            onSearch = {}
                        )
                    }
                }
            }
        }
    }
}