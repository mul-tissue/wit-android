package com.multissue.wit.core.network.util

import com.multissue.wit.core.network.model.ApiResponse
import com.multissue.wit.core.network.model.ErrorData
import com.multissue.wit.core.network.model.BaseResponse
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

private val errorJson = Json { ignoreUnknownKeys = true }

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResponse<T> = try {
    ApiResponse.Success(apiCall())
} catch (e: CancellationException) {
    throw e
} catch (e: HttpException) {
    ApiResponse.Failure(e.code(), e.parseServerMessage())
} catch (e: IOException) {
    ApiResponse.NetworkError(e)
} catch (e: Exception) {
    ApiResponse.NetworkError(e)
}

private fun HttpException.parseServerMessage(): String? = try {
    response()?.errorBody()?.string()?.let { body ->
        errorJson.decodeFromString<BaseResponse<ErrorData>>(body).data?.message
    }
} catch (_: Exception) {
    message()
}
