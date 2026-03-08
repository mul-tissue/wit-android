package com.multissue.wit.feature.mypage

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multissue.wit.core.ui.R
import com.multissue.wit.core.ui.travel.component.OptionBottomSheet
import com.multissue.wit.core.ui.travel.component.PostConfirmBottomSheet
import com.multissue.wit.core.ui.travel.component.SearchScreen
import com.multissue.wit.core.ui.travel.component.TravelPostCardMapThumbnail
import com.multissue.wit.core.ui.travel.component.TravelPostCardScreen
import com.multissue.wit.core.ui.travel.component.UploadCalendarDateSelectionScreen
import com.multissue.wit.designsystem.component.dialog.WitDialog
import com.multissue.wit.designsystem.component.dialog.WitDialogDefaultLayout
import com.multissue.wit.designsystem.component.snackbar.WitSnackBarHost
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.addFocusCleaner
import com.multissue.wit.feature.mypage.component.MyPageHomeContent
import com.multissue.wit.feature.mypage.component.MyPageHomeTopAppBar
import com.multissue.wit.feature.mypage.component.map.MyPageMapContent
import com.multissue.wit.feature.mypage.component.map.MyPageMapTopAppBar
import com.multissue.wit.feature.mypage.component.profileedit.ProfileEditContent
import com.multissue.wit.feature.mypage.component.profileedit.ProfileEditTopAppBar
import com.multissue.wit.feature.mypage.component.settings.MyPageSettingsContent
import com.multissue.wit.feature.mypage.component.settings.MyPageSettingsTopAppBar
import com.multissue.wit.feature.mypage.state.MyPageNav
import com.multissue.wit.feature.mypage.state.MyPageType
import com.multissue.wit.feature.mypage.state.MyPageUiIntent
import com.multissue.wit.feature.mypage.state.UserInfoState
import com.multissue.wit.feature.mypage.state.feed.FeedItemState
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiIntent

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
    onBottomNavVisibilityChanged: (Boolean) -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.myPageNav) {
        onBottomNavVisibilityChanged(uiState.myPageNav != MyPageNav.PROFILE_EDIT)
    }

    DisposableEffect(Unit) {
        onDispose { onBottomNavVisibilityChanged(true) }
    }

    BackHandler(enabled = uiState.myPageNav != MyPageNav.HOME) {
        viewModel.onIntent(MyPageUiIntent.ClickBackButton)
    }

    MyPageScreen(
        modifier = modifier,
        onIntent = viewModel::onIntent,
        snackbarHostState = snackbarHostState,
        myPageNav = uiState.myPageNav,
        myPageType = uiState.myPageType,
        selectedCity = uiState.selectedCity,
        userInfoState = uiState.userInfo,
        feedList = uiState.feedList,
        travelList = uiState.travelList,
        onBottomNavVisibilityChanged = onBottomNavVisibilityChanged,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun MyPageScreen(
    modifier: Modifier = Modifier,
    onIntent: (MyPageUiIntent) -> Unit,
    snackbarHostState: SnackbarHostState,
    myPageNav: MyPageNav,
    myPageType: MyPageType,
    selectedCity: String,
    userInfoState: UserInfoState,
    feedList: List<FeedItemState>,
    travelList: List<FeedItemState>,
    onBottomNavVisibilityChanged: (Boolean) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    val mapTravelViewModel: MapTravelViewModel = hiltViewModel()
    val mapTravelUiState by mapTravelViewModel.uiState.collectAsStateWithLifecycle()

    // 풀스크린 오버레이 표시 여부
    val hasFullScreenOverlay = myPageNav == MyPageNav.MAP && (
            mapTravelUiState.showTravelPostCard
                    || mapTravelUiState.showPostConfirmSheet
                    || mapTravelUiState.showUploadCalendarDialog
                    || mapTravelUiState.showSearchScreen
            )

    LaunchedEffect(hasFullScreenOverlay) {
        if (myPageNav == MyPageNav.MAP) {
            onBottomNavVisibilityChanged(!hasFullScreenOverlay)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(WitTheme.background.color)
                .addFocusCleaner(focusManager),
            topBar = {
                AnimatedContent(
                    targetState = myPageNav,
                    transitionSpec = {
                        val direction = if (targetState.ordinal > initialState.ordinal) 1 else -1
                        slideInHorizontally { it * direction } + fadeIn() togetherWith
                                slideOutHorizontally { it * -direction } + fadeOut()
                    },
                    label = "MyPageTopBarAnimation"
                ) { nav ->
                    when (nav) {
                        MyPageNav.HOME -> {
                            MyPageHomeTopAppBar(
                                onLogoButtonClicked = {},
                                onNotificationButtonClicked = {},
                                onSettingsButtonClicked = { onIntent(MyPageUiIntent.ClickSettingsButton) },
                            )
                        }
                        MyPageNav.MAP -> {
                            MyPageMapTopAppBar(
                                onBackButtonClicked = { onIntent(MyPageUiIntent.ClickBackButton) },
                                onNotificationButtonClicked = { },
                            )
                        }
                        MyPageNav.SETTINGS -> {
                            MyPageSettingsTopAppBar(
                                onBackButtonClicked = { onIntent(MyPageUiIntent.ClickBackButton) },
                                onNotificationButtonClicked = { },
                            )
                        }
                        MyPageNav.PROFILE_EDIT -> {
                            ProfileEditTopAppBar(
                                onBackButtonClicked = { onIntent(MyPageUiIntent.ClickBackButton) },
                            )
                        }
                    }
                }
            },
            snackbarHost = {
                WitSnackBarHost(
                    hostState = snackbarHostState,
                )
            }
        ) { paddingValues ->
            AnimatedContent(
                targetState = myPageNav,
                transitionSpec = {
                    val direction = if (targetState.ordinal > initialState.ordinal) 1 else -1
                    slideInHorizontally { it * direction } + fadeIn() togetherWith
                            slideOutHorizontally { it * -direction } + fadeOut()
                },
                label = "MyPageContentAnimation"
            ) { nav ->
                when (nav) {
                    MyPageNav.HOME -> {
                        MyPageHomeContent(
                            modifier = Modifier.padding(paddingValues),
                            myPageType = myPageType,
                            userInfoState = userInfoState,
                            feedList = feedList,
                            travelList = travelList,
                            onIntent = onIntent,
                        )
                    }
                    MyPageNav.MAP -> {
                        MyPageMapContent(
                            modifier = Modifier.padding(paddingValues),
                            myPageType = myPageType,
                            cityName = selectedCity,
                            mapTravelViewModel = mapTravelViewModel,
                        )
                    }
                    MyPageNav.SETTINGS -> {
                        MyPageSettingsContent(
                            modifier = Modifier.padding(paddingValues),
                            onNavigateToProfileEdit = { onIntent(MyPageUiIntent.NavigateToProfileEdit) },
                        )
                    }
                    MyPageNav.PROFILE_EDIT -> {
                        ProfileEditContent(
                            modifier = Modifier.padding(paddingValues),
                        )
                    }
                }
            }
        }

        if (myPageNav == MyPageNav.MAP) {
            val onTravelIntent = mapTravelViewModel::onIntent

            // 동행 게시글 상세 — 전체 화면 슬라이드업
            AnimatedVisibility(
                modifier = Modifier.fillMaxSize(),
                visible = mapTravelUiState.showTravelPostCard,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(400),
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(400),
                ),
            ) {
                mapTravelUiState.selectedTravelItem?.let { item ->
                    TravelPostCardScreen(
                        travelItem = item,
                        onBack = { onTravelIntent(MapTravelUiIntent.HideTravelPostCard) },
                        onMoreClick = { onTravelIntent(MapTravelUiIntent.ShowOptionSheet) },
                        mapPreview = if (item.lat != 0.0 || item.lng != 0.0) {
                            {
                                TravelPostCardMapThumbnail(
                                    lat = item.lat,
                                    lng = item.lng,
                                )
                            }
                        } else null,
                    )
                }
            }

            // 업로드 — 게시 전 확인 시트 (풀스크린)
            PostConfirmBottomSheet(
                visible = mapTravelUiState.showPostConfirmSheet,
                uploadTravelData = mapTravelUiState.uploadData,
                isEditMode = mapTravelUiState.isEditMode,
                onWriteClick = if (mapTravelUiState.isEditMode) {
                    { onTravelIntent(MapTravelUiIntent.EditWriteSection) }
                } else null,
                onLocationClick = if (mapTravelUiState.isEditMode) {
                    { onTravelIntent(MapTravelUiIntent.EditLocationSection) }
                } else null,
                onScheduleClick = if (mapTravelUiState.isEditMode) {
                    { onTravelIntent(MapTravelUiIntent.EditScheduleSection) }
                } else null,
                onTypeClick = if (mapTravelUiState.isEditMode) {
                    { onTravelIntent(MapTravelUiIntent.EditTypeSection) }
                } else null,
                onBackClick = { onTravelIntent(MapTravelUiIntent.PostConfirmBack) },
                onCloseClick = { onTravelIntent(MapTravelUiIntent.PostConfirmClose) },
                onCompleteClick = {
                    if (mapTravelUiState.isEditMode) onTravelIntent(MapTravelUiIntent.ConfirmEdit)
                    else onTravelIntent(MapTravelUiIntent.PostConfirmClose)
                },
            )

            // 업로드 — 전체 화면 달력 (단일 날짜)
            AnimatedVisibility(
                modifier = Modifier.fillMaxSize(),
                visible = mapTravelUiState.showUploadCalendarDialog,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(400),
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(400),
                ),
            ) {
                UploadCalendarDateSelectionScreen(
                    selectedDate = mapTravelUiState.draftUploadDate,
                    onDateSelected = { onTravelIntent(MapTravelUiIntent.DraftSelectUploadDate(it)) },
                    onReset = { onTravelIntent(MapTravelUiIntent.DraftResetUploadDate) },
                    onComplete = { onTravelIntent(MapTravelUiIntent.ConfirmUploadDate) },
                    onBack = { onTravelIntent(MapTravelUiIntent.HideUploadCalendarDialog) },
                )
            }

            // 업로드 — 위치 검색 전체 화면
            AnimatedVisibility(
                modifier = Modifier.fillMaxSize(),
                visible = mapTravelUiState.showSearchScreen,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(400),
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(400),
                ),
            ) {
                SearchScreen(
                    searchText = mapTravelUiState.searchText,
                    onSearchTextChanged = { onTravelIntent(MapTravelUiIntent.UpdateSearchText(it)) },
                    onSearch = { /* TODO: 검색 API 연결 */ },
                    searchResults = mapTravelUiState.searchResults,
                    selectedItem = mapTravelUiState.selectedSearchResult,
                    onResultClick = { onTravelIntent(MapTravelUiIntent.SelectSearchResult(it)) },
                    onConfirmClick = { onTravelIntent(MapTravelUiIntent.ConfirmSearchResult) },
                    onBack = { onTravelIntent(MapTravelUiIntent.HideSearchScreen) },
                )
            }

            // 옵션 시트 (수정/삭제)
            OptionBottomSheet(
                visible = mapTravelUiState.showOptionSheet,
                onCancel = { onTravelIntent(MapTravelUiIntent.HideOptionSheet) },
                onModify = { onTravelIntent(MapTravelUiIntent.ShowEditMode) },
                onDelete = { onTravelIntent(MapTravelUiIntent.ShowDeleteConfirmDialog) },
            )

            // 삭제 확인 다이얼로그
            WitDialog(
                showDialog = mapTravelUiState.showDeleteConfirmDialog,
                title = stringResource(R.string.travel_delete_dialog_title),
                leftButtonText = stringResource(R.string.travel_delete_cancel),
                rightButtonText = stringResource(R.string.travel_delete_confirm),
                rightButtonColor = WitTheme.colors.error,
                onLeftButtonClick = { onTravelIntent(MapTravelUiIntent.HideDeleteConfirmDialog) },
                onRightButtonClick = { onTravelIntent(MapTravelUiIntent.ConfirmDelete) },
            ) {
                WitDialogDefaultLayout()
            }
        }
    }
}
