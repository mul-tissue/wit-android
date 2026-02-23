package com.multissue.wit.feature.upload.component

import android.util.Log
import android.view.ScaleGestureDetector
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraInfo
import androidx.camera.core.CameraSelector
import androidx.camera.core.ConcurrentCamera
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.upload.util.captureDualImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

@Composable
fun DualCameraScreen(
    modifier: Modifier = Modifier,
    onCaptureFinished: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val shutterAlpha = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    val frontPreviewView = remember { PreviewView(context).apply { implementationMode = PreviewView.ImplementationMode.COMPATIBLE } }
    val backPreviewView = remember { PreviewView(context).apply { implementationMode = PreviewView.ImplementationMode.PERFORMANCE } }
    val backImageCapture = remember { ImageCapture.Builder().build() }
    val frontImageCapture = remember { ImageCapture.Builder().build() }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    var backCameraInfo by remember { mutableStateOf<CameraInfo?>(null) }   // 추가
    var backCameraControl by remember { mutableStateOf<CameraControl?>(null) }
    var isFlashOn by remember { mutableStateOf(false) }

    var currentZoomRatio by remember { mutableFloatStateOf(1f) }

    val scaleGestureDetector = remember {
        ScaleGestureDetector(
            context,
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    val cameraControl = backCameraControl ?: return true
                    val newZoom = (currentZoomRatio * detector.scaleFactor).coerceIn(1f, 2f)
                    currentZoomRatio = newZoom
                    cameraControl.setZoomRatio(newZoom)
                    return true
                }
            }
        )
    }

    // ImageCapture 유즈케이스 선언

    LaunchedEffect(Unit) {
        val cameraProvider = withContext(Dispatchers.IO) {
            ProcessCameraProvider.getInstance(context).get()
        }

        // 1. 후면 설정
        val backPreview = Preview.Builder().setTargetAspectRatio(AspectRatio.RATIO_4_3).build()
        backPreview.setSurfaceProvider(backPreviewView.surfaceProvider)
        val backGroup = UseCaseGroup.Builder()
            .addUseCase(backPreview)
            .addUseCase(backImageCapture) // 촬영 추가
            .build()

        // 2. 전면 설정
        val frontPreview = Preview.Builder().setTargetAspectRatio(AspectRatio.RATIO_4_3).build()
        frontPreview.setSurfaceProvider(frontPreviewView.surfaceProvider)
        val frontGroup = UseCaseGroup.Builder()
            .addUseCase(frontPreview)
            .addUseCase(frontImageCapture) // 촬영 추가
            .build()

        try {
            cameraProvider.unbindAll()
            delay(100)

            val backConfig = ConcurrentCamera.SingleCameraConfig(
                CameraSelector.DEFAULT_BACK_CAMERA, backGroup, lifecycleOwner
            )
            val frontConfig = ConcurrentCamera.SingleCameraConfig(
                CameraSelector.DEFAULT_FRONT_CAMERA, frontGroup, lifecycleOwner
            )

            val cameraInfos = cameraProvider.bindToLifecycle(listOf(backConfig, frontConfig))
            val backCamera = cameraInfos.cameras.find {
                it.cameraInfo.lensFacing == CameraSelector.LENS_FACING_BACK
            }
            backCameraControl = backCamera?.cameraControl
            backCameraInfo = backCamera?.cameraInfo

        } catch (e: Exception) {
            Log.e("DualCamera", "Binding failed: ${e.message}")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
            factory = { backPreviewView },
            update = { view ->
                view.setOnTouchListener { _, event ->
                    scaleGestureDetector.onTouchEvent(event)
                    true
                }
            }
        )

        Box(
            modifier = Modifier
                .padding(26.dp)
                .width(120.dp)
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black)
                .border(
                    width = 1.dp,
                    color = WitTheme.colors.white100,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { frontPreviewView }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = shutterAlpha.value))
        )

        Column(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ZoomLevelRow(
                currentZoom = currentZoomRatio,
                onZoomSelected = { zoom ->
                    currentZoomRatio = zoom
                    backCameraControl?.setZoomRatio(zoom)
                }
            )

            CameraControlRow(
                modifier = modifier
//                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 50.dp),
                isFlashOn = isFlashOn,
                onFlashButtonClicked = {
                    isFlashOn = !it
                    backCameraControl?.enableTorch(isFlashOn)
                },
                onCaptureButtonClicked = {
                    captureDualImage(
                        context = context,
                        backCapture = backImageCapture,
                        frontCapture = frontImageCapture,
                        executor = cameraExecutor,
                        onResult = {
                            onCaptureFinished()
                        },
                    )
                },
            )
        }
    }
}