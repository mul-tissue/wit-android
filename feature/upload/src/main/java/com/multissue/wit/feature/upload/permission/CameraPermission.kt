package com.multissue.wit.feature.upload.permission

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermission(
    onGranted: @Composable () -> Unit
) {
    val context = LocalContext.current
    var permissionRequested by rememberSaveable { mutableStateOf(false) }

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
        )
    )

    when {
        permissionState.allPermissionsGranted -> {
            onGranted()
        }
        else -> {
            LaunchedEffect(Unit) {
                permissionState.launchMultiplePermissionRequest()
            }
        }
    }
}
