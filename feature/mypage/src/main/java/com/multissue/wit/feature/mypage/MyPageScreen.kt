package com.multissue.wit.feature.mypage

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multissue.wit.designsystem.component.snackbar.WitSnackBarHost
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.addFocusCleaner
import com.multissue.wit.feature.mypage.component.MyPageHomeContent
import com.multissue.wit.feature.mypage.component.MyPageMapContent
import com.multissue.wit.feature.mypage.component.MyPageSettingsContent
import com.multissue.wit.feature.mypage.component.MyPageTopAppBar
import com.multissue.wit.feature.mypage.state.FeedItemState
import com.multissue.wit.feature.mypage.state.MyPageNav
import com.multissue.wit.feature.mypage.state.MyPageType
import com.multissue.wit.feature.mypage.state.MyPageUiIntent
import com.multissue.wit.feature.mypage.state.UserInfoState

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyPageScreen(
        modifier = modifier,
        onIntent = viewModel::onIntent,
        snackbarHostState = snackbarHostState,
        myPageNav = uiState.myPageNav,
        myPageType = uiState.myPageType,
        userInfoState = uiState.userInfo,
        feedList = uiState.feedList,
        travelList = uiState.travelList,
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
    userInfoState: UserInfoState,
    feedList: List<FeedItemState>,
    travelList: List<FeedItemState>,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(WitTheme.background.color)
            .addFocusCleaner(focusManager),
        topBar = {
            MyPageTopAppBar(
                onLogoButtonClicked = {},
                onNotificationButtonClicked = {},
                onSettingsButtonClicked = { onIntent(MyPageUiIntent.ClickSettingsButton) },
            )
        },
        snackbarHost = {
            WitSnackBarHost(
                hostState = snackbarHostState,
            )
        }
    ) { paddingValues ->
        when (myPageNav) {
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
                )
            }
            MyPageNav.SETTINGS -> {
                MyPageSettingsContent(
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }
    }
}
