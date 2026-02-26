package com.multissue.wit.feature.map.component.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.component.SpH

internal const val TITLE_MAX_LENGTH = 30
internal const val CONTENT_MAX_LENGTH = 250

@Composable
fun UploadPostWritePage(
    title: String,
    content: String,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = WitTheme.colors.text,
            backgroundColor = WitTheme.colors.text.copy(alpha = 0.4f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .verticalScroll(rememberScrollState())
        ) {
            // 제목 필드
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.upload_post_title_label),
                        style = WitTheme.typography.titleS,
                        color = WitTheme.colors.text,
                    )
                    Text(
                        text = stringResource(R.string.upload_char_count_format, title.length, TITLE_MAX_LENGTH),
                        style = WitTheme.typography.bodyM,
                        color = WitTheme.colors.disabledText,
                    )
                }
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = { if (it.length <= TITLE_MAX_LENGTH) onTitleChanged(it) },
                    textStyle = WitTheme.typography.bodyS,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    singleLine = true,
                    cursorBrush = SolidColor(WitTheme.colors.text),
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.icon_write),
                                contentDescription = "제목 글쓰기",
                                tint = if (title.isEmpty()) WitTheme.colors.disabledText else WitTheme.colors.primaryDark,
                                modifier = Modifier.size(16.dp),
                            )
                            Box {
                                if (title.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.upload_post_title_hint),
                                        style = WitTheme.typography.bodyS,
                                        color = WitTheme.colors.disabledText,
                                    )
                                }
                                innerTextField()
                            }
                        }
                    },
                )
            }
            SpH(48.dp)

            HorizontalDivider(color = WitTheme.colors.divider)

            SpH(18.dp)
            // 내용 필드
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.upload_post_content_label),
                        style = WitTheme.typography.titleS,
                        color = WitTheme.colors.text,
                    )
                    Text(
                        text = stringResource(R.string.upload_char_count_format, content.length, CONTENT_MAX_LENGTH),
                        style = WitTheme.typography.bodyM,
                        color = WitTheme.colors.disabledText,
                    )
                }
                BasicTextField(
                    value = content,
                    onValueChange = { if (it.length <= CONTENT_MAX_LENGTH) onContentChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = WitTheme.typography.bodyS,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    ),
                    cursorBrush = SolidColor(WitTheme.colors.text),
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.icon_write),
                                contentDescription = "내용 글쓰기",
                                tint = if (content.isEmpty()) WitTheme.colors.disabledText else WitTheme.colors.primaryDark,
                                modifier = Modifier.size(16.dp),
                            )
                            Box {
                                if (content.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.upload_post_content_hint),
                                        style = WitTheme.typography.bodyS,
                                        color = WitTheme.colors.disabledText,
                                    )
                                }
                                innerTextField()
                            }
                        }
                    },
                )
            }
        }
    }
}
