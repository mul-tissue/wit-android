package com.multissue.wit.feature.signup

import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.signup.state.agreement.AgreementType
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
            is SignupUiIntent.ShowBirthSelectDialog -> onShowBirthSettingDialog()
            is SignupUiIntent.HideBirthSelectDialog -> onHideBirthSettingDialog()
            is SignupUiIntent.SetGender -> onGenderChange(intent.gender)
            is SignupUiIntent.ShowAgreementBottomSheet -> onShowAgreementBottomSheet()
            is SignupUiIntent.HideAgreementBottomSheet -> onHideAgreementBottomSheet()
            is SignupUiIntent.CheckAgreement -> onCheckAgreement(intent.type, intent.checked)
            is SignupUiIntent.ShowTermsDialog -> onShowTermsDialog()
            is SignupUiIntent.HideTermsDialog -> onHideTermsDialog()
            is SignupUiIntent.SignupComplete -> onSignupComplete()
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

    private fun onShowBirthSettingDialog() {
        setState { copy(showBirthSelectDialog = true) }
    }

    private fun onHideBirthSettingDialog() {
        setState { copy(showBirthSelectDialog = false) }
    }

    private fun onGenderChange(gender: GenderType) {
        setState { copy(gender = gender) }
    }

    private fun onShowAgreementBottomSheet() {
        setState { copy(showAgreementBottomSheet = true) }
    }

    private fun onHideAgreementBottomSheet() {
        setState { copy(showAgreementBottomSheet = false) }
    }

    private fun onCheckAgreement(
        type: AgreementType,
        checked: Boolean
    ) {
        when (type) {
            AgreementType.ALL -> setState {
                copy(
                    agreementState = agreementState.copy(
                        terms = checked,
                        location = checked,
                        marketing = checked,
                    )
                )
            }
            AgreementType.TERMS -> {
                setState {
                    copy(
                        agreementState = agreementState.copy(
                            terms = checked,
                        )
                    )
                }
            }
            AgreementType.LOCATION -> {
                setState {
                    copy(
                        agreementState = agreementState.copy(
                            location = checked,
                        )
                    )
                }
            }
            AgreementType.MARKETING -> {
                setState {
                    copy(
                        agreementState = agreementState.copy(
                            marketing = checked,
                        )
                    )
                }
            }
        }
    }

    private fun onShowTermsDialog() {
        setState {
            copy(
                showTermsDialog = true
            )
        }
    }

    private fun onHideTermsDialog() {
        setState {
            copy(
                showTermsDialog = false
            )
        }
    }

    private fun onSignupComplete() {
        setState {
            copy(
                showAgreementBottomSheet = false,
                signupComplete = true
            )
        }
    }
}
