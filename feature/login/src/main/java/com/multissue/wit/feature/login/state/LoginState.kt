package com.multissue.wit.feature.login.state

import com.multissue.wit.core.domain.model.auth.AuthStatus
import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

data class LoginUiState(
    val isLoading: Boolean = false,
) : UiState

sealed class LoginUiIntent : UiIntent {
    data class KakaoLoginClicked(val token: String) : LoginUiIntent()
    data class GoogleLoginClicked(val token: String) : LoginUiIntent()
}

sealed interface LoginSideEffect : UiSideEffect {
    data class LoginSuccess(val status: AuthStatus) : LoginSideEffect
    data class LoginError(val message: String) : LoginSideEffect
}
