package com.multissue.wit.feature.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.multissue.wit.core.domain.model.auth.AuthStatus
import com.multissue.wit.designsystem.component.background.WitGradientBackground
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.theme.white100
import com.multissue.wit.designsystem.theme.yellow
import com.multissue.wit.feature.login.component.SocialLoginButton
import com.multissue.wit.feature.login.social.SocialLoginClient
import com.multissue.wit.feature.login.state.LoginSideEffect
import com.multissue.wit.feature.login.state.LoginUiIntent
import kotlinx.coroutines.launch

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToSignUp: () -> Unit,
    navigateToMain: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is LoginSideEffect.LoginSuccess -> {
                    when (effect.status) {
                        AuthStatus.ACTIVE -> navigateToMain()
                        else -> navigateToSignUp()
                    }
                }
                is LoginSideEffect.LoginError -> {
                    Log.e("LoginScreen", effect.message)
                    // TODO: 에러 UI 표시 (Snackbar 등)
                }
            }
        }
    }

    LoginScreen(
        isLoading = uiState.isLoading,
        onKakaoLogin = { token ->
            viewModel.onIntent(LoginUiIntent.KakaoLoginClicked(token))
        },
        onGoogleLogin = { token ->
            viewModel.onIntent(LoginUiIntent.GoogleLoginClicked(token))
        },
    )
}

@Composable
private fun LoginScreen(
    isLoading: Boolean,
    onKakaoLogin: (String) -> Unit,
    onGoogleLogin: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    WitGradientBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 26.dp),
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = stringResource(R.string.login_title_first),
                style = WitTheme.typography.titleXXL
            )
            Text(
                text = stringResource(R.string.login_title_second),
                style = WitTheme.typography.titleXXL
            )

            Spacer(modifier = Modifier.height(183.dp))

            Image(
                modifier = Modifier
                    .width(95.dp)
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.image_login_logo),
                contentDescription = "지구 이미지"
            )

            Spacer(modifier = Modifier.weight(1f))

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
                if (!isLoading) {
                    scope.launch {
                        runCatching { SocialLoginClient.loginWithKakao(context) }
                            .onSuccess(onKakaoLogin)
                            .onFailure { Log.e("LoginScreen", "카카오 로그인 실패", it) }
                    }
                }
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
                if (!isLoading) {
                    scope.launch {
                        runCatching { SocialLoginClient.loginWithGoogle(context) }
                            .onSuccess(onGoogleLogin)
                            .onFailure {
                                if (it is GetCredentialCancellationException) return@launch
                                Log.e("LoginScreen", "구글 로그인 실패", it)
                            }
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}
