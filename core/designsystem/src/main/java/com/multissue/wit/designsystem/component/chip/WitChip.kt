package com.multissue.wit.designsystem.component.chip

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun WitFilterChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
    colors: SelectableChipColors = FilterChipDefaults.filterChipColors(
        containerColor = WitTheme.colors.containerColor,
        labelColor = WitTheme.colors.grayText,
        iconColor = Color.Transparent,
        selectedContainerColor = WitTheme.colors.primaryLighter,
        selectedLabelColor = WitTheme.colors.primaryDark,
        selectedTrailingIconColor = WitTheme.colors.primaryDark,
    ),
    border: BorderStroke = FilterChipDefaults.filterChipBorder(
        enabled = true,
        selected = isSelected,
        borderWidth = 1.dp,
        borderColor = WitTheme.colors.border,
        selectedBorderColor = WitTheme.colors.primaryDark,
    ),
    shape: Shape = RoundedCornerShape(50),
) {
    FilterChip(
        modifier = modifier,
        selected = isSelected,
        colors = colors,
        border = border,
        shape = shape,
        onClick = onClick,
        label = {
            Text(text = text, style = WitTheme.typography.bodyS)
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally(),
            ) {
                Icon(
                    modifier = Modifier.size(8.dp),
                    painter = painterResource(R.drawable.icon_close_chip),
                    contentDescription = "Remove Selected Chip Item"
                )
            }
        },
    )
}

@Composable
fun WitSelectableChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
    colors: SelectableChipColors = FilterChipDefaults.filterChipColors(
        containerColor = WitTheme.colors.containerColor,
        labelColor = WitTheme.colors.grayText,
        selectedContainerColor = WitTheme.colors.primaryDark,
        selectedLabelColor = WitTheme.colors.buttonText,
    ),
    border: BorderStroke = FilterChipDefaults.filterChipBorder(
        enabled = true,
        selected = isSelected,
        borderWidth = 1.dp,
        borderColor = WitTheme.colors.border,
        selectedBorderColor = WitTheme.colors.primaryDark,
    ),
    shape: Shape = RoundedCornerShape(50),
) {
    FilterChip(
        modifier = modifier,
        selected = isSelected,
        colors = colors,
        border = border,
        shape = shape,
        onClick = onClick,
        label = {
            Text(text = text, style = WitTheme.typography.titleS)
        }
    )
}

@Preview
@Composable
private fun WitFilterChipPreview() {
    var isSelected by remember { mutableStateOf(false) }
    WitTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WitFilterChip(
                modifier = Modifier
                    .wrapContentSize(),
                isSelected = true,
                text = "전시/미술관",
                onClick = { }
            )

            WitFilterChip(
                modifier = Modifier
                    .wrapContentSize(),
                isSelected = isSelected,
                text = "전시/미술관",
                onClick = { isSelected = !isSelected }
            )
        }
    }
}

@Preview
@Composable
private fun WitSelectableChipPreview() {
    var isSelected by remember { mutableStateOf(false) }
    WitTheme {
        WitSelectableChip(
            modifier = Modifier
                .wrapContentSize(),
            isSelected = isSelected,
            text = "20대",
            onClick = { isSelected = !isSelected }
        )
    }
}

