package com.multissue.wit.feature.home

import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.home.dummy.popularDestinationDummy
import com.multissue.wit.feature.home.dummy.regionDummyData
import com.multissue.wit.feature.home.state.HomeSideEffect
import com.multissue.wit.feature.home.state.HomeUiIntent
import com.multissue.wit.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeUiState, HomeSideEffect, HomeUiIntent>(
    HomeUiState(
        regions = regionDummyData,
        popularDestinations = popularDestinationDummy,
    )
) {
    override fun onIntent(intent: HomeUiIntent) {
        when (intent) {
            is HomeUiIntent.SelectTab -> setState { copy(selectedTab = intent.type) }
            is HomeUiIntent.UpdateSearchText -> setState { copy(searchText = intent.text) }
            is HomeUiIntent.Search -> { /* TODO: API */ }
            is HomeUiIntent.ToggleCountry -> {
                setState {
                    val newSet = expandedCountryIds.toMutableSet()
                    if (intent.countryId in newSet) newSet.remove(intent.countryId)
                    else newSet.add(intent.countryId)
                    copy(expandedCountryIds = newSet)
                }
            }

            is HomeUiIntent.SelectCity -> {
                val city = uiState.value.regions.flatMap { it.countries }.flatMap { it.cities }
                    .find { it.id == intent.cityId }
                postSideEffect(HomeSideEffect.NavigateToMap(city?.latitude, city?.longitude))
            }

            is HomeUiIntent.SelectPopularDestination -> {
                val destination = uiState.value.popularDestinations.find { it.name == intent.name }
                postSideEffect(HomeSideEffect.NavigateToMap(destination?.latitude, destination?.longitude))
            }
        }
    }
}
