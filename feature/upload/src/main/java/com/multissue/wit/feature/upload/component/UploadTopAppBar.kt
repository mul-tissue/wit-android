package com.multissue.wit.feature.upload.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar

@Composable
fun UploadTopAppBar(
    onBackButtonClicked: () -> Unit,
) {
    WitCenterAlignedTopAppBar(
        modifier = Modifier.padding(horizontal = 8.dp),
        title = "피드 작성하기",
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(
                    painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_back),
                    contentDescription = "navigationIconContentDescription"
                )
            }
        },
    )
}