package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.component.SpH
import com.multissue.wit.feature.map.state.travel.UploadTravelData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostConfirmBottomSheet(
    visible: Boolean,
    uploadTravelData: UploadTravelData,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    onCompleteClick: () -> Unit,
) {
    if (!visible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onDismissRequest = {},
        sheetState = sheetState,
        containerColor = WitTheme.colors.background,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false,
            shouldDismissOnClickOutside = false
        ),
        contentWindowInsets = { BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom) },
        sheetGesturesEnabled = false,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            SpH(16.dp)
            PostConfirmTopBar(
                title = stringResource(R.string.post_confirm_title),
                onBackClick = {
                    scope.launch {
                        sheetState.hide()
                        onBackClick()
                    }
                },
                onCloseClick = {
                    scope.launch {
                        sheetState.hide()
                        onCloseClick()
                    }
                }
            )

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                PostConfirmTitle(
                    title = stringResource(R.string.post_confirm_section_title),
                    textCount = uploadTravelData.title.length,
                    maxLength = 30
                ) {
                    PostConfirmInfoRow(
                        iconRes = R.drawable.icon_write,
                        iconDescription = "제목",
                        text = uploadTravelData.title,
                    )
                }

                PostConfirmTitle(
                    title = stringResource(R.string.post_confirm_section_content),
                    textCount = uploadTravelData.content.length,
                    maxLength = 300,
                ) {
                    PostConfirmInfoRow(
                        iconRes = R.drawable.icon_write,
                        iconDescription = "내용",
                        text = uploadTravelData.content,
                    )
                    SpH(80.dp)
                }

                if (uploadTravelData.location.isNotEmpty()) {
                    PostConfirmTitle(
                        title = stringResource(R.string.post_confirm_section_location)
                    ) {
                        PostConfirmInfoRow(
                            iconRes = R.drawable.icon_location,
                            iconDescription = "위치",
                            text = uploadTravelData.location
                        )
                    }
                }

                PostConfirmTitle(
                    title = stringResource(R.string.post_confirm_section_schedule)
                ) {
                    PostConfirmInfoRow(
                        iconRes = R.drawable.icon_calendar,
                        iconDescription = "일정",
                        text = uploadTravelData.schedule
                    )
                }

                PostConfirmTitle(
                    title = stringResource(R.string.post_confirm_section_type),
                    showDivider = false
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        PostConfirmInfoRow(
                            iconRes = R.drawable.icon_flag,
                            iconDescription = "활동 유형",
                            text = uploadTravelData.activityType
                        )
                        PostConfirmInfoRow(
                            iconRes = R.drawable.icon_people,
                            iconDescription = "인원 정보",
                            text = stringResource(
                                R.string.post_confirm_participant_format,
                                uploadTravelData.maxParticipants,
                                uploadTravelData.ageCondition,
                                uploadTravelData.genderCondition
                            )
                        )
                    }
                }

                SpH(8.dp)
            }

            WitButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .height(52.dp),
                title = stringResource(R.string.post_confirm_complete),
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        onCompleteClick()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostConfirmBottomSheetPreview() {
    WitTheme {
        PostConfirmBottomSheet(
            visible = true,
            uploadTravelData = UploadTravelData(
                title = "루브르 미술관 함께 투어알 동행 구해요 🧤",
                content = "1월 말 파리 여행 중 루브르 미술관을 같이 둘러볼 동행을 구합니다. 혼자 보기엔 아쉬워서 천천히 작품 보며 이야기 나눌 분이면 좋아요. 부담 없이 편한 분위기로 관람하고 싶어요!",
                location = "Rue de Rivoli, 75001 Paris, France",
                schedule = "3월 25일 · 06:00 PM",
                activityType = "전시/미술관",
                maxParticipants= 5,
                ageCondition = "20대",
                genderCondition = "남자"
            ),
            onBackClick = {},
            onCloseClick = {},
            onCompleteClick = {}
        )
    }
}
