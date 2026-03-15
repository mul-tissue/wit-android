package com.multissue.wit.core.network.interceptor

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject

class PrettyJsonLogger : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val pretty = when {
                    message.startsWith("{") -> JSONObject(message).toString(2)
                    else -> JSONArray(message).toString(2)
                }
                Log.d(TAG, pretty)
                return
            } catch (_: Exception) { }
        }
        Log.d(TAG, message)
    }

    companion object {
        private const val TAG = "WitHttp"
    }
}
