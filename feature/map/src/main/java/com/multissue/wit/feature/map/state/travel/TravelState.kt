package com.multissue.wit.feature.map.state.travel

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState
import java.time.LocalDate

data class TravelUiState(
    val travelItems: List<TravelItemState> = emptyList(),
    val ageOptions: List<String> = emptyList(),
    val genderOptions: List<String> = emptyList(),

    val selectedActivityType: String = "",
    val selectedAge: String = "",
    val selectedGender: String = "",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,

    val draftActivityType: String = "",
    val draftSelectedAge: String = "",
    val draftSelectedGender: String = "",
    val draftStartDate: LocalDate? = null,
    val draftEndDate: LocalDate? = null,

    val showActivityTypeSheet: Boolean = false,
    val showAgeGenderSheet: Boolean = false,
    val showDateSelectionSheet: Boolean = false,
    val showCalendarDialog: Boolean = false,
) : UiState {
    val ageAndGenderStr: String
        get() = buildString {
            if (selectedAge.isNotEmpty()) append(selectedAge)
            if (selectedAge.isNotEmpty() && selectedGender.isNotEmpty()) append(" / ")
            if (selectedGender.isNotEmpty()) append(selectedGender)
        }

    val dateStr: String
        get() = buildString {
            if (startDate != null && endDate != null) {
                append("${startDate.monthValue}/${startDate.dayOfMonth} - ${endDate.monthValue}/${endDate.dayOfMonth}")
            } else if (startDate != null) {
                append("${startDate.monthValue}/${startDate.dayOfMonth}")
            }
        }
}

data class TravelItemState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val activityType: String = "",
    val meetingDate: String = "",
    val dayDiff: Int = 0,
    val location: String = "",
    val maxParticipants: Int = 0,
    val currentParticipants: Int = 0,
    val companionThumbnails: List<String> = emptyList()
)

sealed class TravelUiIntent : UiIntent {
    data object ShowActivityTypeSheet : TravelUiIntent()
    data object HideActivityTypeSheet : TravelUiIntent()
    data object ShowAgeGenderSheet : TravelUiIntent()
    data object HideAgeGenderSheet : TravelUiIntent()
    data object ShowDateSelectionSheet : TravelUiIntent()
    data object HideDateSelectionSheet : TravelUiIntent()
    data object ShowCalendarDialog : TravelUiIntent()
    data object HideCalendarDialog : TravelUiIntent()
    data class DraftSelectActivityType(val activityType: String) : TravelUiIntent()
    data class DraftSelectAge(val age: String) : TravelUiIntent()
    data class DraftSelectGender(val gender: String) : TravelUiIntent()
    data class DraftSelectDate(val date: LocalDate) : TravelUiIntent()
    data object DraftResetDate : TravelUiIntent()
    data object ConfirmActivityType : TravelUiIntent()
    data object ConfirmAgeGender : TravelUiIntent()
    data object ConfirmDate : TravelUiIntent()
    data object ClearActivityType : TravelUiIntent()
    data object ClearAgeGender : TravelUiIntent()
    data object ClearDate : TravelUiIntent()
    data object Reload : TravelUiIntent()
}

sealed interface TravelSideEffect : UiSideEffect {
    data class NavigateToDetail(val travelId: Int) : TravelSideEffect
}
