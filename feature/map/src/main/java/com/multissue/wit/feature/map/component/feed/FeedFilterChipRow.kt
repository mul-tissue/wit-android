package com.multissue.wit.feature.map.component.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.chip.WitSelectableChip
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.state.FeedFilterType

@Composable
fun FeedFilterChipRow(
    modifier: Modifier = Modifier,
    filterType: FeedFilterType,
    onFilterClicked: (FeedFilterType) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WitSelectableChip(
            modifier = Modifier
                .fillMaxHeight(),
            isSelected = filterType == FeedFilterType.POPULAR,
            text = "인기 명소", // TODO String
            onClick = { onFilterClicked(FeedFilterType.POPULAR) }
        )
        WitSelectableChip(
            modifier = Modifier
                .fillMaxHeight(),
            isSelected = filterType == FeedFilterType.LIVE,
            text = "실시간", // TODO String
            onClick = { onFilterClicked(FeedFilterType.LIVE) }
        )
    }
}