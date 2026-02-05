package com.multissue.wit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.ui.root.RootApp
import com.multissue.wit.ui.root.rememberRootAppState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberRootAppState()

            WitTheme {
                RootApp(appState)
            }
        }
    }
}