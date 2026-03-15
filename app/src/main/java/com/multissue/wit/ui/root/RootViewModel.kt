package com.multissue.wit.ui.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multissue.wit.core.domain.usecase.auth.AutoLoginUseCase
import com.multissue.wit.core.network.auth.AuthEvent
import com.multissue.wit.core.network.auth.AuthEventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    autoLoginUseCase: AutoLoginUseCase,
    private val authEventBus: AuthEventBus,
) : ViewModel() {

    private val _startDestination = MutableStateFlow(
        if (autoLoginUseCase()) StartDestination.Main else StartDestination.Auth
    )
    val startDestination: StateFlow<StartDestination> = _startDestination

    private val _showReLoginDialog = MutableStateFlow(false)
    val showReLoginDialog: StateFlow<Boolean> = _showReLoginDialog

    init {
        viewModelScope.launch {
            authEventBus.events.collect { event ->
                when (event) {
                    AuthEvent.TokenExpired -> _showReLoginDialog.value = true
                }
            }
        }
    }

    fun onReLoginConfirmed() {
        _showReLoginDialog.value = false
        _startDestination.value = StartDestination.Auth
    }
}

sealed interface StartDestination {
    data object Auth : StartDestination
    data object Main : StartDestination
}
