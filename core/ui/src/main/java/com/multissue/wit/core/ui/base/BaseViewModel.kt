package com.multissue.wit.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UiState

interface UiIntent

interface UiSideEffect

abstract class BaseViewModel<STATE : UiState, EFFECT : UiSideEffect, INTENT : UiIntent>(
    initialState: STATE
): ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _sideEffect: Channel<EFFECT> = Channel(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    protected val currentState: STATE
        get() = _uiState.value

    abstract fun onIntent(intent: INTENT)

    protected fun setState(reduce: STATE.() -> STATE) {
        _uiState.update { currentState.reduce() }
    }

    protected fun postSideEffect(vararg effects: EFFECT) {
        viewModelScope.launch {
            effects.forEach { effects ->
                _sideEffect.send(effects)
            }
        }
    }
}