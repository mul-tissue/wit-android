package com.multissue.wit.feature.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multissue.wit.designsystem.component.selectable.WitSelectType
import com.multissue.wit.feature.map.component.FilterSearchRow
import com.multissue.wit.feature.map.component.MapBottomSheetScaffold
import com.multissue.wit.feature.map.component.MapTest
import com.multissue.wit.feature.map.component.MapTopAppBar
import com.multissue.wit.feature.map.component.feed.FeedBottomSheetContent
import com.multissue.wit.feature.map.component.travel.ActivityTypeBottomSheet
import com.multissue.wit.feature.map.component.travel.AddLocationBottomSheet
import com.multissue.wit.feature.map.component.travel.AgeGenderBottomSheet
import com.multissue.wit.feature.map.component.travel.CalendarDateSelectionScreen
import com.multissue.wit.feature.map.component.travel.DateSelectionBottomSheet
import com.multissue.wit.feature.map.component.travel.PostConfirmBottomSheet
import com.multissue.wit.feature.map.component.travel.SearchScreen
import com.multissue.wit.feature.map.component.travel.SelectTimeDialog
import com.multissue.wit.feature.map.component.travel.TravelBottomSheetContent
import com.multissue.wit.feature.map.component.travel.UploadCalendarDateSelectionScreen
import com.multissue.wit.feature.map.component.travel.UploadDateSelectionBottomSheet
import com.multissue.wit.feature.map.component.travel.UploadTravelBottomSheet
import com.multissue.wit.feature.map.dummy.placeDummyList
import androidx.compose.ui.res.stringResource
import com.multissue.wit.designsystem.component.dialog.WitDialog
import com.multissue.wit.designsystem.component.dialog.WitDialogDefaultLayout
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.state.FeedFilterType
import com.multissue.wit.feature.map.state.travel.TravelSideEffect
import com.multissue.wit.feature.map.state.travel.TravelUiIntent
import com.multissue.wit.feature.map.state.travel.TravelUiState
import com.multissue.wit.feature.map.util.permission.LocationPermission
import java.time.YearMonth
import kotlinx.coroutines.flow.Flow

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = hiltViewModel(),
    travelViewModel: TravelViewModel = hiltViewModel(),
    onFeedItemClicked: (feedId: Int) -> Unit,
    onTravelItemClicked: (travelId: Int) -> Unit,
    onChatRoomNavigate: (chatRoomId: Int) -> Unit,
    centerButtonEvent: Flow<Unit>,
    onNavRailVisibilityChanged: (Boolean) -> Unit = {},
) {
    val travelUiState by travelViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        travelViewModel.sideEffect.collect { effect ->
            when (effect) {
                is TravelSideEffect.NavigateToDetail -> onTravelItemClicked(effect.travelId)
                is TravelSideEffect.NavigateToChatRoom -> onChatRoomNavigate(effect.chatRoomId)
            }
        }
    }

    MapScreen(
        modifier = modifier,
        travelUiState = travelUiState,
        onTravelIntent = travelViewModel::onIntent,
        onFeedItemClicked = onFeedItemClicked,
        centerButtonEvent = centerButtonEvent,
        onNavRailVisibilityChanged = onNavRailVisibilityChanged,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    travelUiState: TravelUiState,
    onTravelIntent: (TravelUiIntent) -> Unit,
    onFeedItemClicked: (feedId: Int) -> Unit,
    centerButtonEvent: Flow<Unit>,
    onNavRailVisibilityChanged: (Boolean) -> Unit = {},
) {
    // TODO UI STATE
    var filter by rememberSaveable { mutableStateOf(FeedFilterType.POPULAR) }

    LaunchedEffect(travelUiState.showSearchScreen, travelUiState.showPostConfirmSheet, travelUiState.showCalendarDialog, travelUiState.showUploadCalendarDialog) {
        onNavRailVisibilityChanged(
            !travelUiState.showSearchScreen && !travelUiState.showPostConfirmSheet && !travelUiState.showCalendarDialog && !travelUiState.showUploadCalendarDialog
        )
    }

    LocationPermission {
        // TODO UI STATE
        var selected by rememberSaveable { mutableStateOf(WitSelectType.Feed) }
        var searchText by rememberSaveable { mutableStateOf("") }

        val currentSelected by rememberUpdatedState(selected)
        LaunchedEffect(centerButtonEvent) {
            centerButtonEvent.collect {
                when (currentSelected) {
                    WitSelectType.Travel -> onTravelIntent(TravelUiIntent.ShowUploadDateSelectionSheet)
                    WitSelectType.Feed -> { /* TODO: Feed + 버튼 동작 */ }
                }
            }
        }

        // 활동 유형
        ActivityTypeBottomSheet(
            visible = travelUiState.showActivityTypeSheet,
            selectedActivityType = travelUiState.draftActivityType,
            onActivityTypeSelected = { onTravelIntent(TravelUiIntent.DraftSelectActivityType(it)) },
            onDismiss = { onTravelIntent(TravelUiIntent.HideActivityTypeSheet) },
            onComplete = { onTravelIntent(TravelUiIntent.ConfirmActivityType) }
        )

        // 나이/성별
        AgeGenderBottomSheet(
            visible = travelUiState.showAgeGenderSheet,
            ageOptions = travelUiState.ageOptions,
            genderOptions = travelUiState.genderOptions,
            selectedAge = travelUiState.draftSelectedAge,
            selectedGender = travelUiState.draftSelectedGender,
            onAgeSelected = { onTravelIntent(TravelUiIntent.DraftSelectAge(it)) },
            onGenderSelected = { onTravelIntent(TravelUiIntent.DraftSelectGender(it)) },
            onDismiss = { onTravelIntent(TravelUiIntent.HideAgeGenderSheet) },
            onComplete = { onTravelIntent(TravelUiIntent.ConfirmAgeGender) }
        )

        // 날짜
        DateSelectionBottomSheet(
            visible = travelUiState.showDateSelectionSheet,
            currentMonth = YearMonth.now(),
            startDate = travelUiState.draftStartDate,
            endDate = travelUiState.draftEndDate,
            onOpenOtherDate = {
                onTravelIntent(TravelUiIntent.HideDateSelectionSheet)
                onTravelIntent(TravelUiIntent.ShowCalendarDialog)
            },
            onDateSelected = { onTravelIntent(TravelUiIntent.DraftSelectDate(it)) },
            onReset = { onTravelIntent(TravelUiIntent.DraftResetDate) },
            onDismiss = { onTravelIntent(TravelUiIntent.HideDateSelectionSheet) },
            onComplete = { onTravelIntent(TravelUiIntent.ConfirmDate) }
        )

        // 업로드 — 날짜 선택
        UploadDateSelectionBottomSheet(
            visible = travelUiState.showUploadDateSelectionSheet,
            currentMonth = YearMonth.now(),
            selectedDate = travelUiState.draftUploadDate,
            onOpenOtherDate = {
                onTravelIntent(TravelUiIntent.ShowUploadCalendarDialog)
            },
            onDateSelected = { onTravelIntent(TravelUiIntent.DraftSelectUploadDate(it)) },
            onReset = { onTravelIntent(TravelUiIntent.DraftResetUploadDate) },
            onDismiss = { onTravelIntent(TravelUiIntent.HideUploadDateSelectionSheet) },
            onComplete = { onTravelIntent(TravelUiIntent.ConfirmUploadDate) }
        )

        // 업로드 — 시간 선택
        SelectTimeDialog(
            showDialog = travelUiState.showSelectTimeDialog,
            selectedAmPm = travelUiState.uploadData.amPm,
            selectedHour = travelUiState.uploadData.hour,
            selectedMinute = travelUiState.uploadData.minute,
            isUndecided = travelUiState.uploadData.isTimeUndecided,
            onDismiss = { onTravelIntent(TravelUiIntent.HideSelectTimeDialog) },
            onTimeSelected = { ampm, hour, minute ->
                onTravelIntent(TravelUiIntent.ConfirmTime(ampm, hour, minute))
            },
            onUndecidedSelected = { onTravelIntent(TravelUiIntent.ConfirmTimeUndecided) }
        )

        // 업로드 — 3단계 시트 (활동유형 / 조건 / 글작성)
        UploadTravelBottomSheet(
            visible = travelUiState.showUploadActivityTypeSheet,
            uploadData = travelUiState.uploadData,
            ageOptions = travelUiState.ageOptions,
            genderOptions = travelUiState.genderOptions,
            onActivityTypeSelected = { onTravelIntent(TravelUiIntent.SelectUploadActivityType(it)) },
            onParticipantsSelected = { onTravelIntent(TravelUiIntent.SelectUploadParticipants(it)) },
            onAgeSelected = { onTravelIntent(TravelUiIntent.SelectUploadAge(it)) },
            onGenderSelected = { onTravelIntent(TravelUiIntent.SelectUploadGender(it)) },
            onConditionReset = { onTravelIntent(TravelUiIntent.ResetUploadConditions) },
            onTitleChanged = { onTravelIntent(TravelUiIntent.UpdateUploadTitle(it)) },
            onContentChanged = { onTravelIntent(TravelUiIntent.UpdateUploadContent(it)) },
            onDismiss = { onTravelIntent(TravelUiIntent.HideUploadActivityTypeSheet) },
            onConfirmUpload = { onTravelIntent(TravelUiIntent.ConfirmUpload) },
        )

        // 업로드 — 위치 추가 시트
        AddLocationBottomSheet(
            visible = travelUiState.showLocationSheet,
            onBackClick = { onTravelIntent(TravelUiIntent.BackFromLocationSheet) },
            onCloseClick = { onTravelIntent(TravelUiIntent.PostConfirmClose) },
            onSkipClick = { onTravelIntent(TravelUiIntent.SkipLocation) },
            onAddLocationClick = { onTravelIntent(TravelUiIntent.ConfirmLocation) },
        )

        // 업로드 — 게시 전 확인 시트
        PostConfirmBottomSheet(
            visible = travelUiState.showPostConfirmSheet,
            uploadTravelData = travelUiState.uploadData,
            onBackClick = { onTravelIntent(TravelUiIntent.PostConfirmBack) },
            onCloseClick = { onTravelIntent(TravelUiIntent.PostConfirmClose) },
            onCompleteClick = { onTravelIntent(TravelUiIntent.PostConfirmComplete) },
        )

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
                when (selected) {
                    WitSelectType.Feed -> {
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

                    WitSelectType.Travel -> {
                        TravelBottomSheetContent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            activityType = travelUiState.selectedActivityType,
                            ageAndGender = travelUiState.ageAndGenderStr,
                            selectDate = travelUiState.dateStr,
                            onActivityFilterClick = { onTravelIntent(TravelUiIntent.ShowActivityTypeSheet) },
                            onAgeAndGenderFilterClick = { onTravelIntent(TravelUiIntent.ShowAgeGenderSheet) },
                            onDateFilterClick = { onTravelIntent(TravelUiIntent.ShowDateSelectionSheet) },
                            onActivityClear = { onTravelIntent(TravelUiIntent.ClearActivityType) },
                            onAgeGenderClear = { onTravelIntent(TravelUiIntent.ClearAgeGender) },
                            onDateClear = { onTravelIntent(TravelUiIntent.ClearDate) },
                            onReloadClick = { onTravelIntent(TravelUiIntent.Reload) },
                            onChatClick = { onTravelIntent(TravelUiIntent.ShowJoinChatDialog(it)) },
                            onItemClick = { onTravelIntent(TravelUiIntent.NavigateToTravelDetail(it)) }
                        )
                    }
                }
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

        // 업로드 — 전체 화면 달력 (단일 날짜, 3개월)
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = travelUiState.showUploadCalendarDialog,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(400)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(400)
            )
        ) {
            UploadCalendarDateSelectionScreen(
                selectedDate = travelUiState.draftUploadDate,
                onDateSelected = { onTravelIntent(TravelUiIntent.DraftSelectUploadDate(it)) },
                onReset = { onTravelIntent(TravelUiIntent.DraftResetUploadDate) },
                onComplete = { onTravelIntent(TravelUiIntent.ConfirmUploadDate) },
                onBack = { onTravelIntent(TravelUiIntent.HideUploadCalendarDialog) }
            )
        }

        // 필터 — 전체 화면 달력
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = travelUiState.showCalendarDialog,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(400)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(400)
            )
        ) {
            CalendarDateSelectionScreen(
                startDate = travelUiState.draftStartDate,
                endDate = travelUiState.draftEndDate,
                onDateSelected = { onTravelIntent(TravelUiIntent.DraftSelectDate(it)) },
                onReset = { onTravelIntent(TravelUiIntent.DraftResetDate) },
                onComplete = {
                    onTravelIntent(TravelUiIntent.ConfirmDate)
                    onTravelIntent(TravelUiIntent.HideCalendarDialog)
                },
                onBack = { onTravelIntent(TravelUiIntent.HideCalendarDialog) }
            )
        }

        // 업로드 — 위치 검색 전체 화면
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = travelUiState.showSearchScreen,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(400)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(400)
            )
        ) {
            SearchScreen(
                searchText = travelUiState.searchText,
                onSearchTextChanged = { onTravelIntent(TravelUiIntent.UpdateSearchText(it)) },
                onSearch = { /* TODO: 검색 API 연결 */ },
                searchResults = travelUiState.searchResults,
                selectedItem = travelUiState.selectedSearchResult,
                onResultClick = { onTravelIntent(TravelUiIntent.SelectSearchResult(it)) },
                onConfirmClick = { onTravelIntent(TravelUiIntent.ConfirmSearchResult) },
                onBack = { onTravelIntent(TravelUiIntent.HideSearchScreen) },
            )
        }

        WitDialog(
            showDialog = travelUiState.showJoinChatDialog,
            title = stringResource(R.string.travel_join_chat_title),
            leftButtonText = stringResource(R.string.travel_join_chat_cancel),
            rightButtonText = stringResource(R.string.travel_join_chat_confirm),
            rightButtonColor = WitTheme.colors.primaryDark,
            onLeftButtonClick = { onTravelIntent(TravelUiIntent.HideJoinChatDialog) },
            onRightButtonClick = { onTravelIntent(TravelUiIntent.ConfirmJoinChat) },
        ) {
            WitDialogDefaultLayout()
        }
    }
}
