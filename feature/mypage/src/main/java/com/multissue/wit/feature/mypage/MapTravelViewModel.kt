package com.multissue.wit.feature.mypage

import com.google.android.gms.maps.model.LatLng
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.core.ui.travel.state.AmPm
import com.multissue.wit.core.ui.travel.state.PickerHour
import com.multissue.wit.core.ui.travel.state.PickerMinute
import com.multissue.wit.core.ui.travel.state.SearchResultItemState
import com.multissue.wit.core.ui.travel.state.UploadTravelData
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelItem
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiIntent
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiSideEffect
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelUiState
import com.multissue.wit.feature.mypage.state.map.travel.toTravelItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MapTravelViewModel @Inject constructor() :
    BaseViewModel<MapTravelUiState, MapTravelUiSideEffect, MapTravelUiIntent>(MapTravelUiState()) {

    init {
        setState {
            copy(
                ageOptions = listOf("20대", "30대", "40대", "50대", "60대+", "무관"),
                genderOptions = listOf("남자", "여자", "무관"),
            )
        }
    }

    override fun onIntent(intent: MapTravelUiIntent) {
        when (intent) {
            is MapTravelUiIntent.LoadTravel -> {
                setState {
                    copy(
                        selectedCityName = intent.cityName,
                        travelList = travelListByCity(intent.cityName),
                    )
                }
            }
            is MapTravelUiIntent.ClickTravelItem -> {
                val item = currentState.travelList.find { it.id == intent.id } ?: return
                postSideEffect(MapTravelUiSideEffect.NavigateToLocation(item.latLng))
            }

            is MapTravelUiIntent.ShowTravelPostCard -> onShowTravelPostCard(intent.id)
            is MapTravelUiIntent.HideTravelPostCard -> onHideTravelPostCard()
            is MapTravelUiIntent.ShowOptionSheet -> setState { copy(showOptionSheet = true) }
            is MapTravelUiIntent.HideOptionSheet -> setState { copy(showOptionSheet = false) }
            is MapTravelUiIntent.ShowDeleteConfirmDialog -> setState { copy(showOptionSheet = false, showDeleteConfirmDialog = true) }
            is MapTravelUiIntent.HideDeleteConfirmDialog -> setState { copy(showDeleteConfirmDialog = false, showOptionSheet = true) }
            is MapTravelUiIntent.ConfirmDelete -> onConfirmDelete()
            is MapTravelUiIntent.ShowEditMode -> onShowEditMode()
            is MapTravelUiIntent.ConfirmEdit -> onConfirmEdit()
            is MapTravelUiIntent.EditTypeSection -> setState { copy(showUploadActivityTypeSheet = true, uploadInitialPage = 0) }
            is MapTravelUiIntent.EditScheduleSection -> setState { copy(showUploadDateSelectionSheet = true) }
            is MapTravelUiIntent.EditLocationSection -> setState { copy(showLocationSheet = true) }
            is MapTravelUiIntent.EditWriteSection -> setState { copy(showUploadActivityTypeSheet = true, uploadInitialPage = 2) }
            is MapTravelUiIntent.HideUploadActivityTypeSheet -> onHideUploadActivityTypeSheet()
            is MapTravelUiIntent.SelectUploadActivityType -> setState { copy(uploadData = uploadData.copy(activityType = intent.activityType)) }
            is MapTravelUiIntent.SelectUploadParticipants -> setState { copy(uploadData = uploadData.copy(maxParticipants = intent.count)) }
            is MapTravelUiIntent.SelectUploadAge -> setState { copy(uploadData = uploadData.copy(ageCondition = intent.age)) }
            is MapTravelUiIntent.SelectUploadGender -> setState { copy(uploadData = uploadData.copy(genderCondition = intent.gender)) }
            is MapTravelUiIntent.ResetUploadConditions -> setState { copy(uploadData = uploadData.copy(maxParticipants = 0, ageCondition = "", genderCondition = "")) }
            is MapTravelUiIntent.UpdateUploadTitle -> setState { copy(uploadData = uploadData.copy(title = intent.title)) }
            is MapTravelUiIntent.UpdateUploadContent -> setState { copy(uploadData = uploadData.copy(content = intent.content)) }
            is MapTravelUiIntent.ConfirmUpload -> onConfirmUpload()
            is MapTravelUiIntent.ShowUploadDateSelectionSheet -> setState { copy(showUploadDateSelectionSheet = true) }
            is MapTravelUiIntent.HideUploadDateSelectionSheet -> onHideUploadDateSelectionSheet()
            is MapTravelUiIntent.DraftSelectUploadDate -> onDraftSelectUploadDate(intent.date)
            is MapTravelUiIntent.DraftResetUploadDate -> setState { copy(draftUploadDate = null) }
            is MapTravelUiIntent.ConfirmUploadDate -> onConfirmUploadDate()
            is MapTravelUiIntent.ShowUploadCalendarDialog -> onShowUploadCalendarDialog()
            is MapTravelUiIntent.HideUploadCalendarDialog -> onHideUploadCalendarDialog()
            is MapTravelUiIntent.HideSelectTimeDialog -> setState { copy(showSelectTimeDialog = false) }
            is MapTravelUiIntent.ConfirmTime -> onConfirmTime(intent.amPm, intent.hour, intent.minute)
            is MapTravelUiIntent.ConfirmTimeUndecided -> onConfirmTimeUndecided()
            is MapTravelUiIntent.HideLocationSheet -> setState { copy(showLocationSheet = false) }
            is MapTravelUiIntent.BackFromLocationSheet -> onBackFromLocationSheet()
            is MapTravelUiIntent.SkipLocation -> onSkipLocation()
            is MapTravelUiIntent.ConfirmLocation -> onConfirmLocation()
            is MapTravelUiIntent.HideSearchScreen -> onHideSearchScreen()
            is MapTravelUiIntent.UpdateSearchText -> setState { copy(searchText = intent.text) }
            is MapTravelUiIntent.SelectSearchResult -> onSelectSearchResult(intent.item)
            is MapTravelUiIntent.ConfirmSearchResult -> onConfirmSearchResult()
            is MapTravelUiIntent.PostConfirmBack -> onPostConfirmBack()
            is MapTravelUiIntent.PostConfirmClose -> onPostConfirmClose()
        }
    }

    private fun onShowTravelPostCard(id: Int) {
        val item = currentState.travelList.find { it.id == id } ?: return
        setState { copy(showTravelPostCard = true, selectedTravelItem = item.toTravelItemState()) }
    }

    private fun onHideTravelPostCard() {
        setState { copy(showTravelPostCard = false, selectedTravelItem = null) }
    }

    private fun onConfirmDelete() {
        val selectedItem = currentState.selectedTravelItem ?: return
        setState {
            copy(
                showDeleteConfirmDialog = false,
                showTravelPostCard = false,
                selectedTravelItem = null,
                travelList = travelList.filter { it.id != selectedItem.id },
            )
        }
        postSideEffect(MapTravelUiSideEffect.ShowDeletedSnackbar)
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
                travelList = travelList.map { mapItem ->
                    if (mapItem.id == item.id) {
                        mapItem.copy(
                            title = data.title,
                            content = data.content,
                            activityType = data.activityType,
                            maxParticipants = data.maxParticipants,
                            ageCondition = data.ageCondition,
                            genderCondition = data.genderCondition,
                            location = data.location,
                            address = data.location,
                            meetingDate = data.schedule,
                        )
                    } else mapItem
                },
            )
        }
        // TODO: 수정 API 호출
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

    private fun onConfirmUpload() {
        if (currentState.isEditMode) {
            setState { copy(showUploadActivityTypeSheet = false, showPostConfirmSheet = true) }
        } else {
            setState { copy(showUploadActivityTypeSheet = false, showLocationSheet = true) }
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

    private fun onConfirmUploadDate() {
        setState {
            copy(
                uploadData = uploadData.copy(meetingDate = draftUploadDate),
                showUploadCalendarDialog = false,
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

    private fun onConfirmTime(amPm: AmPm, hour: PickerHour, minute: PickerMinute) {
        setState {
            val schedule = buildSchedule(uploadData.meetingDate, false, amPm, hour, minute)
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
            val schedule = buildSchedule(uploadData.meetingDate, true)
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
        val timeStr = if (isTimeUndecided) "미정" else "${hour.displayText}:${minute.displayText} ${amPm.name}"
        return "$dateStr · $timeStr"
    }

    private fun onBackFromLocationSheet() {
        if (currentState.isEditMode) {
            setState { copy(showLocationSheet = false, showPostConfirmSheet = true) }
        } else {
            setState { copy(showLocationSheet = false, showUploadActivityTypeSheet = true) }
        }
    }

    private fun onSkipLocation() {
        setState {
            copy(
                showLocationSheet = false,
                showPostConfirmSheet = true,
                uploadData = uploadData.copy(location = "", lat = 0.0, lng = 0.0),
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
                searchResults = searchDummyList,
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

    private fun travelListByCity(cityName: String): List<MapTravelItem> = when (cityName) {
        "파리" -> listOf(
            MapTravelItem(
                id = 1,
                title = "에펠탑 같이 가요",
                description = "20대 / 성별 무관",
                content = "에펠탑 앞에서 인생샷 찍어드림\n같이 야경도 보러가요!",
                activityType = "관광",
                meetingDate = "3월 15일",
                dayDiff = 10,
                location = "에펠탑",
                maxParticipants = 4,
                currentParticipants = 2,
                companionThumbnails = listOf("https://picsum.photos/id/64/100/100"),
                latLng = LatLng(48.8584, 2.2945),
                ageCondition = "20대",
                genderCondition = "무관",
                authorName = "여행좋아",
                address = "프랑스 파리 에펠탑",
            ),
            MapTravelItem(
                id = 2,
                title = "루브르 미술관 투어",
                description = "30대 / 여자",
                content = "루브르 미술관에서 모나리자도 보고\n천천히 감상하면서 돌아봐요",
                activityType = "전시/미술관",
                meetingDate = "3월 18일 · 09:00 AM",
                dayDiff = 13,
                location = "루브르 미술관",
                maxParticipants = 3,
                currentParticipants = 1,
                companionThumbnails = listOf("https://picsum.photos/id/65/100/100"),
                latLng = LatLng(48.8606, 2.3376),
                ageCondition = "30대",
                genderCondition = "여자",
                authorName = "아트러버",
                address = "프랑스 파리 루브르 미술관",
            ),
            MapTravelItem(
                id = 3,
                title = "몽마르트르 카페 투어",
                description = "무관 / 무관",
                content = "몽마르트르 언덕에서 커피 마시면서\n파리 시내 전망 감상해요",
                activityType = "카페/맛집",
                meetingDate = "3월 20일",
                dayDiff = 15,
                location = "몽마르트르",
                maxParticipants = 5,
                currentParticipants = 3,
                companionThumbnails = listOf(
                    "https://picsum.photos/id/70/100/100",
                    "https://picsum.photos/id/71/100/100",
                ),
                latLng = LatLng(48.8867, 2.3431),
                ageCondition = "무관",
                genderCondition = "무관",
                authorName = "카페매니아",
                address = "프랑스 파리 몽마르트르",
            ),
            MapTravelItem(
                id = 4,
                title = "센강 야경 산책",
                description = "20대 / 무관",
                content = "센강 따라 걸으면서 야경 구경해요\n퐁네프 다리에서 사진도 찍어요!",
                activityType = "산책",
                meetingDate = "3월 22일",
                dayDiff = 17,
                location = "센강",
                maxParticipants = 4,
                currentParticipants = 2,
                companionThumbnails = listOf("https://picsum.photos/id/72/100/100"),
                latLng = LatLng(48.8566, 2.3425),
                ageCondition = "20대",
                genderCondition = "무관",
                authorName = "야경좋아",
                address = "프랑스 파리 센강",
            ),
        )
        "런던" -> listOf(
            MapTravelItem(
                id = 10,
                title = "빅벤 야경 투어",
                description = "30대 / 무관",
                content = "야경 보면서 산책해요\n템즈강 따라 걸으면서 이야기 나눠요",
                activityType = "산책",
                meetingDate = "3월 20일",
                dayDiff = 15,
                location = "빅벤",
                maxParticipants = 2,
                currentParticipants = 1,
                companionThumbnails = listOf("https://picsum.photos/id/66/100/100"),
                latLng = LatLng(51.5007, -0.1246),
                ageCondition = "30대",
                genderCondition = "무관",
                authorName = "런던러버",
                address = "영국 런던 빅벤",
            ),
            MapTravelItem(
                id = 11,
                title = "대영박물관 함께 관람",
                description = "무관 / 무관",
                content = "대영박물관 같이 돌아봐요\n이집트관이랑 그리스관 위주로!",
                activityType = "전시/미술관",
                meetingDate = "3월 22일",
                dayDiff = 17,
                location = "대영박물관",
                maxParticipants = 4,
                currentParticipants = 2,
                companionThumbnails = listOf(
                    "https://picsum.photos/id/73/100/100",
                ),
                latLng = LatLng(51.5194, -0.1270),
                ageCondition = "무관",
                genderCondition = "무관",
                authorName = "역사덕후",
                address = "영국 런던 대영박물관",
            ),
            MapTravelItem(
                id = 12,
                title = "하이드 파크 피크닉",
                description = "20대 / 여자",
                content = "하이드 파크에서 피크닉해요\n간단한 샌드위치랑 커피 가져갈게요",
                activityType = "산책",
                meetingDate = "3월 25일",
                dayDiff = 20,
                location = "하이드 파크",
                maxParticipants = 3,
                currentParticipants = 1,
                companionThumbnails = listOf("https://picsum.photos/id/74/100/100"),
                latLng = LatLng(51.5073, -0.1657),
                ageCondition = "20대",
                genderCondition = "여자",
                authorName = "피크닉좋아",
                address = "영국 런던 하이드 파크",
            ),
        )
        "바르셀로나" -> listOf(
            MapTravelItem(
                id = 20,
                title = "사그라다 파밀리아",
                description = "무관 / 무관",
                content = "가우디 투어 하실 분\n사그라다 파밀리아 + 구엘공원 같이 가요",
                activityType = "관광",
                meetingDate = "4월 5일",
                dayDiff = 30,
                location = "사그라다 파밀리아",
                maxParticipants = 6,
                currentParticipants = 3,
                companionThumbnails = listOf("https://picsum.photos/id/67/100/100"),
                latLng = LatLng(41.4036, 2.1744),
                ageCondition = "무관",
                genderCondition = "무관",
                authorName = "가우디팬",
                address = "스페인 바르셀로나 사그라다 파밀리아",
            ),
            MapTravelItem(
                id = 21,
                title = "람블라스 거리 맛집 투어",
                description = "20대 / 무관",
                content = "람블라스 거리에서 타파스 맛집 탐방해요\n보케리아 시장도 갈 예정!",
                activityType = "카페/맛집",
                meetingDate = "4월 7일",
                dayDiff = 32,
                location = "람블라스 거리",
                maxParticipants = 4,
                currentParticipants = 1,
                companionThumbnails = listOf("https://picsum.photos/id/75/100/100"),
                latLng = LatLng(41.3809, 2.1735),
                ageCondition = "20대",
                genderCondition = "무관",
                authorName = "맛집탐험가",
                address = "스페인 바르셀로나 람블라스 거리",
            ),
            MapTravelItem(
                id = 22,
                title = "바르셀로네타 해변",
                description = "20대 / 남자",
                content = "바르셀로네타 해변에서 서핑 같이 해요\n초보도 환영!",
                activityType = "액티비티",
                meetingDate = "4월 10일",
                dayDiff = 35,
                location = "바르셀로네타 해변",
                maxParticipants = 3,
                currentParticipants = 2,
                companionThumbnails = listOf(
                    "https://picsum.photos/id/76/100/100",
                ),
                latLng = LatLng(41.3784, 2.1925),
                ageCondition = "20대",
                genderCondition = "남자",
                authorName = "서핑보이",
                address = "스페인 바르셀로나 바르셀로네타 해변",
            ),
        )
        "로마" -> listOf(
            MapTravelItem(
                id = 30,
                title = "콜로세움 정복",
                description = "20대 / 남자",
                content = "로마 역사 탐방\n콜로세움 + 포로 로마노 같이 돌아봐요",
                activityType = "관광",
                meetingDate = "4월 10일",
                dayDiff = 35,
                location = "콜로세움",
                maxParticipants = 4,
                currentParticipants = 1,
                companionThumbnails = listOf("https://picsum.photos/id/68/100/100"),
                latLng = LatLng(41.8902, 12.4922),
                ageCondition = "20대",
                genderCondition = "남자",
                authorName = "로마탐험가",
                address = "이탈리아 로마 콜로세움",
            ),
            MapTravelItem(
                id = 31,
                title = "바티칸 미술관 같이 가요",
                description = "무관 / 무관",
                content = "바티칸 미술관 + 시스티나 성당\n오전에 일찍 가서 줄 안 서고 들어가요",
                activityType = "전시/미술관",
                meetingDate = "4월 12일",
                dayDiff = 37,
                location = "바티칸 미술관",
                maxParticipants = 3,
                currentParticipants = 2,
                companionThumbnails = listOf(
                    "https://picsum.photos/id/77/100/100",
                ),
                latLng = LatLng(41.9065, 12.4536),
                ageCondition = "무관",
                genderCondition = "무관",
                authorName = "예술사랑",
                address = "이탈리아 로마 바티칸 미술관",
            ),
            MapTravelItem(
                id = 32,
                title = "트레비 분수 야경 산책",
                description = "20대 / 여자",
                content = "트레비 분수에서 동전 던지고\n스페인 광장까지 야경 산책해요",
                activityType = "산책",
                meetingDate = "4월 14일",
                dayDiff = 39,
                location = "트레비 분수",
                maxParticipants = 2,
                currentParticipants = 1,
                companionThumbnails = listOf("https://picsum.photos/id/78/100/100"),
                latLng = LatLng(41.9009, 12.4833),
                ageCondition = "20대",
                genderCondition = "여자",
                authorName = "로마걸",
                address = "이탈리아 로마 트레비 분수",
            ),
        )
        else -> emptyList()
    }

    private val searchDummyList = listOf(
        SearchResultItemState(1, "에펠탑", "프랑스 파리 샹 드 마르스", lat = 48.8584, lng = 2.2945),
        SearchResultItemState(2, "루브르 미술관", "프랑스 파리 리볼리 거리", lat = 48.8606, lng = 2.3376),
        SearchResultItemState(3, "개선문", "프랑스 파리 샹젤리제", lat = 48.8738, lng = 2.2950),
        SearchResultItemState(4, "몽마르트르 언덕", "프랑스 파리 몽마르트르", lat = 48.8867, lng = 2.3431),
        SearchResultItemState(5, "노트르담 대성당", "프랑스 파리 시테섬", lat = 48.8530, lng = 2.3499),
    )
}
