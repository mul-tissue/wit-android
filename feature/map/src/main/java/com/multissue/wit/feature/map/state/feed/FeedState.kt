package com.multissue.wit.feature.map.state.feed

import com.multissue.wit.core.domain.model.location.MyDistrict
import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

data class FeedUiState(
    val myDistrict: MyDistrict? = null,
    val isLoading: Boolean = false,
) : UiState

sealed class FeedUiIntent : UiIntent {
    data class LoadMyDistrict(val lat: Double, val lng: Double) : FeedUiIntent()
}

sealed interface FeedSideEffect : UiSideEffect
