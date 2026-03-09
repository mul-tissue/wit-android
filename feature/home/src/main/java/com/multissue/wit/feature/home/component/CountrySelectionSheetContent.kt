package com.multissue.wit.feature.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.home.R
import com.multissue.wit.feature.home.state.HomeUiIntent
import com.multissue.wit.feature.home.state.PopularDestinationState
import com.multissue.wit.feature.home.state.RegionState

@Composable
fun CountrySelectionSheetContent(
    modifier: Modifier = Modifier,
    regions: List<RegionState>,
    popularDestinations: List<PopularDestinationState>,
    expandedCountryIds: Set<String>,
    onIntent: (HomeUiIntent) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        item {
            Text(
                text = stringResource(R.string.home_country_selection_title),
                style = WitTheme.typography.titleL,
                color = WitTheme.colors.text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = stringResource(R.string.home_popular_destinations),
                style = WitTheme.typography.titleM,
                color = WitTheme.colors.text,
            )
            Spacer(modifier = Modifier.height(12.dp))
            PopularDestinationGrid(
                modifier = Modifier.padding(horizontal = 24.dp),
                destinations = popularDestinations,
                onDestinationClick = { onIntent(HomeUiIntent.SelectPopularDestination(it.name)) },
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        regions.forEach { region ->
            item(key = "region_${region.id}") {
                RegionHeader(region = region)
            }

            region.countries.forEach { country ->
                item(key = "country_${country.id}") {
                    CountryDropdownRow(
                        modifier = Modifier
                            .background(WitTheme.colors.backgroundLighter)
                            .padding(horizontal = 24.dp),
                        country = country,
                        isExpanded = country.id in expandedCountryIds,
                        onToggle = { onIntent(HomeUiIntent.ToggleCountry(country.id)) },
                    )

                    AnimatedVisibility(
                        visible = country.id in expandedCountryIds,
                        enter = expandVertically(animationSpec = tween(300)),
                        exit = shrinkVertically(animationSpec = tween(300)),
                    ) {
                        Column {
                            country.cities.forEach { city ->
                                CityRow(
                                    city = city,
                                    onClick = { onIntent(HomeUiIntent.SelectCity(city.id)) },
                                )
                            }
                        }
                    }
                }
            }

            item(key = "spacer_${region.id}") {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}