package com.multissue.wit.feature.mypage.component.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.mypage.MapFeedViewModel
import com.multissue.wit.feature.mypage.component.map.feed.MapFeedBottomSheetContent
import com.multissue.wit.feature.mypage.component.map.feed.MapFeedMarker
import com.multissue.wit.feature.mypage.state.MyPageType
import com.multissue.wit.feature.mypage.state.map.feed.MapFeedUiIntent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageMapContent(
    modifier: Modifier = Modifier,
    myPageType: MyPageType,
    cityName: String = "",
) {
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            confirmValueChange = { it != SheetValue.Hidden },
        )
    )

    // TODO TEST
    val cityLatLngMap = mapOf(
        "삿포로" to LatLng(43.0642, 141.3469),
        "도쿄" to LatLng(35.6762, 139.6503),
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            cityLatLngMap[cityName] ?: LatLng(0.0, 0.0),
            12f,
        )
    }

    val mapFeedViewModel: MapFeedViewModel = hiltViewModel()
    val mapFeedUiState by mapFeedViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(cityName) {
        if (myPageType == MyPageType.FEED) {
            mapFeedViewModel.onIntent(MapFeedUiIntent.LoadFeed(cityName))
            cityLatLngMap[cityName]?.let {
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 12f))
            }
        }
    }
    // TODO mapTravel 추가 필요

    val sheetMaxHeight = LocalConfiguration.current.screenHeightDp.dp * 0.5f

    fun navigateToLocation(latLng: LatLng) {
        scope.launch { scaffoldState.bottomSheetState.partialExpand() }
        scope.launch { cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(latLng, 15f)) }
    }

    BottomSheetScaffold(
        modifier = modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 120.dp,
        sheetContainerColor = WitTheme.colors.white100,
        sheetShadowElevation = 8.dp,
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle(
                height = 3.dp,
                color = WitTheme.colors.gray200,
            )
        },
        sheetContent = {
            Box(modifier = Modifier.heightIn(max = sheetMaxHeight)) {
                when (myPageType) {
                    MyPageType.FEED -> MapFeedBottomSheetContent(
                        onNavigateToLocation = ::navigateToLocation,
                        viewModel = mapFeedViewModel,
                    )
                    MyPageType.TRAVEL -> { /* TODO: 협업자가 구현 예정 */ }
                }
            }
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = false,
                    zoomControlsEnabled = false,
                ),
            ) {
                if (myPageType == MyPageType.FEED) {
                    mapFeedUiState.feedList.forEach { item ->
                        MapFeedMarker(
                            item = item,
                            onClick = { mapFeedViewModel.onIntent(MapFeedUiIntent.ClickFeedItem(item.id)) },
                        )
                    }
                }
            }
        }
    }
}
