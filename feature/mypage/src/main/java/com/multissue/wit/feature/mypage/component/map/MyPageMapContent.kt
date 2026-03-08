package com.multissue.wit.feature.mypage.component.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.multissue.wit.core.ui.R
import com.multissue.wit.core.ui.travel.component.UploadDateSelectionBottomSheet
import com.multissue.wit.core.ui.travel.component.SelectTimeDialog
import com.multissue.wit.core.ui.travel.component.UploadTravelBottomSheet
import com.multissue.wit.core.ui.travel.component.AddLocationBottomSheet
import com.multissue.wit.designsystem.component.snackbar.WitSnackBarHost
import com.multissue.wit.designsystem.component.snackbar.WitSnackBarVisuals
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.mypage.MapFeedViewModel
import com.multissue.wit.feature.mypage.MapTravelViewModel
import com.multissue.wit.feature.mypage.component.map.feed.MapFeedBottomSheetContent
import com.multissue.wit.feature.mypage.component.map.feed.MapFeedMarker
import com.multissue.wit.feature.mypage.component.map.travel.MapTravelBottomSheetContent
import com.multissue.wit.feature.mypage.component.map.travel.MapTravelMarker
import com.multissue.wit.feature.mypage.state.MyPageType
import com.multissue.wit.feature.mypage.state.map.feed.MapFeedUiIntent
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiIntent
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiSideEffect
import kotlinx.coroutines.launch
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageMapContent(
    modifier: Modifier = Modifier,
    myPageType: MyPageType,
    cityName: String = "",
    mapTravelViewModel: MapTravelViewModel = hiltViewModel(),
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
        "파리" to LatLng(48.8584, 2.2945),
        "런던" to LatLng(51.5007, -0.1246),
        "바르셀로나" to LatLng(41.4036, 2.1744),
        "로마" to LatLng(41.8902, 12.4922),
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            cityLatLngMap[cityName] ?: LatLng(0.0, 0.0),
            12f,
        )
    }

    val mapFeedViewModel: MapFeedViewModel = hiltViewModel()
    val mapFeedUiState by mapFeedViewModel.uiState.collectAsStateWithLifecycle()

    val mapTravelUiState by mapTravelViewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val deletedSnackbarMessage = stringResource(R.string.travel_deleted_snackbar)

    LaunchedEffect(cityName) {
        cityLatLngMap[cityName]?.let {
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 12f))
        }
        if (myPageType == MyPageType.FEED) {
            mapFeedViewModel.onIntent(MapFeedUiIntent.LoadFeed(cityName))
        } else if (myPageType == MyPageType.TRAVEL) {
            mapTravelViewModel.onIntent(MapTravelUiIntent.LoadTravel(cityName))
        }
    }

    LaunchedEffect(Unit) {
        mapTravelViewModel.sideEffect.collect { effect ->
            when (effect) {
                is MapTravelUiSideEffect.NavigateToLocation -> {
                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                    scope.launch { cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(effect.latLng, 15f)) }
                }
                is MapTravelUiSideEffect.ShowDeletedSnackbar -> {
                    snackbarHostState.showSnackbar(
                        WitSnackBarVisuals(
                            message = deletedSnackbarMessage,
                            leadingIconRes = com.multissue.wit.feature.mypage.R.drawable.icon_round_check_blue,
                        )
                    )
                }
            }
        }
    }

    fun navigateToLocation(latLng: LatLng) {
        scope.launch { scaffoldState.bottomSheetState.partialExpand() }
        scope.launch { cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(latLng, 15f)) }
    }

    Box(modifier = modifier.fillMaxSize()) {
        BottomSheetScaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            sheetPeekHeight = 120.dp,
            sheetContainerColor = WitTheme.colors.white100,
            sheetShadowElevation = 8.dp,
            sheetDragHandle = {
                BottomSheetDefaults.DragHandle(
                    modifier = Modifier.clickable(enabled = false) {},
                    height = 3.dp,
                    color = WitTheme.colors.gray200,
                )
            },
            sheetContent = {
                when (myPageType) {
                    MyPageType.FEED -> MapFeedBottomSheetContent(
                        onNavigateToLocation = ::navigateToLocation,
                        viewModel = mapFeedViewModel,
                    )
                    MyPageType.TRAVEL -> MapTravelBottomSheetContent(
                        viewModel = mapTravelViewModel,
                    )
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
                    when (myPageType) {
                        MyPageType.FEED -> {
                            mapFeedUiState.feedList.forEach { item ->
                                MapFeedMarker(
                                    item = item,
                                    onClick = { mapFeedViewModel.onIntent(MapFeedUiIntent.ClickFeedItem(item.id)) },
                                )
                            }
                        }
                        MyPageType.TRAVEL -> {
                            mapTravelUiState.travelList.forEach { item ->
                                MapTravelMarker(
                                    item = item,
                                    onClick = { mapTravelViewModel.onIntent(MapTravelUiIntent.ClickTravelItem(item.id)) },
                                )
                            }
                        }
                    }
                }
            }
        }

        val onTravelIntent = mapTravelViewModel::onIntent

        // 업로드 — 날짜 선택
        UploadDateSelectionBottomSheet(
            visible = mapTravelUiState.showUploadDateSelectionSheet,
            currentMonth = YearMonth.now(),
            selectedDate = mapTravelUiState.draftUploadDate,
            onOpenOtherDate = { onTravelIntent(MapTravelUiIntent.ShowUploadCalendarDialog) },
            onDateSelected = { onTravelIntent(MapTravelUiIntent.DraftSelectUploadDate(it)) },
            onReset = { onTravelIntent(MapTravelUiIntent.DraftResetUploadDate) },
            onDismiss = { onTravelIntent(MapTravelUiIntent.HideUploadDateSelectionSheet) },
            onComplete = { onTravelIntent(MapTravelUiIntent.ConfirmUploadDate) },
        )

        // 업로드 — 시간 선택
        SelectTimeDialog(
            showDialog = mapTravelUiState.showSelectTimeDialog,
            selectedAmPm = mapTravelUiState.uploadData.amPm,
            selectedHour = mapTravelUiState.uploadData.hour,
            selectedMinute = mapTravelUiState.uploadData.minute,
            isUndecided = mapTravelUiState.uploadData.isTimeUndecided,
            onDismiss = { onTravelIntent(MapTravelUiIntent.HideSelectTimeDialog) },
            onTimeSelected = { amPm, hour, minute ->
                onTravelIntent(MapTravelUiIntent.ConfirmTime(amPm, hour, minute))
            },
            onUndecidedSelected = { onTravelIntent(MapTravelUiIntent.ConfirmTimeUndecided) },
        )

        // 업로드 — 3단계 시트 (활동유형 / 조건 / 글작성)
        UploadTravelBottomSheet(
            visible = mapTravelUiState.showUploadActivityTypeSheet,
            startPage = mapTravelUiState.uploadInitialPage,
            uploadData = mapTravelUiState.uploadData,
            ageOptions = mapTravelUiState.ageOptions,
            genderOptions = mapTravelUiState.genderOptions,
            onActivityTypeSelected = { onTravelIntent(MapTravelUiIntent.SelectUploadActivityType(it)) },
            onParticipantsSelected = { onTravelIntent(MapTravelUiIntent.SelectUploadParticipants(it)) },
            onAgeSelected = { onTravelIntent(MapTravelUiIntent.SelectUploadAge(it)) },
            onGenderSelected = { onTravelIntent(MapTravelUiIntent.SelectUploadGender(it)) },
            onConditionReset = { onTravelIntent(MapTravelUiIntent.ResetUploadConditions) },
            onTitleChanged = { onTravelIntent(MapTravelUiIntent.UpdateUploadTitle(it)) },
            onContentChanged = { onTravelIntent(MapTravelUiIntent.UpdateUploadContent(it)) },
            onDismiss = { onTravelIntent(MapTravelUiIntent.HideUploadActivityTypeSheet) },
            onConfirmUpload = { onTravelIntent(MapTravelUiIntent.ConfirmUpload) },
        )

        // 업로드 — 위치 추가 시트
        AddLocationBottomSheet(
            visible = mapTravelUiState.showLocationSheet,
            onBackClick = { onTravelIntent(MapTravelUiIntent.BackFromLocationSheet) },
            onCloseClick = { onTravelIntent(MapTravelUiIntent.PostConfirmClose) },
            onSkipClick = { onTravelIntent(MapTravelUiIntent.SkipLocation) },
            onAddLocationClick = { onTravelIntent(MapTravelUiIntent.ConfirmLocation) },
        )

        WitSnackBarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState,
        )
    }
}
