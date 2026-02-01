package com.multissue.wit.designsystem.component.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun WitUnderlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    cursorBrush: Brush = SolidColor(WitTheme.colors.text),
    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    underlineColor: Color = WitTheme.colors.disabledText,
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = WitTheme.colors.text,
            backgroundColor = WitTheme.colors.text.copy(alpha = 0.4f)
        )
    ) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        enabled = enabled,
                        singleLine = singleLine,
                        textStyle = WitTheme.typography.titleXL.copy(
                            color = WitTheme.colors.text
                        ),
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        cursorBrush = cursorBrush,
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (value.isEmpty()) {
                                Text(
                                    text = hint,
                                    style = WitTheme.typography.titleXL,
                                    color = WitTheme.colors.subText
                                )
                            }
                            innerTextField()
                        }
                    )
                }

                if (trailingIcon != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    trailingIcon()
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = underlineColor
            )
        }
    }
}

@Preview
@Composable
private fun WitUnderlinedTextFieldPreview() {
    var value by remember { mutableStateOf("") }
    WitTheme {
        WitUnderlinedTextField(
            value = value,
            hint = "아이디 입력 (2-12자)",
            onValueChange = { value = it },
        )
    }
}


@Preview
@Composable
private fun WitUnderlinedTextFieldWithLeadingIconPreview() {
    var value by remember { mutableStateOf("") }
    WitTheme {
        WitUnderlinedTextField(
            value = value,
            hint = "아이디 입력 (2-12자)",
            onValueChange = { value = it },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.icon_profile),
                    contentDescription = "",
                    tint = WitTheme.colors.subText
                )
            }
        )
    }
}

@Preview
@Composable
private fun WitUnderlinedTextFieldWithTrailingIconPreview() {
    var value by remember { mutableStateOf("") }
    WitTheme {
        WitUnderlinedTextField(
            value = value,
            hint = "아이디 입력 (2-12자)",
            onValueChange = { value = it },
            trailingIcon = {
                WitButton(
                    modifier = Modifier.width(80.dp),
                    title = "확인",
                    onClick = { }
                )
            }
        )
    }
}