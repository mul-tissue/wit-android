package com.multissue.wit.feature.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.component.snackbar.WitSnackBarHost
import com.multissue.wit.designsystem.component.snackbar.WitSnackBarVisuals
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.travel.component.TravelCompanionInfo
import com.multissue.wit.feature.travel.component.TravelDetailCard
import com.multissue.wit.feature.travel.component.TravelDetailMapThumbnail
import com.multissue.wit.feature.travel.component.TravelReportBottomSheet
import com.multissue.wit.feature.travel.component.TravelReportDialog
import com.multissue.wit.feature.travel.state.TravelDetailSideEffect
import com.multissue.wit.feature.travel.state.TravelDetailUiIntent
import com.multissue.wit.feature.travel.state.TravelDetailUiState

@Composable
fun TravelDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: TravelDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onChatRoomNavigate: (Int) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val reportedSnackbarMessage = stringResource(R.string.travel_detail_reported_snackbar)

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is TravelDetailSideEffect.NavigateToChatRoom -> onChatRoomNavigate(effect.chatRoomId)
                is TravelDetailSideEffect.ShowReportSnackbar -> {
                    snackbarHostState.showSnackbar(
                        WitSnackBarVisuals(
                            message = reportedSnackbarMessage,
                            leadingIconRes = R.drawable.icon_round_check_blue,
                        )
                    )
                }
            }
        }
    }

    TravelDetailScreen(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        uiState = uiState,
        onIntent = viewModel::onIntent,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TravelDetailScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    uiState: TravelDetailUiState,
    onIntent: (TravelDetailUiIntent) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(WitTheme.colors.gradientBackground),
        ) {
            // 상단 AppBar
            WitCenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = uiState.activityType,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = WitTheme.colors.text,
                    navigationIconContentColor = WitTheme.colors.iconTint,
                    actionIconContentColor = WitTheme.colors.iconTint,
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.icon_back),
                            contentDescription = "뒤로 가기",
                            tint = WitTheme.colors.iconTint,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onIntent(TravelDetailUiIntent.ShowReportBottomSheet) }) {
                        Icon(
                            painter = painterResource(R.drawable.icon_more),
                            contentDescription = "더보기",
                            tint = WitTheme.colors.iconTint,
                        )
                    }
                },
            )

            // 스크롤 가능한 카드 영역
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                TravelDetailCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    title = uiState.title,
                    dayDiff = uiState.dayDiff,
                    participantsText = stringResource(
                        R.string.travel_detail_participant,
                        uiState.currentParticipants,
                        uiState.maxParticipants,
                    ),
                    meetingDateText = uiState.meetingDate,
                    locationName = uiState.location,
                    badges = buildList {
                        if (uiState.ageCondition.isNotEmpty()) add(uiState.ageCondition)
                        if (uiState.genderCondition.isNotEmpty()) add(uiState.genderCondition)
                        if (uiState.maxParticipants > 0) add(
                            stringResource(R.string.travel_detail_participants_format, uiState.maxParticipants)
                        )
                    },
                    authorName = uiState.authorName,
                    authorImageUrl = uiState.authorAvatarUrl.ifEmpty { null },
                    content = uiState.content,
                    locationAddress = uiState.address.ifEmpty {
                        stringResource(R.string.travel_detail_location_none)
                    },
                    mapPreview = if (uiState.lat != 0.0 && uiState.lng != 0.0) {
                        {
                            TravelDetailMapThumbnail(
                                modifier = Modifier.fillMaxSize(),
                                lat = uiState.lat,
                                lng = uiState.lng,
                            )
                        }
                    } else null,
                )
            }

            // 하단 고정 버튼 영역
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WitTheme.colors.background)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TravelCompanionInfo(
                    travelUserName = uiState.travelUserName,
                    companionThumbnails = uiState.companionThumbnails,
                )

                WitButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 6.dp),
                            painter = painterResource(R.drawable.icon_chat),
                            contentDescription = "채팅 참여",
                            tint = WitTheme.colors.iconTintConverse,
                        )
                    },
                    title = stringResource(R.string.travel_detail_join_chat),
                    onClick = { onIntent(TravelDetailUiIntent.JoinChat) },
                )
            }
        }

        WitSnackBarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState,
        )

        // 신고 바텀시트
        TravelReportBottomSheet(
            visible = uiState.isReportBottomSheetVisible,
            onCancel = { onIntent(TravelDetailUiIntent.HideReportBottomSheet) },
            onReport = { onIntent(TravelDetailUiIntent.ShowReportDialog) }
        )

        // 신고 다이얼로그
        TravelReportDialog(
            showDialog = uiState.isReportDialogVisible,
            selectedReportType = uiState.selectedReportType,
            onSelectReportType = { onIntent(TravelDetailUiIntent.SelectReportType(it)) },
            onDismiss = { onIntent(TravelDetailUiIntent.HideReportDialog) },
            onSubmit = { onIntent(TravelDetailUiIntent.SubmitReport) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelDetailScreenPreview() {
    WitTheme {
        TravelDetailScreen(
            uiState = TravelDetailUiState(
                title = "루브르 미술관 함께 투어할 동행 구해요",
                content = "1월 말 파리 여행 중 루브르 미술관을 같이 둘러볼 동행을 구합니다.\n혼자 보기엔 아쉬워서 천천히 작품 보며 이야기 나눌 분이면 좋아요.\n부담 없이 편한 분위기로 관람하고 싶어요!",
                activityType = "전시/미술관",
                meetingDate = "3월 25일 · 06:00 PM",
                dayDiff = 3,
                location = "루브르 미술관",
                maxParticipants = 5,
                currentParticipants = 1,
                ageCondition = "20대",
                genderCondition = "남자",
                authorName = "여행자",
                travelUserName = "Parprika",
                companionThumbnails = listOf("", ""),
                address = "Rue de Rivoli, 75001 Paris, France",
                lat = 48.8606,
                lng = 2.3376,
                isLoading = false,
            ),
        )
    }
}
