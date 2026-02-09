package com.multissue.wit

import android.app.Activity
import android.os.Build
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

            setNavigationBarColorCompat(
                color = Color.White.toArgb(),
                isLightBar = true
            )

            setStatusBarColorCompat(
                color = Color.White.toArgb(),
                isLightBar = true
            )

            val appState = rememberRootAppState()

            WitTheme {
                RootApp(appState)
            }
        }
    }
}

fun Activity.setNavigationBarColorCompat(color: Int, isLightBar: Boolean) {
    window.navigationBarColor = color
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        var flags = window.decorView.systemUiVisibility
        flags = if (isLightBar) {
            flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
        window.decorView.systemUiVisibility = flags
    }
}

fun Activity.setStatusBarColorCompat(color: Int, isLightBar: Boolean) {
    window.statusBarColor = color
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = window.decorView.systemUiVisibility
        flags = if (isLightBar) {
            flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        window.decorView.systemUiVisibility = flags
    }
}

//@Composable
//fun SetSystemBarsColor(
//    statusBarColor: Int,
//    statusBarIsLight: Boolean,
//    navBarColor: Int,
//    navBarIsLight: Boolean
//) {
//    val activity = LocalContext.current as? Activity
//    LaunchedEffect(statusBarColor, statusBarIsLight, navBarColor, navBarIsLight) {
//        activity?.setStatusBarColorCompat(statusBarColor, statusBarIsLight)
//        activity?.setNavigationBarColorCompat(navBarColor, navBarIsLight)
//    }
//}