package com.multissue.wit.feature.map

import androidx.lifecycle.viewModelScope
import com.multissue.wit.core.ui.base.BaseViewModel
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
        setState {
            copy(
                showUploadDateSelectionSheet = true
            )
        }
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
