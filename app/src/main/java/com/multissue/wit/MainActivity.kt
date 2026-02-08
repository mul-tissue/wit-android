package com.multissue.wit

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.ui.root.RootApp
import com.multissue.wit.ui.root.rememberRootAppState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()

            WindowCompat.getInsetsController(window, window.decorView)
                .isAppearanceLightStatusBars = true //TODO 다크모드 대응 시 교체 필요

            val appState = rememberRootAppState()

            WitTheme {
                RootApp(appState)
            }
        }
    }
}