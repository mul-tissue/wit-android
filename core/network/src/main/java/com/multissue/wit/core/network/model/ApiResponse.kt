package com.multissue.wit.core.network.model

sealed interface ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>
    data class Failure(val code: Int, val message: String?) : ApiResponse<Nothing>
    data class NetworkError(val throwable: Throwable) : ApiResponse<Nothing>
}

inline fun <T, R> ApiResponse<T>.map(transform: (T) -> R): ApiResponse<R> = when (this) {
    is ApiResponse.Success -> ApiResponse.Success(transform(data))
    is ApiResponse.Failure -> this
    is ApiResponse.NetworkError -> this
}

inline fun <T> ApiResponse<T>.onSuccess(action: (T) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Success) action(data)
    return this
}

inline fun <T> ApiResponse<T>.onFailure(action: (code: Int, message: String?) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Failure) action(code, message)
    return this
}

inline fun <T> ApiResponse<T>.onNetworkError(action: (Throwable) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.NetworkError) action(throwable)
    return this
}

fun <T> ApiResponse<T>.getOrNull(): T? = if (this is ApiResponse.Success) data else null
