package com.multissue.wit.feature.mypage

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import com.multissue.wit.feature.mypage.component.MyPageHomeTopAppBar
import com.multissue.wit.feature.mypage.component.MyPageMapContent
import com.multissue.wit.feature.mypage.component.MyPageMapTopAppBar
import com.multissue.wit.feature.mypage.component.settings.MyPageSettingsContent
import com.multissue.wit.feature.mypage.component.settings.MyPageSettingsTopAppBar
import com.multissue.wit.feature.mypage.state.feed.FeedItemState
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

    BackHandler(
        enabled = uiState.myPageNav == MyPageNav.MAP || uiState.myPageNav == MyPageNav.SETTINGS
    ) {
        viewModel.onIntent(MyPageUiIntent.ClickBackButton)
    }

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
}
