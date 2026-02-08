package com.multissue.wit.feature.signup

import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.signup.state.GenderType
import com.multissue.wit.feature.signup.state.SignupSideEffect
import com.multissue.wit.feature.signup.state.SignupUiIntent
import com.multissue.wit.feature.signup.state.SignupUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(

) : BaseViewModel<SignupUiState, SignupSideEffect, SignupUiIntent>(
    initialState = SignupUiState()
) {
    override fun onIntent(intent: SignupUiIntent) {
        when (intent) {
            is SignupUiIntent.SetNickname -> onNicknameChange(intent.nickname)
            is SignupUiIntent.CheckNickNameDuplicate -> onCheckNickNameDuplicate()
        }
    }

    private fun onNicknameChange(nickname: String) {
        setState {
            copy(
                nickname = nickname,
                isCheckedNickname = false,
                isNickNameDuplicated = null
            )
        }
    }

    private fun onCheckNickNameDuplicate() {
        // TODO("서버에서 NickName check")
        setState {
            copy(
                isNickNameDuplicated = false,
                isCheckedNickname = true
            )
        }
    }
}
