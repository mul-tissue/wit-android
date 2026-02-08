package com.multissue.wit.feature.map.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.chip.WitSelectableChip
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
) {
    var isPopularSelectable by remember { mutableStateOf(false) }
    var isLive by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
           modifier = Modifier
               .height(44.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WitSelectableChip(
                modifier = Modifier
                    .fillMaxHeight(),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = WitTheme.colors.containerColor,
                    labelColor = WitTheme.colors.grayText,
                    selectedContainerColor = WitTheme.colors.primaryLighter,
                    selectedLabelColor = WitTheme.colors.primaryDark,
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isPopularSelectable,
                    borderWidth = 1.dp,
                    borderColor = WitTheme.colors.border,
                    selectedBorderColor = WitTheme.colors.primaryDark,
                ),
                isSelected = isPopularSelectable,
                text = "인기 명소",
                onClick = { isPopularSelectable = !isPopularSelectable }
            )
            WitSelectableChip(
                modifier = Modifier
                    .fillMaxHeight(),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = WitTheme.colors.containerColor,
                    labelColor = WitTheme.colors.grayText,
                    selectedContainerColor = WitTheme.colors.primaryLighter,
                    selectedLabelColor = WitTheme.colors.primaryDark,
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isLive,
                    borderWidth = 1.dp,
                    borderColor = WitTheme.colors.border,
                    selectedBorderColor = WitTheme.colors.primaryDark,
                ),
                isSelected = isLive,
                text = "실시간",
                onClick = { isLive = !isLive }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("XX 핫플 모아보기🔥", style = WitTheme.typography.titleL)
        Spacer(modifier = Modifier.height(400.dp))
    }
}