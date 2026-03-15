package com.multissue.wit

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        if (BuildConfig.DEBUG) {
            Log.d("WitApplication", "KeyHash: ${Utility.getKeyHash(this)}")
        }
    }
}
