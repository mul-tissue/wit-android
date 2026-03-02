package com.multissue.wit.feature.upload.component

import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.multissue.wit.feature.upload.util.CameraManager
import com.multissue.wit.feature.upload.util.DualCameraManager

@Composable
fun DualCameraScreen() {
    val context = LocalContext.current
    val dualCameraManager = remember { DualCameraManager(context) }

    // SurfaceHolder를 추적하기 위한 상태
    var backHolder by remember { mutableStateOf<SurfaceHolder?>(null) }
    var frontHolder by remember { mutableStateOf<SurfaceHolder?>(null) }

    // 두 홀더가 모두 준비되면 카메라 오픈
    LaunchedEffect(backHolder, frontHolder) {
        if (backHolder != null && frontHolder != null) {
            dualCameraManager.openDualCamera(backHolder!!, frontHolder!!)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 후면 카메라 (전체화면)
        AndroidView(
            factory = { ctx ->
                SurfaceView(ctx).apply {
                    holder.addCallback(object : SurfaceHolder.Callback {
                        override fun surfaceCreated(h: SurfaceHolder) { backHolder = h }
                        override fun surfaceChanged(h: SurfaceHolder, f: Int, w: Int, h1: Int) {}
                        override fun surfaceDestroyed(h: SurfaceHolder) { dualCameraManager.closeCameras() }
                    })
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // 전면 카메라 (PIP 창)
        AndroidView(
            factory = { ctx ->
                SurfaceView(ctx).apply {
                    holder.addCallback(object : SurfaceHolder.Callback {
                        override fun surfaceCreated(h: SurfaceHolder) { frontHolder = h }
                        override fun surfaceChanged(h: SurfaceHolder, f: Int, w: Int, h1: Int) {}
                        override fun surfaceDestroyed(h: SurfaceHolder) {}
                    })
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(180.dp, 120.dp)
        )
    }
}

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val dualCameraManager = remember { CameraManager(context) }
    var backHolder by remember { mutableStateOf<SurfaceHolder?>(null) }
    var frontHolder by remember { mutableStateOf<SurfaceHolder?>(null) }

    LaunchedEffect(backHolder, frontHolder) {
        if (backHolder != null && frontHolder != null) {
            dualCameraManager.openDualCamera(backHolder!!.surface, frontHolder!!.surface)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 후면 프리뷰 (3:4 비율 강제)
        Box(modifier = Modifier.fillMaxSize().aspectRatio(3f / 4f)) {
            AndroidView(
                factory = { ctx ->
                    SurfaceView(ctx).apply {
                        holder.addCallback(object : SurfaceHolder.Callback {
                            override fun surfaceCreated(h: SurfaceHolder) { backHolder = h }
                            override fun surfaceChanged(h: SurfaceHolder, f: Int, w: Int, h1: Int) {}
                            override fun surfaceDestroyed(h: SurfaceHolder) {}
                        })
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // 전면 프리뷰 (PIP - 3:4 비율)
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(120.dp, 160.dp) // 120:160 = 3:4
                .align(Alignment.TopEnd)
                .border(2.dp, Color.White)
        ) {
            AndroidView(
                factory = { ctx ->
                    SurfaceView(ctx).apply {
                        holder.addCallback(object : SurfaceHolder.Callback {
                            override fun surfaceCreated(h: SurfaceHolder) { frontHolder = h }
                            override fun surfaceChanged(h: SurfaceHolder, f: Int, w: Int, h1: Int) {}
                            override fun surfaceDestroyed(h: SurfaceHolder) {}
                        })
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        Button(
            onClick = { dualCameraManager.takePhoto { /* 완료 콜백 */ } },
            modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp)
        ) {
            Text("3:4 듀얼 사진 촬영")
        }
    }
}