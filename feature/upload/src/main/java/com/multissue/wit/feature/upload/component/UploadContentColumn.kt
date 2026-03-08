package com.multissue.wit.feature.upload.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.component.textfield.WitNormalTextField
import com.multissue.wit.designsystem.component.textfield.WitUnderlinedTextField
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.upload.R

@Composable
fun UploadContentColumn(
    modifier: Modifier = Modifier,
    location: String,
    date: String,
    content: String,
    onContentValueChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        WitNormalTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "한 줄로 현재 있는 곳의 정보를 공유해요",
            value = content,
            onValueChange = onContentValueChanged,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.icon_write),
                    contentDescription = "",
                    tint = WitTheme.colors.primary
                )
            }
        )
        SpH(4.dp)
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.icon_location),
                    contentDescription = "위치 아이콘",
                    tint = WitTheme.colors.subText
                )
                Text(
                    text = location,
                    style = WitTheme.typography.bodyL,
                    color = WitTheme.colors.subText
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.icon_date),
                    contentDescription = "날짜 아이콘",
                    tint = WitTheme.colors.subText
                )
                Text(
                    text = date,
                    style = WitTheme.typography.bodyL,
                    color = WitTheme.colors.subText
                )
            }
        }
    }
}