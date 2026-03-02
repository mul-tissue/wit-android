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
import com.multissue.wit.feature.mypage.map.feed.MapFeedViewModel
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
) {
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            confirmValueChange = { it != SheetValue.Hidden },
        )
    )

    val cameraPositionState = rememberCameraPositionState {
        // TODO: 선택된 마커 위치로 이동
        position = CameraPosition.fromLatLngZoom(LatLng(43.0642, 141.3469), 11f) // 삿포로
    }

    val mapFeedViewModel: MapFeedViewModel = hiltViewModel()
    val mapFeedUiState by mapFeedViewModel.uiState.collectAsStateWithLifecycle()

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
