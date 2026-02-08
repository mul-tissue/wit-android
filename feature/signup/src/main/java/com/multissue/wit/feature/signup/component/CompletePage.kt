package com.multissue.wit.feature.signup.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.signup.R

@Composable
fun CompletePage(
    modifier: Modifier = Modifier,
    navigateToMain: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(206.dp))

        Image(
            modifier = Modifier
                .width(228.dp)
                .height(221.dp),
            painter = painterResource(R.drawable.image_signup_complete),
            contentDescription = "회원가입 완료 이미지"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "여행피드와 동행까지, Wit",
            style = WitTheme.typography.titleXL
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "회원가입이 완료되었어요",
            style = WitTheme.typography.titleS,
            color = WitTheme.colors.disabledText
        )

        Spacer(modifier = Modifier.weight(1f))

        WitButton(
            modifier = Modifier
                .padding(bottom = 22.dp)
                .fillMaxWidth()
                .height(50.dp),
            onClick = navigateToMain,
            title = stringResource(R.string.button_complete)
        )
    }
}