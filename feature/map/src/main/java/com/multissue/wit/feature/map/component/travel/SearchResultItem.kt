package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.component.SpH
import com.multissue.wit.feature.map.state.travel.SearchResultItemState

@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    item: SearchResultItemState,
    isSelected: Boolean = false,
    onClick: (SearchResultItemState) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isSelected) WitTheme.colors.gray100 else WitTheme.colors.background)
                .noRippleClickable { onClick(item) }
                .padding(horizontal = 24.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_location),
                contentDescription = "위치",
                modifier = Modifier.size(24.dp),
                tint = WitTheme.colors.gray600
            )
            SpH(12.dp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = WitTheme.typography.titleS,
                    color = WitTheme.colors.text
                )
                SpH(2.dp)
                Text(
                    text = item.address,
                    style = WitTheme.typography.bodyXS,
                    color = WitTheme.colors.subText
                )
            }
        }
        HorizontalDivider(
            color = WitTheme.colors.gray100,
            thickness = 1.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultItemPreview() {
    WitTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SearchResultItem(
                item = SearchResultItemState(1, "도쿄 스카이트리", "일본 도쿄도 스미다구 오시아게 1-1-2"),
                onClick = {}
            )

            SearchResultItem(
                item = SearchResultItemState(1, "도쿄 스카이트리", "일본 도쿄도 스미다구 오시아게 1-1-2"),
                isSelected = true,
                onClick = {}
            )
        }
    }
}
