package com.multissue.wit.core.domain.exception

sealed class WitException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    class HttpException(val code: Int, message: String?) : WitException(message)
    class NetworkException(cause: Throwable) : WitException(cause = cause)
}

fun Throwable.toUserMessage(): String = when (this) {
    is WitException.HttpException -> message ?: defaultHttpMessage(code)
    is WitException.NetworkException -> "네트워크 연결을 확인해주세요."
    else -> "알 수 없는 오류가 발생했습니다."
}

private fun defaultHttpMessage(code: Int): String = when (code) {
    400 -> "잘못된 요청입니다."
    401 -> "로그인이 필요합니다."
    403 -> "접근 권한이 없습니다."
    404 -> "요청한 정보를 찾을 수 없습니다."
    in 500..599 -> "서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요."
    else -> "오류가 발생했습니다. (코드: $code)"
}
