package com.multissue.wit.feature.mypage.component.map.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.LatLng
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.mypage.map.feed.MapFeedViewModel
import com.multissue.wit.feature.mypage.state.map.feed.MapFeedItem
import com.multissue.wit.feature.mypage.state.map.feed.MapFeedUiIntent
import com.multissue.wit.feature.mypage.state.map.feed.MapFeedUiSideEffect

@Composable
fun MapFeedBottomSheetContent(
    modifier: Modifier = Modifier,
    onNavigateToLocation: (LatLng) -> Unit = {},
    viewModel: MapFeedViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is MapFeedUiSideEffect.NavigateToLocation -> onNavigateToLocation(effect.latLng)
            }
        }
    }

    MapFeedBottomSheetContent(
        modifier = modifier,
        cityName = uiState.selectedCityName,
        feedList = uiState.feedList,
        onIntent = viewModel::onIntent,
    )
}

@Composable
internal fun MapFeedBottomSheetContent(
    modifier: Modifier = Modifier,
    cityName: String,
    feedList: List<MapFeedItem>,
    onIntent: (MapFeedUiIntent) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item(span = { GridItemSpan(2) }) {
            Text(
                text = cityName,
                style = WitTheme.typography.titleXL,
                color = WitTheme.colors.text,
            )
        }
        items(feedList, key = { it.id }) { item ->
            MapFeedItem(
                item = item,
                onLikeClicked = { onIntent(MapFeedUiIntent.ToggleLike(item.id)) },
                onClick = { onIntent(MapFeedUiIntent.ClickFeedItem(item.id)) },
            )
        }
    }
}
