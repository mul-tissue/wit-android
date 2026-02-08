package com.multissue.wit.feature.map.util.permission

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermission(
    onGranted: @Composable () -> Unit
) {
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )

    when {
        permissionState.status.isGranted -> {
            onGranted()
        }
        else -> {
            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}
