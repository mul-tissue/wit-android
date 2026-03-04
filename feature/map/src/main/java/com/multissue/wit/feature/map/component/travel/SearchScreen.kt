package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.component.searchbar.WitSearchBar
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.addFocusCleaner
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.dummy.searchDummyList
import com.multissue.wit.feature.map.state.travel.SearchResultItemState

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    searchResults: List<SearchResultItemState>,
    selectedItem: SearchResultItemState?,
    onResultClick: (SearchResultItemState) -> Unit,
    onConfirmClick: () -> Unit,
    onBack: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusCleaner = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WitTheme.colors.background)
            .addFocusCleaner(focusCleaner)
            .noRippleClickable {
                keyboardController?.hide()
            }
    ) {
        WitCenterAlignedTopAppBar(
            title = "목적지 선택",
            navigationIcon = {
                IconButton(
                    onClick = {
                        focusCleaner.clearFocus()
                        keyboardController?.hide()
                        onBack()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_back),
                        contentDescription = "뒤로가기",
                        tint = WitTheme.colors.text
                    )
                }
            }
        )

        WitSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            value = searchText,
            placeholder = stringResource(R.string.search_location_placeholder),
            onValueChange = onSearchTextChanged,
            onSearch = {
                focusCleaner.clearFocus()
                keyboardController?.hide()
                onSearch(searchText)
            }
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(
                items = searchResults,
                key = { it.id }
            ) { item ->
                SearchResultItem(
                    item = item,
                    isSelected = item == selectedItem,
                    onClick = onResultClick
                )
            }
        }

        WitButton(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth()
                .height(52.dp),
            title = stringResource(R.string.travel_select_done),
            enabled = selectedItem != null,
            onClick = onConfirmClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    WitTheme {
        SearchScreen(
            searchText = "도쿄",
            onSearchTextChanged = {},
            onSearch = {},
            searchResults = searchDummyList,
            selectedItem = searchDummyList.firstOrNull(),
            onResultClick = {},
            onConfirmClick = {},
            onBack = {}
        )
    }
}
