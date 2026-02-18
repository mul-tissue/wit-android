package com.multissue.wit.designsystem.component.selectable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable

@Composable
fun FilledWitSelectableBox(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    shape: Shape = RoundedCornerShape(8.dp),
    onSelectChanged: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                color = if (isSelected) WitTheme.colors.primaryDark else WitTheme.colors.disabledButton
            )
            .noRippleClickable { onSelectChanged() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = WitTheme.typography.titleS,
            color = if (isSelected) WitTheme.colors.buttonText else WitTheme.colors.disabledText
        )
    }
}

@Composable
fun OutlinedWitSelectableBox(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    shape: Shape = RoundedCornerShape(10.dp),
    onSelectChanged: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                color = WitTheme.colors.background
            )
            .border(
                width = 1.5.dp,
                color = if (isSelected) WitTheme.colors.primaryDark else WitTheme.colors.divider,
                shape = shape
            )
            .noRippleClickable { onSelectChanged() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = WitTheme.typography.titleM,
            color = WitTheme.colors.subText
        )
    }
}

@Preview
@Composable
fun FilledSelectableBoxPreview() {
    var isSelected by remember { mutableStateOf(false) }
    WitTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FilledWitSelectableBox(
                modifier = Modifier
                    .width(164.dp)
                    .height(64.dp),
                title = "Filled",
                isSelected = isSelected,
                onSelectChanged = { isSelected = !isSelected }
            )
            FilledWitSelectableBox(
                modifier = Modifier
                    .width(164.dp)
                    .height(64.dp),
                title = "Filled true",
                isSelected = true,
                onSelectChanged = { }
            )
        }
    }
}

@Preview
@Composable
fun OutlinedSelectableBoxPreview() {
    var isSelected by remember { mutableStateOf(false) }
    WitTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedWitSelectableBox(
                modifier = Modifier
                    .width(164.dp)
                    .height(64.dp),
                title = "Outlined",
                isSelected = isSelected,
                onSelectChanged = { isSelected = !isSelected }
            )
            OutlinedWitSelectableBox(
                modifier = Modifier
                    .width(164.dp)
                    .height(64.dp),
                title = "Outlined true",
                isSelected = true,
                onSelectChanged = { }
            )
        }
    }
}