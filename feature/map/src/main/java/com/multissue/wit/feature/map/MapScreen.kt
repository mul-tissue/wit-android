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
import com.multissue.wit.feature.map.component.FilterSearchRow
import com.multissue.wit.feature.map.component.MapBottomSheetScaffold
import com.multissue.wit.feature.map.component.MapTest
import com.multissue.wit.feature.map.component.MapTopAppBar
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
    // TODO UI STATE
    var filter by remember { mutableStateOf(FeedFilterType.POPULAR) }

    LocationPermission {
        // TODO UI STATE
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
                    title = placeDummyList[0].cityName,
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
                MapTopAppBar(
                    onBackButtonClicked = {},
                    onNotificationButtonClicked = {},
                    onProfileButtonClicked = {},
                )

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    MapTest(
                        locationButtonPadding = paddingValues.calculateBottomPadding()
                    )

                    FilterSearchRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 6.dp)
                            .height(44.dp),
                        type = selected,
                        onToggle = { selected = it },
                        searchText = searchText,
                        onSearchTextChanged = { searchText = it },
                        onSearch = { }, // TODO
                    )
                }
            }
        }
    }
}