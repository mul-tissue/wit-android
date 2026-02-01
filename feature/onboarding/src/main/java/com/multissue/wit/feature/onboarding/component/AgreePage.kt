package com.multissue.wit.feature.onboarding.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.onboarding.R

@Composable
fun AgreePage(
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(top = 150.dp, start = 26.dp, end = 26.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.terms_of_service_title),
                style = WitTheme.typography.titleXXL,
                color = WitTheme.colors.text
            )
            Text(
                text = stringResource(R.string.terms_of_service_sub_title),
                style = WitTheme.typography.titleM,
                color = WitTheme.colors.subText
            )
        }

        WitButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = onNextButtonClicked,
            title = stringResource(R.string.next_button)
        )
    }
}