package com.multissue.wit.feature.upload.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermission(
    onGranted: @Composable () -> Unit
) {
    val context = LocalContext.current
    var permissionRequested by rememberSaveable { mutableStateOf(false) }

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(Manifest.permission.CAMERA)
    )

    when {
        permissionState.allPermissionsGranted -> {
            onGranted()
        }
        permissionState.shouldShowRationale -> {
            CameraPermissionDeniedContent(
                title = "카메라 권한이 필요해요",
                description = "사진을 촬영하려면 카메라 접근 권한이 필요합니다.\n권한을 허용해 주세요.",
                buttonLabel = "권한 허용",
                onButtonClick = { permissionState.launchMultiplePermissionRequest() },
            )
        }
        permissionRequested -> {
            CameraPermissionDeniedContent(
                title = "카메라 권한이 거부되었어요",
                description = "카메라 권한이 거부되었습니다.\n설정에서 권한을 직접 허용해 주세요.",
                buttonLabel = "설정으로 이동",
                onButtonClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                },
            )
        }
        else -> {
            LaunchedEffect(Unit) {
                permissionRequested = true
                permissionState.launchMultiplePermissionRequest()
            }
        }
    }
}

@Composable
private fun CameraPermissionDeniedContent(
    title: String,
    description: String,
    buttonLabel: String,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = WitTheme.typography.titleL,
            color = WitTheme.colors.text,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = description,
            style = WitTheme.typography.bodyM,
            color = WitTheme.colors.subText,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(32.dp))
        WitButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            title = buttonLabel,
            onClick = onButtonClick,
        )
    }
}
