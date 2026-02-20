package com.multissue.wit.feature.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.component.dialog.WitDialog
import com.multissue.wit.designsystem.component.dialog.WitDialogDefaultLayout
import com.multissue.wit.designsystem.component.dialog.WitDialogTitleOnlyLayout
import com.multissue.wit.designsystem.component.snackbar.WitSnackBarHost
import com.multissue.wit.designsystem.component.snackbar.WitSnackBarVisuals
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.feed.component.FeedContentColumn
import com.multissue.wit.feature.feed.component.MapTopAppBar
import com.multissue.wit.feature.feed.component.MoreBottomSheet
import com.multissue.wit.feature.feed.component.ReactionButtonRow
import com.multissue.wit.feature.feed.component.ReportSelectColumn
import com.multissue.wit.feature.feed.component.SpH
import com.multissue.wit.feature.feed.state.FeedState
import com.multissue.wit.feature.feed.state.FeedUiIntent
import com.multissue.wit.feature.feed.state.FeedUiSideEffect
import com.multissue.wit.feature.feed.state.ReactionState
import com.multissue.wit.feature.feed.state.ReactionType
import com.multissue.wit.feature.feed.state.ReportState
import com.multissue.wit.feature.feed.state.UserState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val feedUiState by viewModel.uiState.collectAsStateWithLifecycle()

    FeedScreen(
        userState = feedUiState.user,
        reactionState = feedUiState.reactionState,
        feedState = feedUiState.feedState,
        reportState = feedUiState.reportState,
        onReactionItemClicked = viewModel::onReactionItemClick,
        onIntent = viewModel::onIntent,
        sideEffect = viewModel.sideEffect
    )
}

@Composable
internal fun FeedScreen(
    modifier: Modifier = Modifier,
    userState: UserState,
    reactionState: ReactionState,
    feedState: FeedState,
    reportState: ReportState,
    onIntent: (FeedUiIntent) -> Unit,
    onReactionItemClicked: (ReactionType) -> Unit,
    sideEffect: Flow<FeedUiSideEffect>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        sideEffect.collect { event ->
            when (event) {
                FeedUiSideEffect.OnDeleteSuccess -> {
                    snackbarHostState.showSnackbar(
                        WitSnackBarVisuals(
                            message = "게시글이 삭제되었어요.",
                            leadingIconRes = R.drawable.icon_round_check_blue
                        )
                    )
                }
                FeedUiSideEffect.OnReportSuccess -> {
                    snackbarHostState.showSnackbar(
                        WitSnackBarVisuals(
                            message = "신고가 접수되었습니다.",
                            leadingIconRes = R.drawable.icon_round_check_blue
                        )
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            MapTopAppBar(
                username = userState.username,
                userThumbnail = userState.userThumbnailUrl,
                isMine = true,
                onBackButtonClicked = {  },
                onMoreButtonClicked = { onIntent(FeedUiIntent.ClickMoreButton) },
            )
        },
        snackbarHost = {
            WitSnackBarHost(
                hostState = snackbarHostState,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(WitTheme.background.color)
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() + 24.dp,
                    start = 26.dp,
                    end = 26.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3/4f)
                    .clip(RoundedCornerShape(16.dp)),
                model = feedState.imageUrl,
                contentDescription = "피드 이미지"
            )
            SpH(18.dp)
            FeedContentColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                title = feedState.title,
                location = feedState.location,
                date = feedState.date,
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 14.dp),
                thickness = 1.dp,
                color = WitTheme.colors.gray200
            )
            ReactionButtonRow(
                modifier = Modifier.fillMaxWidth(),
                onReactionClicked = onReactionItemClicked,
                reactionState = reactionState,
            )
        }
        MoreBottomSheet(
            visible = reportState.reportBottomSheet,
            isMine = true, // TODO
            onReportButtonClicked = { onIntent(FeedUiIntent.ClickReportButton) },
            onDeleteButtonClicked = { onIntent(FeedUiIntent.ClickDeleteButton) },
            onDismiss = { onIntent(FeedUiIntent.DismissReportBottomSheet) },
        )
        WitDialog(
            showDialog = reportState.deleteDialog,
            title = "게시글을 삭제할까요?",
            leftButtonText = "닫기",
            rightButtonText = "삭제하기",
            rightButtonColor = WitTheme.colors.error,
            onLeftButtonClick = { onIntent(FeedUiIntent.ClickDialogCancelButton) },
            onRightButtonClick = { onIntent(FeedUiIntent.ClickDialogDeleteButton) }
        ) {
            WitDialogTitleOnlyLayout()
        }
        WitDialog(
            showDialog = reportState.reportDialog,
            title = "게시글을 삭제할까요?",
            message = "이 게시글에 서비스 운영 정책을 위반한다고 판단될 경우 신고 사유를 선택해 주세요.",
            bodyContent = {
                ReportSelectColumn(
                    modifier = Modifier.fillMaxWidth(),
                    selectedType = reportState.selectedReportType,
                    onItemClicked = { onIntent(FeedUiIntent.ClickReportType(it)) },
                )
            },
            leftButtonText = "취소",
            rightButtonText = "신고",
            rightButtonColor = WitTheme.colors.error,
            onLeftButtonClick = { onIntent(FeedUiIntent.ClickDialogCancelButton) },
            onRightButtonClick = { onIntent(FeedUiIntent.ClickDialogReportButton) },
        ) {
            WitDialogDefaultLayout()
        }
    }
}