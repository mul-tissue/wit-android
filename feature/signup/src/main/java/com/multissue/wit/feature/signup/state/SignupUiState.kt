package com.multissue.wit.feature.signup.state

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

data class SignupUiState (
    val nickname: String = "",
    val isCheckedNickname: Boolean = false,
    val isNickNameDuplicated: Boolean? = null,
): UiState

sealed class SignupUiIntent: UiIntent {
    data class SetNickname(val nickname: String): SignupUiIntent()
    data object CheckNickNameDuplicate: SignupUiIntent()
}

sealed interface SignupSideEffect: UiSideEffect