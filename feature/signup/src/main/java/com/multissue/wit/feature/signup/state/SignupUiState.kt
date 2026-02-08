package com.multissue.wit.feature.signup.state

import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState
import com.multissue.wit.feature.signup.state.agreement.AgreementType

data class SignupUiState (
    val nickname: String = "",
    val isCheckedNickname: Boolean = false,
    val isNickNameDuplicated: Boolean? = null,

    val showBirthSelectDialog: Boolean = false,
    val birth: String = "",
    val gender: GenderType = GenderType.NONE,
    val showAgreementBottomSheet: Boolean = false,
    val agreementState: AgreementState = AgreementState(),
    val signupComplete: Boolean = false
): UiState {
    data class AgreementState(
        val terms: Boolean = false,       // 필수
        val location: Boolean = false,    // 필수
        val marketing: Boolean = false    // 선택
    ) {
        val isRequiredAccepted: Boolean
            get() = terms && location
    }
}

sealed class SignupUiIntent: UiIntent {
    data class SetNickname(val nickname: String): SignupUiIntent()
    data object CheckNickNameDuplicate: SignupUiIntent()
    data object ShowBirthSelectDialog: SignupUiIntent()
    data object HideBirthSelectDialog: SignupUiIntent()
    data class SetGender(val gender: GenderType): SignupUiIntent()
    data object ShowAgreementBottomSheet: SignupUiIntent()
    data object HideAgreementBottomSheet: SignupUiIntent()
    data class CheckAgreement(
        val type: AgreementType,
        val checked: Boolean
    ): SignupUiIntent()
    data object SignupComplete: SignupUiIntent()
}

sealed interface SignupSideEffect: UiSideEffect