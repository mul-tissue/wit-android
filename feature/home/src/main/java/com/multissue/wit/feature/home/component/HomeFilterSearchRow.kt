package com.multissue.wit.feature.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.searchbar.WitSearchBar
import com.multissue.wit.designsystem.component.selectable.WitSelectType
import com.multissue.wit.designsystem.component.selectable.WitTextSwitch
import com.multissue.wit.feature.home.R

@Composable
fun HomeFilterSearchRow(
    modifier: Modifier = Modifier,
    type: WitSelectType,
    onToggle: (WitSelectType) -> Unit,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
    ) {
        WitTextSwitch(
            modifier = Modifier
                .fillMaxHeight()
                .width(118.dp),
            selected = type,
            leftText = stringResource(R.string.home_tab_feed),
            rightText = stringResource(R.string.home_tab_travel),
            onLeftClick = { onToggle(WitSelectType.Feed) },
            onRightClick = { onToggle(WitSelectType.Travel) },
        )
        Spacer(modifier = Modifier.width(12.dp))
        WitSearchBar(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            value = searchText,
            placeholder = stringResource(R.string.home_search_placeholder),
            onValueChange = onSearchTextChanged,
            onSearch = {
                onSearch(searchText)
                focusManager.clearFocus()
            },
        )
    }
}
