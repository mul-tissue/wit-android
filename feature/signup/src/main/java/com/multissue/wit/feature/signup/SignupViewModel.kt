package com.multissue.wit.feature.signup

import androidx.lifecycle.viewModelScope
import com.multissue.wit.core.domain.exception.WitException
import com.multissue.wit.core.domain.exception.toUserMessage
import com.multissue.wit.core.domain.usecase.terms.AgreeTermsUseCase
import com.multissue.wit.core.domain.usecase.terms.GetActiveTermsUseCase
import com.multissue.wit.core.domain.usecase.user.CheckNicknameDuplicateUseCase
import com.multissue.wit.core.domain.usecase.user.CompleteOnboardingUseCase
import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.feature.signup.state.GenderType
import com.multissue.wit.feature.signup.state.SignupSideEffect
import com.multissue.wit.feature.signup.state.SignupUiIntent
import com.multissue.wit.feature.signup.state.SignupUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val checkNicknameDuplicateUseCase: CheckNicknameDuplicateUseCase,
    private val getActiveTermsUseCase: GetActiveTermsUseCase,
    private val agreeTermsUseCase: AgreeTermsUseCase,
    private val completeOnboardingUseCase: CompleteOnboardingUseCase,
) : BaseViewModel<SignupUiState, SignupSideEffect, SignupUiIntent>(
    initialState = SignupUiState()
) {
    override fun onIntent(intent: SignupUiIntent) {
        when (intent) {
            is SignupUiIntent.SetNickname -> onNicknameChange(intent.nickname)
            is SignupUiIntent.CheckNickNameDuplicate -> onCheckNickNameDuplicate()
            is SignupUiIntent.ShowBirthSelectDialog -> onShowBirthSelectDialog()
            is SignupUiIntent.HideBirthSelectDialog -> onHideBirthSelectDialog()
            is SignupUiIntent.SelectBirthDate -> onSelectBirthDate(intent.year, intent.month, intent.day)
            is SignupUiIntent.SetGender -> onGenderChange(intent.gender)
            is SignupUiIntent.ShowAgreementBottomSheet -> onShowAgreementBottomSheet()
            is SignupUiIntent.HideAgreementBottomSheet -> onHideAgreementBottomSheet()
            is SignupUiIntent.ToggleTermAgreement -> onToggleTermAgreement(intent.termId)
            is SignupUiIntent.ToggleAllTerms -> onToggleAllTerms()
            is SignupUiIntent.ShowTermsContent -> onShowTermsContent(intent.contentUrl)
            is SignupUiIntent.HideTermsContent -> onHideTermsContent()
            is SignupUiIntent.SubmitAgreement -> onSubmitAgreement()
            is SignupUiIntent.DismissError -> onDismissError()
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
        viewModelScope.launch {
            checkNicknameDuplicateUseCase(currentState.nickname)
                .onSuccess { isAvailable ->
                    setState {
                        copy(
                            isNickNameDuplicated = !isAvailable,
                            isCheckedNickname = true
                        )
                    }
                }
                .onFailure { error ->
                    if (error is WitException.HttpException && error.code == 409) {
                        setState { copy(isNickNameDuplicated = true, isCheckedNickname = true) }
                    } else {
                        setState { copy(errorMessage = error.toUserMessage()) }
                    }
                }
        }
    }

    private fun onShowBirthSelectDialog() {
        setState { copy(showBirthSelectDialog = true) }
    }

    private fun onHideBirthSelectDialog() {
        setState { copy(showBirthSelectDialog = false) }
    }

    private fun onSelectBirthDate(year: Int, month: Int, day: Int) {
        setState { copy(birthYear = year, birthMonth = month, birthDay = day) }
    }

    private fun onGenderChange(gender: GenderType) {
        setState { copy(gender = gender) }
    }

    private fun onShowAgreementBottomSheet() {
        if (currentState.termItems.isEmpty()) {
            loadTerms()
        }
    }

    private fun loadTerms() {
        viewModelScope.launch {
            getActiveTermsUseCase()
                .onSuccess { terms ->
                    setState { copy(termItems = terms, showAgreementBottomSheet = true) }
                }
                .onFailure {
                    setState { copy(errorMessage = it.toUserMessage()) }
                }
        }
    }

    private fun onHideAgreementBottomSheet() {
        setState { copy(showAgreementBottomSheet = false) }
    }

    private fun onShowTermsContent(contentUrl: String) {
        setState { copy(termsContentUrl = contentUrl) }
    }

    private fun onHideTermsContent() {
        setState { copy(termsContentUrl = "") }
    }

    private fun onToggleTermAgreement(termId: String) {
        val current = currentState.agreedTermIds
        val updated = if (termId in current) current - termId else current + termId
        setState { copy(agreedTermIds = updated) }
    }

    private fun onToggleAllTerms() {
        val allIds = currentState.termItems.map { it.id }.toSet()
        val updated = if (currentState.isAllTermsAgreed) emptySet<String>() else allIds
        setState { copy(agreedTermIds = updated) }
    }

    private fun onDismissError() {
        setState { copy(errorMessage = null) }
    }

    private fun onSubmitAgreement() {
        viewModelScope.launch {
            val agreements = currentState.termItems.map { term ->
                term.id to (term.id in currentState.agreedTermIds)
            }
            agreeTermsUseCase(agreements)
                .onSuccess {
                    setState { copy(showAgreementBottomSheet = false) }
                    completeOnboarding()
                }
                .onFailure {
                    setState { copy(errorMessage = it.toUserMessage()) }
                }
        }
    }

    private suspend fun completeOnboarding() {
        val state = currentState
        val birthDate = "%04d-%02d-%02d".format(state.birthYear, state.birthMonth, state.birthDay)
        val gender = state.gender.name
        completeOnboardingUseCase(
            nickname = state.nickname,
            gender = gender,
            birthDate = birthDate,
            profileImagePath = null,
        )
            .onSuccess {
                setState { copy(signupComplete = true) }
            }
            .onFailure {
                setState { copy(errorMessage = it.toUserMessage()) }
            }
    }
}
