package com.multissue.wit.designsystem.component.chip

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.multissue.wit.designsystem.util.noRippleClickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun WitFilterChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
    onClear: (() -> Unit)? = null,
    paddingHorizontal: Dp = 12.dp,
    paddingVertical: Dp = 10.dp,
    style: TextStyle = WitTheme.typography.bodyM,
    containerColor: Color = WitTheme.colors.containerColor,
    labelColor: Color = WitTheme.colors.grayText,
    selectedContainerColor: Color = WitTheme.colors.primaryLighter,
    selectedLabelColor: Color = WitTheme.colors.primaryDark,
    borderColor: Color = WitTheme.colors.border,
    selectedBorderColor: Color = WitTheme.colors.primaryDark,
    shape: Shape = RoundedCornerShape(50),
) {
    Surface(
        selected = isSelected,
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        color = if (isSelected) selectedContainerColor else containerColor,
        contentColor = if (isSelected) selectedLabelColor else labelColor,
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) selectedBorderColor else borderColor
        )
    ) {
        Row(
            modifier = Modifier.padding(paddingHorizontal, paddingVertical),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = style
            )
            AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally(),
            ) {
                if (onClear != null) {
                    Icon(
                        modifier = Modifier
                            .size(style.fontSize.value.dp)
                            .noRippleClickable { onClear() }
                            .padding(start = 4.dp),
                        painter = painterResource(R.drawable.icon_close_chip),
                        contentDescription = "Remove Selected Chip Item",
                        tint = if (isSelected) selectedLabelColor else labelColor,
                    )
                }
            }
        }
    }
}

@Composable
fun WitSelectableChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
    paddingHorizontal: Dp = 12.dp,
    paddingVertical: Dp = 10.dp,
    style: TextStyle = WitTheme.typography.bodyM,
    containerColor: Color = WitTheme.colors.containerColor,
    labelColor: Color = WitTheme.colors.grayText,
    selectedContainerColor: Color = WitTheme.colors.primaryLighter,
    selectedLabelColor: Color = WitTheme.colors.primaryDark,
    borderColor: Color = WitTheme.colors.border,
    selectedBorderColor: Color = WitTheme.colors.primaryDark,
    shape: Shape = RoundedCornerShape(50),
) {
    Surface(
        selected = isSelected,
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        color = if (isSelected) selectedContainerColor else containerColor,
        contentColor = if (isSelected) selectedLabelColor else labelColor,
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) selectedBorderColor else borderColor
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = paddingHorizontal, vertical = paddingVertical),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = style
            )
        }
    }
}

@Preview
@Composable
private fun WitFilterChipPreview() {
    var isSelected by remember { mutableStateOf(false) }
    WitTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WitFilterChip(
                isSelected = true,
                text = "전시/미술관",
                onClick = { }
            )

            WitFilterChip(
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
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WitSelectableChip(
                isSelected = isSelected,
                text = "20대",
                onClick = { isSelected = !isSelected }
            )
            
            WitSelectableChip(
                isSelected = true,
                text = "20대",
                onClick = { }
            )
        }
    }
}
