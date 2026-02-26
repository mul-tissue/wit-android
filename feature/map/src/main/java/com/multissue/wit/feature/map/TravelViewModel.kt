package com.multissue.wit.feature.map

import androidx.lifecycle.viewModelScope
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.map.state.travel.AmPm
import com.multissue.wit.feature.map.state.travel.PickerHour
import com.multissue.wit.feature.map.state.travel.PickerMinute
import com.multissue.wit.feature.map.state.travel.TravelSideEffect
import com.multissue.wit.feature.map.state.travel.TravelUiIntent
import com.multissue.wit.feature.map.state.travel.TravelUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor(

) : BaseViewModel<TravelUiState, TravelSideEffect, TravelUiIntent>(
    initialState = TravelUiState()
) {
    init {
        fetchFilterOptions()
    }

    override fun onIntent(intent: TravelUiIntent) {
        when (intent) {
            is TravelUiIntent.ShowActivityTypeSheet -> onShowActivityTypeSheet()
            is TravelUiIntent.HideActivityTypeSheet -> onHideActivityTypeSheet()
            is TravelUiIntent.ShowAgeGenderSheet -> onShowAgeGenderSheet()
            is TravelUiIntent.HideAgeGenderSheet -> onHideAgeGenderSheet()
            is TravelUiIntent.ShowDateSelectionSheet -> onShowDateSelectionSheet()
            is TravelUiIntent.ShowUploadDateSelectionSheet -> onShowUploadDateSelectionSheet()
            is TravelUiIntent.HideUploadDateSelectionSheet -> onHideUploadDateSelectionSheet()
            is TravelUiIntent.DraftSelectUploadDate -> onDraftSelectUploadDate(intent.date)
            is TravelUiIntent.DraftResetUploadDate -> onDraftResetUploadDate()
            is TravelUiIntent.ConfirmUploadDate -> onConfirmUploadDate()
            is TravelUiIntent.ShowUploadCalendarDialog -> onShowUploadCalendarDialog()
            is TravelUiIntent.HideUploadCalendarDialog -> onHideUploadCalendarDialog()
            is TravelUiIntent.HideSelectTimeDialog -> onHideSelectTimeDialog()
            is TravelUiIntent.ConfirmTime -> onConfirmTime(intent.amPm, intent.hour, intent.minute)
            is TravelUiIntent.ConfirmTimeUndecided -> onConfirmTimeUndecided()
            is TravelUiIntent.HideUploadActivityTypeSheet -> onHideUploadActivityTypeSheet()
            is TravelUiIntent.SelectUploadActivityType -> onSelectUploadActivityType(intent.activityType)
            is TravelUiIntent.SelectUploadParticipants -> onSelectUploadParticipants(intent.count)
            is TravelUiIntent.SelectUploadAge -> onSelectUploadAge(intent.age)
            is TravelUiIntent.SelectUploadGender -> onSelectUploadGender(intent.gender)
            is TravelUiIntent.ResetUploadConditions -> onResetUploadConditions()
            is TravelUiIntent.UpdateUploadTitle -> onUpdateUploadTitle(intent.title)
            is TravelUiIntent.UpdateUploadContent -> onUpdateUploadContent(intent.content)
            is TravelUiIntent.ConfirmUpload -> onConfirmUpload()
            is TravelUiIntent.HideDateSelectionSheet -> onHideDateSelectionSheet()
            is TravelUiIntent.ShowCalendarDialog -> onShowCalendarDialog()
            is TravelUiIntent.HideCalendarDialog -> onHideCalendarDialog()
            is TravelUiIntent.DraftSelectActivityType -> onDraftSelectActivityType(intent.activityType)
            is TravelUiIntent.DraftSelectAge -> onDraftSelectAge(intent.age)
            is TravelUiIntent.DraftSelectGender -> onDraftSelectGender(intent.gender)
            is TravelUiIntent.DraftSelectDate -> onDraftSelectDate(intent.date)
            is TravelUiIntent.DraftResetDate -> onDraftResetDate()
            is TravelUiIntent.ConfirmActivityType -> onConfirmActivityType()
            is TravelUiIntent.ConfirmAgeGender -> onConfirmAgeGender()
            is TravelUiIntent.ConfirmDate -> onConfirmDate()
            is TravelUiIntent.ClearActivityType -> onClearActivityType()
            is TravelUiIntent.ClearAgeGender -> onClearAgeGender()
            is TravelUiIntent.ClearDate -> onClearDate()
            is TravelUiIntent.Reload -> onReload()
        }
    }

    private fun fetchFilterOptions() {
        viewModelScope.launch {
            // TODO: Repository에서 가져오기
            setState {
                copy(
                    ageOptions = listOf("20대", "30대", "40대", "50대", "60대+", "무관"),
                    genderOptions = listOf("남자", "여자", "무관")
                )
            }
        }
    }

    private fun onShowActivityTypeSheet() {
        setState { copy(draftActivityType = selectedActivityType, showActivityTypeSheet = true) }
    }

    private fun onHideActivityTypeSheet() {
        setState { copy(showActivityTypeSheet = false) }
    }

    private fun onShowAgeGenderSheet() {
        setState {
            copy(
                draftSelectedAge = selectedAge,
                draftSelectedGender = selectedGender,
                showAgeGenderSheet = true
            )
        }
    }

    private fun onHideAgeGenderSheet() {
        setState { copy(showAgeGenderSheet = false) }
    }

    private fun onShowDateSelectionSheet() {
        setState {
            copy(
                draftStartDate = startDate,
                draftEndDate = endDate,
                showDateSelectionSheet = true
            )
        }
    }

    private fun onShowUploadDateSelectionSheet() {
        setState { copy(showUploadDateSelectionSheet = true) }
    }

    private fun onHideUploadDateSelectionSheet() {
        setState {
            copy(
                showUploadDateSelectionSheet = false,
                showSelectTimeDialog = false,
                draftUploadDate = null,
            )
        }
    }

    private fun onDraftSelectUploadDate(date: LocalDate) {
        val current = currentState.draftUploadDate
        setState { copy(draftUploadDate = if (date == current) null else date) }
    }

    private fun onDraftResetUploadDate() {
        setState { copy(draftUploadDate = null) }
    }

    private fun onConfirmUploadDate() {
        setState {
            copy(
                uploadData = uploadData.copy(meetingDate = draftUploadDate),
                showUploadCalendarDialog = false,
                // 날짜 시트는 시간 선택 완료까지 유지
                showSelectTimeDialog = true,
            )
        }
    }

    private fun onShowUploadCalendarDialog() {
        setState { copy(showUploadDateSelectionSheet = false, showUploadCalendarDialog = true) }
    }

    private fun onHideUploadCalendarDialog() {
        setState { copy(showUploadCalendarDialog = false, showUploadDateSelectionSheet = true) }
    }

    private fun onHideSelectTimeDialog() {
        // 시간 선택 취소 시 날짜 시트는 유지
        setState { copy(showSelectTimeDialog = false) }
    }

    private fun onConfirmTime(amPm: AmPm, hour: PickerHour, minute: PickerMinute) {
        setState {
            copy(
                uploadData = uploadData.copy(
                    amPm = amPm,
                    hour = hour,
                    minute = minute,
                    isTimeUndecided = false,
                ),
                showUploadDateSelectionSheet = false,
                showSelectTimeDialog = false,
                showUploadActivityTypeSheet = true,
            )
        }
    }

    private fun onConfirmTimeUndecided() {
        setState {
            copy(
                uploadData = uploadData.copy(isTimeUndecided = true),
                showUploadDateSelectionSheet = false,
                showSelectTimeDialog = false,
                showUploadActivityTypeSheet = true,
            )
        }
    }

    private fun onHideUploadActivityTypeSheet() {
        setState {
            copy(
                showUploadActivityTypeSheet = false,
                uploadData = uploadData.copy(
                    activityType = "",
                    maxParticipants = 1,
                    ageCondition = "",
                    genderCondition = "",
                    title = "",
                    content = "",
                )
            )
        }
    }

    private fun onSelectUploadActivityType(activityType: String) {
        setState { copy(uploadData = uploadData.copy(activityType = activityType)) }
    }

    private fun onSelectUploadParticipants(count: Int) {
        setState { copy(uploadData = uploadData.copy(maxParticipants = count)) }
    }

    private fun onSelectUploadAge(age: String) {
        setState { copy(uploadData = uploadData.copy(ageCondition = age)) }
    }

    private fun onSelectUploadGender(gender: String) {
        setState { copy(uploadData = uploadData.copy(genderCondition = gender)) }
    }

    private fun onResetUploadConditions() {
        setState {
            copy(
                uploadData = uploadData.copy(
                    maxParticipants = 0,
                    ageCondition = "",
                    genderCondition = "",
                )
            )
        }
    }

    private fun onUpdateUploadTitle(title: String) {
        setState { copy(uploadData = uploadData.copy(title = title)) }
    }

    private fun onUpdateUploadContent(content: String) {
        setState { copy(uploadData = uploadData.copy(content = content)) }
    }

    private fun onConfirmUpload() {
        setState { copy(showUploadActivityTypeSheet = false) }
        // TODO: 업로드 API 호출
    }

    private fun onHideDateSelectionSheet() {
        setState { copy(showDateSelectionSheet = false) }
    }

    private fun onShowCalendarDialog() {
        setState { copy(showCalendarDialog = true) }
    }

    private fun onHideCalendarDialog() {
        setState { copy(showCalendarDialog = false) }
    }

    private fun onDraftSelectActivityType(activityType: String) {
        setState { copy(draftActivityType = activityType) }
    }

    private fun onDraftSelectAge(age: String) {
        setState { copy(draftSelectedAge = age) }
    }

    private fun onDraftSelectGender(gender: String) {
        setState { copy(draftSelectedGender = gender) }
    }

    private fun onDraftSelectDate(date: LocalDate) {
        val currentDraftStart = currentState.draftStartDate
        val currentDraftEnd = currentState.draftEndDate
        when {
            currentDraftStart == null || currentDraftEnd != null -> setState {
                copy(draftStartDate = date, draftEndDate = null)
            }
            date.isBefore(currentDraftStart) -> setState { copy(draftStartDate = date) }
            date == currentDraftStart -> setState { copy(draftStartDate = null) }
            else -> setState { copy(draftEndDate = date) }
        }
    }

    private fun onDraftResetDate() {
        setState { copy(draftStartDate = null, draftEndDate = null) }
    }

    private fun onConfirmActivityType() {
        setState { copy(selectedActivityType = draftActivityType, showActivityTypeSheet = false) }
    }

    private fun onConfirmAgeGender() {
        setState {
            copy(
                selectedAge = draftSelectedAge,
                selectedGender = draftSelectedGender,
                showAgeGenderSheet = false
            )
        }
    }

    private fun onConfirmDate() {
        setState {
            copy(
                startDate = draftStartDate,
                endDate = draftEndDate,
                showDateSelectionSheet = false
            )
        }
    }

    private fun onClearActivityType() {
        setState { copy(selectedActivityType = "") }
    }

    private fun onClearAgeGender() {
        setState { copy(selectedAge = "", selectedGender = "") }
    }

    private fun onClearDate() {
        setState { copy(startDate = null, endDate = null) }
    }

    private fun onReload() {
        // TODO: 검색 API 호출
    }
}
