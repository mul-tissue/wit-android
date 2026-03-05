package com.multissue.wit.core.network.util

import com.multissue.wit.core.network.model.ApiResponse
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResponse<T> = try {
    ApiResponse.Success(apiCall())
} catch (e: HttpException) {
    ApiResponse.Failure(e.code(), e.message())
} catch (e: IOException) {
    ApiResponse.NetworkError(e)
} catch (e: Exception) {
    ApiResponse.NetworkError(e)
}
