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
import androidx.compose.runtime.remember
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
import com.multissue.wit.feature.map.component.travel.AgeGenderBottomSheet
import com.multissue.wit.feature.map.component.travel.CalendarDateSelectionScreen
import com.multissue.wit.feature.map.component.travel.DateSelectionBottomSheet
import com.multissue.wit.feature.map.component.travel.TravelBottomSheetContent
import com.multissue.wit.feature.map.dummy.placeDummyList
import com.multissue.wit.feature.map.state.FeedFilterType
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
    centerButtonEvent: Flow<Unit>,
) {
    val travelUiState by travelViewModel.uiState.collectAsStateWithLifecycle()

    MapScreen(
        modifier = modifier,
        travelUiState = travelUiState,
        onTravelIntent = travelViewModel::onIntent,
        onFeedItemClicked = onFeedItemClicked,
        centerButtonEvent = centerButtonEvent
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
) {
    // TODO UI STATE
    var filter by remember { mutableStateOf(FeedFilterType.POPULAR) }

    LocationPermission {
        // TODO UI STATE
        var selected by remember { mutableStateOf(WitSelectType.Feed) }
        var searchText by remember { mutableStateOf("") }

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
                            onChatClick = { /*TODO*/ },
                            onItemClick = { /*TODO*/ }
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

        // 전체 화면 달력
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
    }
}
