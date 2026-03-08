package com.multissue.wit.feature.map

import androidx.lifecycle.viewModelScope
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.core.ui.travel.state.AmPm
import com.multissue.wit.core.ui.travel.state.PickerHour
import com.multissue.wit.core.ui.travel.state.PickerMinute
import com.multissue.wit.core.ui.travel.state.SearchResultItemState
import com.multissue.wit.core.ui.travel.state.TravelItemState
import com.multissue.wit.core.ui.travel.state.UploadTravelData
import com.multissue.wit.feature.map.state.travel.TravelSideEffect
import com.multissue.wit.feature.map.state.travel.TravelUiIntent
import com.multissue.wit.feature.map.state.travel.TravelUiState
import com.multissue.wit.feature.map.dummy.searchDummyList
import com.multissue.wit.feature.map.dummy.travelDummyData
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
            is TravelUiIntent.HideLocationSheet -> onHideLocationSheet()
            is TravelUiIntent.BackFromLocationSheet -> onBackFromLocationSheet()
            is TravelUiIntent.UpdateUploadLocation -> onUpdateUploadLocation(intent.location)
            is TravelUiIntent.SkipLocation -> onSkipLocation()
            is TravelUiIntent.ConfirmLocation -> onConfirmLocation()
            is TravelUiIntent.HideSearchScreen -> onHideSearchScreen()
            is TravelUiIntent.UpdateSearchText -> onUpdateSearchText(intent.text)
            is TravelUiIntent.SelectSearchResult -> onSelectSearchResult(intent.item)
            is TravelUiIntent.ConfirmSearchResult -> onConfirmSearchResult()
            is TravelUiIntent.PostConfirmBack -> onPostConfirmBack()
            is TravelUiIntent.PostConfirmClose -> onPostConfirmClose()
            is TravelUiIntent.PostConfirmComplete -> onPostConfirmComplete()
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
            is TravelUiIntent.NavigateToTravelDetail -> postSideEffect(
                TravelSideEffect.NavigateToDetail(
                    intent.travelId
                )
            )

            is TravelUiIntent.ShowJoinChatDialog -> onShowJoinChatDialog(intent.travelId)
            is TravelUiIntent.HideJoinChatDialog -> onHideJoinChatDialog()
            is TravelUiIntent.ConfirmJoinChat -> onConfirmJoinChat()

            is TravelUiIntent.ShowTravelPostCard -> onShowTravelPostCard(intent.travelId)
            is TravelUiIntent.HideTravelPostCard -> onHideTravelPostCard()

            is TravelUiIntent.ShowOptionSheet -> setState { copy(showOptionSheet = true) }
            is TravelUiIntent.HideOptionSheet -> setState { copy(showOptionSheet = false) }
            is TravelUiIntent.ShowDeleteConfirmDialog -> setState { copy(showOptionSheet = false, showDeleteConfirmDialog = true) }
            is TravelUiIntent.HideDeleteConfirmDialog -> setState { copy(showDeleteConfirmDialog = false, showOptionSheet = true) }
            is TravelUiIntent.ConfirmDelete -> onConfirmDelete()

            is TravelUiIntent.ShowEditMode -> onShowEditMode()
            is TravelUiIntent.ConfirmEdit -> onConfirmEdit()
            is TravelUiIntent.EditTypeSection -> setState { copy(showUploadActivityTypeSheet = true, uploadInitialPage = 0) }
            is TravelUiIntent.EditScheduleSection -> setState { copy(showUploadDateSelectionSheet = true) }
            is TravelUiIntent.EditLocationSection -> setState { copy(showLocationSheet = true) }
            is TravelUiIntent.EditWriteSection -> setState { copy(showUploadActivityTypeSheet = true, uploadInitialPage = 2) }
        }
    }

    private fun fetchFilterOptions() {
        viewModelScope.launch {
            // TODO: Repository에서 가져오기
            setState {
                copy(
                    travelItems = travelDummyData,
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
                showUploadDateSelectionSheet = true,
                draftUploadDate = null,
                uploadData = UploadTravelData(),
                uploadInitialPage = 0,
                isEditMode = false,
            )
        }
    }

    private fun onHideUploadDateSelectionSheet() {
        setState {
            copy(
                showUploadDateSelectionSheet = false,
                showSelectTimeDialog = false,
                draftUploadDate = null,
                showPostConfirmSheet = if (isEditMode) true else showPostConfirmSheet,
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
        setState {
            copy(
                showUploadDateSelectionSheet = false,
                showUploadCalendarDialog = true,
                showPostConfirmSheet = if (isEditMode) false else showPostConfirmSheet,
            )
        }
    }

    private fun onHideUploadCalendarDialog() {
        setState {
            copy(
                showUploadCalendarDialog = false,
                showUploadDateSelectionSheet = true,
                showPostConfirmSheet = if (isEditMode) true else showPostConfirmSheet,
            )
        }
    }

    private fun onHideSelectTimeDialog() {
        // 시간 선택 취소 시 날짜 시트는 유지
        setState { copy(showSelectTimeDialog = false) }
    }

    private fun onConfirmTime(amPm: AmPm, hour: PickerHour, minute: PickerMinute) {
        setState {
            val schedule = buildSchedule(
                date = uploadData.meetingDate,
                isTimeUndecided = false,
                amPm = amPm,
                hour = hour,
                minute = minute,
            )
            copy(
                uploadData = uploadData.copy(
                    amPm = amPm,
                    hour = hour,
                    minute = minute,
                    isTimeUndecided = false,
                    schedule = schedule,
                ),
                showUploadDateSelectionSheet = false,
                showSelectTimeDialog = false,
                showUploadActivityTypeSheet = if (isEditMode) false else true,
                showPostConfirmSheet = if (isEditMode) true else false,
            )
        }
    }

    private fun onConfirmTimeUndecided() {
        setState {
            val schedule = buildSchedule(
                date = uploadData.meetingDate,
                isTimeUndecided = true,
            )
            copy(
                uploadData = uploadData.copy(
                    isTimeUndecided = true,
                    schedule = schedule,
                ),
                showUploadDateSelectionSheet = false,
                showSelectTimeDialog = false,
                showUploadActivityTypeSheet = if (isEditMode) false else true,
                showPostConfirmSheet = if (isEditMode) true else false,
            )
        }
    }

    private fun buildSchedule(
        date: LocalDate?,
        isTimeUndecided: Boolean,
        amPm: AmPm = AmPm.AM,
        hour: PickerHour = PickerHour.NINE,
        minute: PickerMinute = PickerMinute.ZERO,
    ): String {
        date ?: return ""
        val dateStr = "${date.monthValue}월 ${date.dayOfMonth}일"
        val timeStr =
            if (isTimeUndecided) "미정" else "${hour.displayText}:${minute.displayText} ${amPm.name}"
        return "$dateStr · $timeStr"
    }

    private fun onHideUploadActivityTypeSheet() {
        if (currentState.isEditMode) {
            setState { copy(showUploadActivityTypeSheet = false, showPostConfirmSheet = true, uploadInitialPage = 0) }
        } else {
            setState {
                copy(
                    showUploadActivityTypeSheet = false,
                    uploadInitialPage = 0,
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
        if (currentState.isEditMode) {
            setState { copy(showUploadActivityTypeSheet = false, showPostConfirmSheet = true) }
        } else {
            setState { copy(showUploadActivityTypeSheet = false, showLocationSheet = true) }
        }
    }

    private fun onHideLocationSheet() {
        setState { copy(showLocationSheet = false) }
    }

    private fun onBackFromLocationSheet() {
        if (currentState.isEditMode) {
            setState { copy(showLocationSheet = false, showPostConfirmSheet = true) }
        } else {
            setState { copy(showLocationSheet = false, showUploadActivityTypeSheet = true) }
        }
    }

    private fun onUpdateUploadLocation(location: String) {
        setState { copy(uploadData = uploadData.copy(location = location)) }
    }

    private fun onSkipLocation() {
        setState {
            copy(
                showLocationSheet = false,
                showPostConfirmSheet = true,
                uploadData = uploadData.copy(
                    location = "",
                    lat = 0.0,
                    lng = 0.0
                )
            )
        }
    }

    private fun onConfirmLocation() {
        setState {
            copy(
                showLocationSheet = false,
                showPostConfirmSheet = false,
                showSearchScreen = true,
                searchText = "",
                searchResults = searchDummyList, // TODO: 검색 API 연결 시 교체
            )
        }
    }

    private fun onHideSearchScreen() {
        setState {
            copy(
                showSearchScreen = false,
                showLocationSheet = true,
                searchText = "",
                selectedSearchResult = null,
            )
        }
    }

    private fun onUpdateSearchText(text: String) {
        setState { copy(searchText = text) }
    }

    private fun onSelectSearchResult(item: SearchResultItemState) {
        setState { copy(selectedSearchResult = if (selectedSearchResult == item) null else item) }
    }

    private fun onConfirmSearchResult() {
        val selected = currentState.selectedSearchResult ?: return
        setState {
            copy(
                uploadData = uploadData.copy(
                    location = selected.name,
                    lat = selected.lat,
                    lng = selected.lng,
                ),
                selectedSearchResult = null,
                showSearchScreen = false,
                showPostConfirmSheet = true,
            )
        }
    }

    private fun onPostConfirmBack() {
        if (currentState.isEditMode) {
            setState { copy(showPostConfirmSheet = false, isEditMode = false, uploadData = UploadTravelData()) }
        } else {
            setState { copy(showPostConfirmSheet = false, showLocationSheet = true) }
        }
    }

    private fun onPostConfirmClose() {
        setState {
            copy(
                showPostConfirmSheet = false,
                showLocationSheet = false,
                isEditMode = false,
                uploadData = UploadTravelData(),
            )
        }
    }

    private fun onPostConfirmComplete() {
        // TODO: 업로드 API 호출
        val data = currentState.uploadData
        val previewItem = TravelItemState(
            id = -1,
            title = data.title,
            content = data.content,
            activityType = data.activityType,
            meetingDate = data.schedule,
            dayDiff = 0,
            location = data.location,
            maxParticipants = data.maxParticipants,
            currentParticipants = 1,
            ageCondition = data.ageCondition,
            genderCondition = data.genderCondition,
            authorName = "나",
            lat = data.lat,
            lng = data.lng,
            address = data.location,
        )
        setState {
            copy(
                showPostConfirmSheet = false,
                showTravelPostCard = true,
                selectedTravelItem = previewItem,
                uploadData = UploadTravelData(),
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

    private fun onShowJoinChatDialog(travelId: Int) {
        setState { copy(showJoinChatDialog = true, pendingChatTravelId = travelId) }
    }

    private fun onHideJoinChatDialog() {
        setState { copy(showJoinChatDialog = false, pendingChatTravelId = null) }
    }

    private fun onConfirmJoinChat() {
        val chatTravelId = currentState.pendingChatTravelId ?: return
        setState { copy(showJoinChatDialog = false, pendingChatTravelId = null) }
        postSideEffect(TravelSideEffect.NavigateToChatRoom(chatTravelId))
    }

    private fun onShowTravelPostCard(travelId: Int) {
        val item = currentState.travelItems.find { it.id == travelId } ?: return
        setState { copy(showTravelPostCard = true, selectedTravelItem = item) }
    }

    private fun onHideTravelPostCard() {
        setState { copy(showTravelPostCard = false, selectedTravelItem = null) }
    }

    private fun onConfirmDelete() {
        setState {
            copy(
                showDeleteConfirmDialog = false,
                showTravelPostCard = false,
                selectedTravelItem = null,
            )
        }
        postSideEffect(TravelSideEffect.ShowDeletedSnackbar)
        // TODO: 삭제 API 호출
    }

    private fun onShowEditMode() {
        val item = currentState.selectedTravelItem ?: return
        setState {
            copy(
                showOptionSheet = false,
                isEditMode = true,
                uploadData = UploadTravelData(
                    title = item.title,
                    content = item.content,
                    activityType = item.activityType,
                    maxParticipants = item.maxParticipants,
                    ageCondition = item.ageCondition,
                    genderCondition = item.genderCondition,
                    location = item.location,
                    lat = item.lat,
                    lng = item.lng,
                    schedule = item.meetingDate,
                ),
                showPostConfirmSheet = true,
            )
        }
    }

    private fun onConfirmEdit() {
        val item = currentState.selectedTravelItem ?: return
        val data = currentState.uploadData
        val updatedItem = item.copy(
            title = data.title,
            content = data.content,
            activityType = data.activityType,
            maxParticipants = data.maxParticipants,
            ageCondition = data.ageCondition,
            genderCondition = data.genderCondition,
            location = data.location,
            lat = data.lat,
            lng = data.lng,
            address = data.location,
            meetingDate = data.schedule,
        )
        setState {
            copy(
                showPostConfirmSheet = false,
                isEditMode = false,
                uploadData = UploadTravelData(),
                selectedTravelItem = updatedItem,
                travelItems = travelItems.map { if (it.id == item.id) updatedItem else it },
            )
        }
        // TODO: 수정 API 호출
    }
}
