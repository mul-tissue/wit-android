package com.multissue.wit

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multissue.wit.designsystem.component.dialog.WitReLoginDialog
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.ui.root.RootApp
import com.multissue.wit.ui.root.RootViewModel
import com.multissue.wit.ui.root.StartDestination
import com.multissue.wit.ui.root.rememberRootAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WitActivity : ComponentActivity() {

    private val rootViewModel: RootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true //TODO 다크모드 대응 시 교체 필요

        setContent {
            val startDestination by rootViewModel.startDestination.collectAsStateWithLifecycle()
            val showReLoginDialog by rootViewModel.showReLoginDialog.collectAsStateWithLifecycle()

            WitTheme {
                val appState = rememberRootAppState(
                    startFromMain = startDestination == StartDestination.Main
                )
                RootApp(appState = appState)

                WitReLoginDialog(
                    visible = showReLoginDialog,
                    onConfirm = {
                        rootViewModel.onReLoginConfirmed()
                        appState.navigateToAuth()
                    }
                )
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