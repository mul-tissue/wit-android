package com.multissue.wit.feature.map

import androidx.lifecycle.viewModelScope
import com.multissue.wit.core.domain.exception.WitException
import com.multissue.wit.core.domain.usecase.location.GetMyDistrictUseCase
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.map.state.feed.FeedSideEffect
import com.multissue.wit.feature.map.state.feed.FeedUiIntent
import com.multissue.wit.feature.map.state.feed.FeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getMyDistrictUseCase: GetMyDistrictUseCase,
) : BaseViewModel<FeedUiState, FeedSideEffect, FeedUiIntent>(
    initialState = FeedUiState()
) {
    override fun onIntent(intent: FeedUiIntent) {
        when (intent) {
            is FeedUiIntent.LoadMyDistrict -> loadMyDistrict(intent.lat, intent.lng)
        }
    }

    private fun loadMyDistrict(lat: Double, lng: Double) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            getMyDistrictUseCase(lat, lng)
                .onSuccess { district ->
                    setState { copy(myDistrict = district, isLoading = false) }
                }
                .onFailure { throwable ->
                    setState { copy(isLoading = false) }
                    when (throwable) {
                        is WitException.HttpException -> { /* TODO */ }
                        is WitException.NetworkException -> { /* TODO */ }
                        else -> { }
                    }
                }
        }
    }
}
