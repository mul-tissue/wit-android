package com.multissue.wit.feature.map.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.searchbar.WitSearchBar
import com.multissue.wit.designsystem.component.selectable.WitSelectType
import com.multissue.wit.designsystem.component.selectable.WitTextSwitch
import com.multissue.wit.feature.map.R

@Composable
fun FilterSearchRow(
    modifier: Modifier = Modifier,
    type: WitSelectType,
    onToggle: (WitSelectType) -> Unit,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
    ) {
        WitTextSwitch(
            modifier = Modifier
                .fillMaxHeight()
                .width(118.dp),
            selected = type,
            leftText = stringResource(R.string.type_feed),
            rightText = stringResource(R.string.type_with),
            onLeftClick = { onToggle(WitSelectType.Feed) },
            onRightClick = { onToggle(WitSelectType.Travel) }
        )
        Spacer(modifier = Modifier.width(12.dp))
        WitSearchBar(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            value = searchText,
            placeholder = "어디로 떠날까요?",
            onValueChange = onSearchTextChanged,
            onSearch = {
                onSearch(searchText)
                focusManager.clearFocus()
            }
        )
    }
}