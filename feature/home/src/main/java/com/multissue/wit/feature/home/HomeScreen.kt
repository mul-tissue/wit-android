package com.multissue.wit.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.home.component.CountrySelectionSheetContent
import com.multissue.wit.feature.home.component.HomeFilterSearchRow
import com.multissue.wit.feature.home.component.TravelerBanner
import com.multissue.wit.feature.home.state.HomeSideEffect
import com.multissue.wit.feature.home.state.HomeUiIntent
import com.multissue.wit.feature.home.state.HomeUiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToMap: (Double?, Double?) -> Unit,
    navigateToMyPage: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.NavigateToMap -> navigateToMap(effect.latitude, effect.longitude)
            }
        }
    }

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        onProfileClick = navigateToMyPage,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onProfileClick: () -> Unit,
    onIntent: (HomeUiIntent) -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            confirmValueChange = { it != SheetValue.Hidden },
        ),
    )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 380.dp,
        sheetContainerColor = WitTheme.colors.background,
        sheetShadowElevation = 8.dp,
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle(
                modifier = Modifier.clickable(enabled = false) {},
                height = 2.dp,
                color = WitTheme.colors.disabledText,
            )
        },
        sheetContent = {
            CountrySelectionSheetContent(
                regions = uiState.regions,
                popularDestinations = uiState.popularDestinations,
                expandedCountryIds = uiState.expandedCountryIds,
                onIntent = onIntent,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WitTheme.colors.gradientBackground)
                .padding(paddingValues),
        ) {
            WitCenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    Image(
                        modifier = Modifier
                            .padding(start = 16.dp),
                        painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_appbar_logo),
                        contentDescription = stringResource(R.string.home_logo_description),
                    )
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_notification),
                            contentDescription = stringResource(R.string.home_notification),
                        )
                    }
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_profile),
                            contentDescription = stringResource(R.string.home_profile),
                        )
                    }
                },
            )

            HomeFilterSearchRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .padding(horizontal = 24.dp),
                type = uiState.selectedTab,
                onToggle = { onIntent(HomeUiIntent.SelectTab(it)) },
                searchText = uiState.searchText,
                onSearchTextChanged = { onIntent(HomeUiIntent.UpdateSearchText(it)) },
                onSearch = { onIntent(HomeUiIntent.Search(it)) },
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Image(
                    painter = painterResource(R.drawable.image_cloud),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 60.dp)
                        .size(62.dp)
                        .rotate(-10f)
                )
                Image(
                    painter = painterResource(R.drawable.image_water),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 30.dp, top = 100.dp)
                        .size(24.dp, 26.dp)
                )
                TravelerBanner(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    travelerCount = 45,
                )
            }
        }
    }
}

