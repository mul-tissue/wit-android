package com.multissue.wit.feature.signup.state

import com.multissue.wit.core.domain.model.terms.TermItem
import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState

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
    val termItems: List<TermItem> = emptyList(),
    val agreedTermIds: Set<String> = emptySet(),
    val termsContentUrl: String = "",
    val signupComplete: Boolean = false,
    val errorMessage: String? = null
) : UiState {
    val hasBirthDate: Boolean
        get() = birthYear != 0 && birthMonth != 0 && birthDay != 0

    val isAllTermsAgreed: Boolean
        get() = termItems.isNotEmpty() && termItems.all { it.id in agreedTermIds }

    val isRequiredTermsAgreed: Boolean
        get() = termItems.filter { it.required }.all { it.id in agreedTermIds }
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
    data class ToggleTermAgreement(val termId: String) : SignupUiIntent()
    data object ToggleAllTerms : SignupUiIntent()

    data class ShowTermsContent(val contentUrl: String) : SignupUiIntent()
    data object HideTermsContent : SignupUiIntent()
    data object SubmitAgreement : SignupUiIntent()
    data object DismissError : SignupUiIntent()
}

sealed interface SignupSideEffect : UiSideEffect
