package com.multissue.wit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WitApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}