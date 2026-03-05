package com.multissue.wit.feature.mypage.component.map.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multissue.wit.core.ui.travel.component.TravelListItem
import com.multissue.wit.feature.mypage.MapTravelViewModel
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelItem
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiIntent
import com.multissue.wit.feature.mypage.state.map.travel.toTravelItemState

@Composable
fun MapTravelBottomSheetContent(
    modifier: Modifier = Modifier,
    viewModel: MapTravelViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MapTravelBottomSheetContent(
        modifier = modifier,
        cityName = uiState.selectedCityName,
        travelList = uiState.travelList,
        onIntent = viewModel::onIntent,
    )
}

@Composable
internal fun MapTravelBottomSheetContent(
    modifier: Modifier = Modifier,
    cityName: String,
    travelList: List<MapTravelItem>,
    onIntent: (MapTravelUiIntent) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(travelList, key = { it.id }) { item ->
            TravelListItem(
                travelItem = item.toTravelItemState(),
                onItemClicked = { onIntent(MapTravelUiIntent.ShowTravelPostCard(item.id)) },
            )
        }
    }
}
