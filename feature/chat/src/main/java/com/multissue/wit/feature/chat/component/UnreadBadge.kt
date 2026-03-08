package com.multissue.wit.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme


@Composable
fun UnreadBadge(
    count: Int
) {
    Box(
        modifier = Modifier
            .defaultMinSize(minWidth = 18.dp, minHeight = 18.dp)
            .background(
                color = WitTheme.colors.primary,
                shape = RoundedCornerShape(50),
            )
            .padding(horizontal = 6.dp)
            .semantics {
                contentDescription = if (count > 99) "읽지 않은 메시지 99개 이상" else "읽지 않은 메시지 ${count}개"
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if (count > 99) "99+" else count.toString(),
            style = WitTheme.typography.bodyM,
            color = WitTheme.colors.white100,
        )
    }
}