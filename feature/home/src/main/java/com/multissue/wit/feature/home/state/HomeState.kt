package com.multissue.wit.feature.home.state

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState
import com.multissue.wit.designsystem.component.selectable.WitSelectType

data class HomeUiState(
    val selectedTab: WitSelectType = WitSelectType.Feed,
    val searchText: String = "",
    val expandedCountryIds: Set<String> = emptySet(),
    val regions: List<RegionState> = emptyList(),
    val popularDestinations: List<PopularDestinationState> = emptyList(),
) : UiState

data class RegionState(
    val id: String,
    val name: String,
    val emoji: String,
    val countries: List<CountryState>,
)

data class CountryState(
    val id: String,
    val name: String,
    val flagEmoji: String,
    val cities: List<CityState>,
)

data class CityState(
    val id: String,
    val name: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
)

data class PopularDestinationState(
    val flagEmoji: String,
    val name: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
)

sealed class HomeUiIntent : UiIntent {
    data class SelectTab(val type: WitSelectType) : HomeUiIntent()
    data class UpdateSearchText(val text: String) : HomeUiIntent()
    data class Search(val text: String) : HomeUiIntent()
    data class ToggleCountry(val countryId: String) : HomeUiIntent()
    data class SelectCity(val cityId: String) : HomeUiIntent()
    data class SelectPopularDestination(val name: String) : HomeUiIntent()
}

sealed interface HomeSideEffect : UiSideEffect {
    data class NavigateToMap(val latitude: Double? = null, val longitude: Double? = null) : HomeSideEffect
}

