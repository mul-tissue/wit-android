package com.multissue.wit.feature.upload

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.component.snackbar.WitSnackBarHost
import com.multissue.wit.designsystem.component.snackbar.WitSnackBarVisuals
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.upload.component.DualCameraScreen
import com.multissue.wit.feature.upload.permission.CameraPermission
import kotlinx.coroutines.launch

@Composable
fun UploadScreen(
    modifier: Modifier = Modifier,
    viewModel: UploadViewModel = hiltViewModel()
) {

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun UploadScreen(
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        snackbarHost = {
            WitSnackBarHost(
                hostState = snackbarHostState,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(
                    color = WitTheme.colors.black100
                )
                .padding(top = 110.dp),
            verticalArrangement = Arrangement.Center
        ) {
            CameraPermission {
                DualCameraScreen(
                    onCaptureFinished = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                WitSnackBarVisuals(
                                    message = "촬영되었습니다.",
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}