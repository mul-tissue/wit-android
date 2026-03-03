package com.multissue.wit.feature.upload

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.multissue.wit.feature.upload.component.CameraScreen
import com.multissue.wit.feature.upload.permission.CameraPermission

@Composable
fun UploadScreen(
    modifier: Modifier = Modifier,
    viewModel: UploadViewModel = hiltViewModel()
) {

}

@Composable
internal fun UploadScreen(
    modifier: Modifier = Modifier,
) {
    CameraPermission {
        CameraScreen()
    }
}