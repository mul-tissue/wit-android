package com.multissue.wit.designsystem.component.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun WitSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = WitTheme.colors.text,
            backgroundColor = WitTheme.colors.text.copy(alpha = 0.4f)
        )
    ) {
        Row(
            modifier = modifier
                .wrapContentHeight()
                .clip(RoundedCornerShape(50))
                .background(
                    color = WitTheme.colors.background
                )
                .border(
                    width = 1.dp,
                    color = WitTheme.colors.disabledText,
                    shape = RoundedCornerShape(50)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(44.dp),
                painter = painterResource(R.drawable.icon_search),
                contentDescription = "Search",
                tint = WitTheme.colors.disabledText
            )
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearch() }
                ),
                textStyle = WitTheme.typography.titleS.copy(
                    color = WitTheme.colors.text
                ),
                cursorBrush = SolidColor(WitTheme.colors.text),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = WitTheme.typography.titleS,
                            color = WitTheme.colors.disabledText,
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Preview
@Composable
private fun WitSearchBarPreview() {
    var searchText by remember { mutableStateOf("") }
    WitTheme {
        WitSearchBar(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            placeholder = "어디로 떠날까요?",
            onValueChange = { searchText = it },
            onSearch = {}
        )
    }
}