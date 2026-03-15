package com.multissue.wit.core.network.auth

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthEventBus @Inject constructor() {

    private val _events = MutableSharedFlow<AuthEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<AuthEvent> = _events

    fun emit(event: AuthEvent) {
        _events.tryEmit(event)
    }
}

sealed interface AuthEvent {
    data object TokenExpired : AuthEvent
}
