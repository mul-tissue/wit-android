package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.addFocusCleaner
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.state.travel.UploadTravelData
import kotlinx.coroutines.launch

private const val TOTAL_STEPS = 3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadTravelBottomSheet(
    visible: Boolean,
    uploadData: UploadTravelData,
    ageOptions: List<String>,
    genderOptions: List<String>,
    onActivityTypeSelected: (String) -> Unit,
    onParticipantsSelected: (Int) -> Unit,
    onAgeSelected: (String) -> Unit,
    onGenderSelected: (String) -> Unit,
    onConditionReset: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirmUpload: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val pagerState = rememberPagerState(
        pageCount = { TOTAL_STEPS }
    )
    val scope = rememberCoroutineScope()

    if (!visible) return

    val stepTitles = listOf(
        stringResource(R.string.upload_step_activity_type),
        stringResource(R.string.upload_step_condition),
        stringResource(R.string.upload_step_post),
    )
    val currentPage = pagerState.currentPage

    val isNextEnabled = when (currentPage) {
        0 -> uploadData.activityType.isNotEmpty()
        1 -> uploadData.maxParticipants > 0 && uploadData.ageCondition.isNotEmpty() && uploadData.genderCondition.isNotEmpty()
        2 -> uploadData.title.isNotBlank() && uploadData.content.isNotBlank()
        else -> false
    }

    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = sheetState,
        containerColor = WitTheme.colors.background,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false,
            shouldDismissOnClickOutside = false,
        ),
        dragHandle = null,
        sheetGesturesEnabled = false,
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .addFocusCleaner(focusManager)
                .padding(horizontal = 24.dp)
                .padding(top = 20.dp, bottom = 20.dp),
        ) {
            UploadSheetHeader(
                title = stepTitles[currentPage],
                trailingText = stringResource(
                    R.string.upload_char_count_format,
                    currentPage + 1,
                    TOTAL_STEPS
                ),
                showBack = currentPage > 0,
                onBack = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    scope.launch {
                        pagerState.animateScrollToPage(currentPage - 1)
                    }
                },
                onDismiss = onDismiss,
            )

            Spacer(modifier = Modifier.height(16.dp))

            UploadStepProgressBar(
                currentPage = currentPage,
                totalSteps = TOTAL_STEPS,
            )

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = false,
            ) { page ->
                when (page) {
                    0 -> {
                        UploadActivityTypePage(
                            selectedActivityType = uploadData.activityType,
                            onActivityTypeSelected = onActivityTypeSelected,
                        )
                    }

                    1 -> {
                        UploadConditionPage(
                            maxParticipants = uploadData.maxParticipants,
                            ageOptions = ageOptions,
                            selectedAge = uploadData.ageCondition,
                            genderOptions = genderOptions,
                            selectedGender = uploadData.genderCondition,
                            onParticipantsSelected = onParticipantsSelected,
                            onAgeSelected = onAgeSelected,
                            onGenderSelected = onGenderSelected,
                        )
                    }

                    2 -> {
                        UploadPostWritePage(
                            title = uploadData.title,
                            content = uploadData.content,
                            onTitleChanged = onTitleChanged,
                            onContentChanged = onContentChanged,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            UploadSheetBottomButtons(
                modifier = Modifier.imePadding(),
                currentPage = currentPage,
                isNextEnabled = isNextEnabled,
                onNext = {
                    if (currentPage < TOTAL_STEPS - 1) {
                        scope.launch { pagerState.animateScrollToPage(currentPage + 1) }
                    } else {
                        onConfirmUpload()
                    }
                },
                onConditionReset = onConditionReset,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UploadTravelBottomSheetPreview() {
    WitTheme {
        UploadTravelBottomSheet(
            visible = true,
            uploadData = UploadTravelData(),
            ageOptions = listOf("20대", "30대", "40대", "50대", "60대+", "무관"),
            genderOptions = listOf("남자", "여자", "무관"),
            onActivityTypeSelected = {},
            onParticipantsSelected = {},
            onAgeSelected = {},
            onGenderSelected = {},
            onConditionReset = {},
            onTitleChanged = {},
            onContentChanged = {},
            onDismiss = {},
            onConfirmUpload = {},
        )
    }
}
