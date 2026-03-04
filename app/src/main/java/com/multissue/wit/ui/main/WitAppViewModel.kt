package com.multissue.wit.ui.main

import com.multissue.wit.core.ui.base.BaseViewModel
import com.multissue.wit.core.ui.base.UiIntent
import com.multissue.wit.core.ui.base.UiSideEffect
import com.multissue.wit.core.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data object WitAppUiState : UiState

sealed class WitAppUiIntent : UiIntent {
    data object CenterButtonClicked : WitAppUiIntent()
}

sealed interface WitAppSideEffect : UiSideEffect {
    data object OpenMapSheet : WitAppSideEffect
}

@HiltViewModel
class WitAppViewModel @Inject constructor() : BaseViewModel<WitAppUiState, WitAppSideEffect, WitAppUiIntent>(
    initialState = WitAppUiState
) {
    override fun onIntent(intent: WitAppUiIntent) {
        when (intent) {
            WitAppUiIntent.CenterButtonClicked -> postSideEffect(WitAppSideEffect.OpenMapSheet)
        }
    }
}
