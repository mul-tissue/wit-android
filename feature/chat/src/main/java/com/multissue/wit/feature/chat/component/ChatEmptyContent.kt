package com.multissue.wit.feature.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.chat.R

@Composable
fun ChatEmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WitTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(200.dp))

        Icon(
            modifier = Modifier.size(64.dp),
            painter = painterResource(R.drawable.icon_chat_empty),
            contentDescription = "채팅 아이콘",
            tint = WitTheme.colors.primaryLighter,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.chat_empty_title),
            style = WitTheme.typography.titleL,
            color = WitTheme.colors.subText,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.chat_empty_subtitle),
            style = WitTheme.typography.bodyS,
            color = WitTheme.colors.disabledText,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 36.dp)
                .offset(y = 64.dp),
            painter = painterResource(R.drawable.image_globe),
            contentDescription = "지구본"
        )
    }
}
