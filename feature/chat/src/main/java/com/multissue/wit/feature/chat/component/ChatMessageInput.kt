package com.multissue.wit.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.chat.R

@Composable
fun ChatMessageInput(
    modifier: Modifier = Modifier,
    inputText: String,
    onInputChange: (String) -> Unit,
    onSendClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(WitTheme.colors.background)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .heightIn(max = 100.dp)
                .border(
                    width = 1.dp,
                    color = WitTheme.colors.divider,
                    shape = RoundedCornerShape(20.dp),
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            value = inputText,
            onValueChange = onInputChange,
            textStyle = WitTheme.typography.bodyS.copy(color = WitTheme.colors.text),
            decorationBox = { innerTextField ->
                if (inputText.isEmpty()) {
                    Text(
                        text = stringResource(R.string.chat_room_message_placeholder),
                        style = WitTheme.typography.bodyS,
                        color = WitTheme.colors.disabledText,
                    )
                }
                innerTextField()
            },
        )

        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(40.dp)
                .background(
                    color = if (inputText.isNotEmpty()) WitTheme.colors.primaryDark
                    else WitTheme.colors.disabledButton,
                    shape = CircleShape,
                )
                .noRippleClickable { if (inputText.isNotEmpty()) onSendClick() },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_send),
                contentDescription = "전송",
                modifier = Modifier.size(20.dp),
                tint = WitTheme.colors.white100,
            )
        }
    }
}