package com.multissue.wit.core.domain.exception

sealed class WitException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    class HttpException(val code: Int, message: String?) : WitException(message)
    class NetworkException(cause: Throwable) : WitException(cause = cause)
}
