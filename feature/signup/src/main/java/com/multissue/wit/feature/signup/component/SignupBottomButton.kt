package com.multissue.wit.feature.signup.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.feature.signup.R

@Composable
fun SignupBottomButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    WitButton(
        modifier = Modifier
            .padding(bottom = 22.dp)
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
        enabled = enabled,
        title = stringResource(R.string.button_next)
    )
}