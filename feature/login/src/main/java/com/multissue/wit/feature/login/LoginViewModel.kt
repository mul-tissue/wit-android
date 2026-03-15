package com.multissue.wit.feature.login

import androidx.lifecycle.viewModelScope
import com.multissue.wit.core.domain.model.auth.SocialType
import com.multissue.wit.core.domain.usecase.auth.SocialLoginUseCase
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.login.state.LoginSideEffect
import com.multissue.wit.feature.login.state.LoginUiIntent
import com.multissue.wit.feature.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val socialLoginUseCase: SocialLoginUseCase,
) : BaseViewModel<LoginUiState, LoginSideEffect, LoginUiIntent>(LoginUiState()) {

    override fun onIntent(intent: LoginUiIntent) {
        when (intent) {
            is LoginUiIntent.KakaoLoginClicked -> login(SocialType.KAKAO, intent.token)
            is LoginUiIntent.GoogleLoginClicked -> login(SocialType.GOOGLE, intent.token)
        }
    }

    private fun login(socialType: SocialType, token: String) {
        if (currentState.isLoading) return
        setState { copy(isLoading = true) }

        viewModelScope.launch {
            socialLoginUseCase(socialType, token)
                .onSuccess { result ->
                    setState { copy(isLoading = false) }
                    postSideEffect(LoginSideEffect.LoginSuccess(result.status))
                }
                .onFailure { throwable ->
                    setState { copy(isLoading = false) }
                    postSideEffect(
                        LoginSideEffect.LoginError(
                            throwable.message ?: "로그인에 실패했습니다."
                        )
                    )
                }
        }
    }
}
