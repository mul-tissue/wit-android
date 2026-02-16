package com.multissue.wit.feature.signup.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState
import com.multissue.wit.feature.signup.R
import com.multissue.wit.feature.signup.state.agreement.AgreementType

data class SignupUiState(
    val nickname: String = "",
    val isCheckedNickname: Boolean = false,
    val isNickNameDuplicated: Boolean? = null,

    val showBirthSelectDialog: Boolean = false,
    val birthYear: Int = 0,
    val birthMonth: Int = 0,
    val birthDay: Int = 0,

    val gender: GenderType = GenderType.NONE,
    val showAgreementBottomSheet: Boolean = false,
    val agreementState: AgreementState = AgreementState(),
    val showTermsDialog: Boolean = false,
    val signupComplete: Boolean = false
) : UiState {
    @get:Composable
    val birthDate: String
        get() = if (birthYear != 0 && birthMonth != 0 && birthDay != 0) {
            stringResource(
                R.string.birth_format_date_selected,
                birthYear,
                birthMonth,
                birthDay
            )
        } else ""


    data class AgreementState(
        val terms: Boolean = false,       // 필수
        val location: Boolean = false,    // 필수
        val marketing: Boolean = false    // 선택
    ) {
        val isRequiredAccepted: Boolean
            get() = terms && location
    }
}

sealed class SignupUiIntent : UiIntent {
    data class SetNickname(val nickname: String) : SignupUiIntent()
    data object CheckNickNameDuplicate : SignupUiIntent()
    data object ShowBirthSelectDialog : SignupUiIntent()
    data object HideBirthSelectDialog : SignupUiIntent()
    data class SelectBirthDate(
        val year: Int,
        val month: Int,
        val day: Int
    ) : SignupUiIntent()

    data class SetGender(val gender: GenderType) : SignupUiIntent()
    data object ShowAgreementBottomSheet : SignupUiIntent()
    data object HideAgreementBottomSheet : SignupUiIntent()
    data class CheckAgreement(
        val type: AgreementType,
        val checked: Boolean
    ) : SignupUiIntent()

    data object ShowTermsDialog : SignupUiIntent()
    data object HideTermsDialog : SignupUiIntent()
    data object SignupComplete : SignupUiIntent()
}

sealed interface SignupSideEffect : UiSideEffect