package com.multissue.wit.feature.mypage.component.map.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.multissue.wit.feature.mypage.MapTravelViewModel
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelItem
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiIntent
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiSideEffect

@Composable
fun MapTravelBottomSheetContent(
    modifier: Modifier = Modifier,
    onNavigateToLocation: (LatLng) -> Unit = {},
    viewModel: MapTravelViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is MapTravelUiSideEffect.NavigateToLocation -> onNavigateToLocation(effect.latLng)
            }
        }
    }

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
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                text = cityName,
                style = WitTheme.typography.titleXL,
                color = WitTheme.colors.text,
            )
        }
        items(travelList, key = { it.id }) { item ->
            // TODO("동행 화면 구현")
        }
    }
}
