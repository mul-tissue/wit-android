package com.multissue.wit.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.background.WitGradientBackground
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.theme.white100
import com.multissue.wit.designsystem.theme.yellow
import com.multissue.wit.feature.login.component.SocialLoginButton

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier,
) {
    WitGradientBackground {
        Column(
            modifier = modifier
                .padding(horizontal = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier.size(322.dp),
                painter = painterResource(R.drawable.image_globe),
                contentDescription = "지구 이미지"
            )

            Spacer(modifier = Modifier.height(90.dp))

            SocialLoginButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                title = stringResource(R.string.kakao_login),
                icon = {
                    Image(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(20.dp),
                        painter = painterResource(R.drawable.icon_kakao),
                        contentDescription = stringResource(R.string.kakao_login)
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = yellow,
                    contentColor = WitTheme.colors.text
                )
            ) {
                TODO("카카오 로그인")
            }

            Spacer(modifier = Modifier.height(12.dp))

            SocialLoginButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                title = stringResource(R.string.google_login),
                icon = {
                    Image(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(20.dp),
                        painter = painterResource(R.drawable.icon_google),
                        contentDescription = stringResource(R.string.google_login)
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = white100,
                    contentColor = WitTheme.colors.text
                )
            ) {
                TODO("구글 로그인")
            }
            
            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}