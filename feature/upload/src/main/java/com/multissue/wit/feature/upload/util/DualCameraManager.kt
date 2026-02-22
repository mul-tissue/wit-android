package com.multissue.wit.feature.upload.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ConcurrentCamera
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.multissue.wit.designsystem.theme.WitTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

// 촬영 및 합성 로직
fun captureDualImage(
    context: Context,
    backCapture: ImageCapture,
    frontCapture: ImageCapture,
    executor: java.util.concurrent.Executor,
    onResult: () -> Unit
) {
    // 1. 후면 촬영
    backCapture.takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(backImage: ImageProxy) {
            val backBitmap = backImage.toBitmap().rotateBitmap(backImage.imageInfo.rotationDegrees)
            backImage.close()

            // 2. 전면 촬영
            frontCapture.takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(frontImage: ImageProxy) {
                    val frontBitmap = frontImage.toBitmap()
                        .rotateBitmap(frontImage.imageInfo.rotationDegrees)
                        .flipBitmap()
                    frontImage.close()

                    // 3. 합성 및 저장
                    val combined = combineBitmaps(backBitmap, frontBitmap, context)
                    saveBitmapToGallery(context, combined)

                    onResult()
                }
            })
        }
    })
}

private fun combineBitmaps(back: Bitmap, front: Bitmap, context: Context): Bitmap {
    // 1. 전체 결과 비트맵 생성 (후면 사진 기준)
    val result = Bitmap.createBitmap(back.width, back.height, back.config!!)
    val canvas = Canvas(result)

    // 2. 후면 배경 그리기
    canvas.drawBitmap(back, 0f, 0f, null)

    // 3. 전면 카메라 크기 계산 (120dp를 픽셀로 변환하되, 사진 해상도 비율에 맞춤)
    // 실제 기기 화면 너비 대비 사진 너비 비율을 구함
    val screenWidthPx = context.resources.displayMetrics.widthPixels.toFloat()
    val scaleFactor = back.width / screenWidthPx // 화면 대비 사진 해상도 배수

    val targetWidth = 120f.dpToPx(context) * scaleFactor
    val targetHeight = targetWidth * (4f / 3f) // 3:4 비율 강제 설정

    // 4. 전면 비트맵을 3:4 비율로 센터 크롭 및 스케일링
    val scaledFront = centerCropAndScale(front, targetWidth.toInt(), targetHeight.toInt())

    // 5. 라운드 및 테두리 처리를 위한 캔버스 작업
    val roundedFrontBitmap = Bitmap.createBitmap(targetWidth.toInt(), targetHeight.toInt(), Bitmap.Config.ARGB_8888)
    val frontCanvas = Canvas(roundedFrontBitmap)
    val paint = Paint().apply { isAntiAlias = true }

    // 6. 모서리 둥글게 깎기 (12dp 기준)
    val cornerRadius = 12f.dpToPx(context) * scaleFactor
    val rect = RectF(0f, 0f, targetWidth, targetHeight)

    frontCanvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    frontCanvas.drawBitmap(scaledFront, 0f, 0f, paint)

    // 7. 테두리 그리기 (1dp 기준)
    paint.xfermode = null
    paint.style = Paint.Style.STROKE
    paint.color = android.graphics.Color.WHITE
    paint.strokeWidth = 1.4f.dpToPx(context) * scaleFactor
    frontCanvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)

    // 8. 메인 캔버스에 합성 (여백 26dp 적용)
    val margin = 26f.dpToPx(context) * scaleFactor
    canvas.drawBitmap(roundedFrontBitmap, margin, margin, null)

    // 메모리 정리
    scaledFront.recycle()
    roundedFrontBitmap.recycle()

    return result
}

/**
 * 전면 비트맵을 3:4 비율로 센터 크롭 후 타겟 사이즈로 조절
 */
private fun centerCropAndScale(source: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
    val sourceRatio = source.width.toFloat() / source.height.toFloat()
    val targetRatio = targetWidth.toFloat() / targetHeight.toFloat()

    val cropWidth: Int
    val cropHeight: Int
    if (sourceRatio > targetRatio) {
        cropHeight = source.height
        cropWidth = (source.height * targetRatio).toInt()
    } else {
        cropWidth = source.width
        cropHeight = (source.width / targetRatio).toInt()
    }

    val x = (source.width - cropWidth) / 2
    val y = (source.height - cropHeight) / 2

    val cropped = Bitmap.createBitmap(source, x, y, cropWidth, cropHeight)
    val scaled = Bitmap.createScaledBitmap(cropped, targetWidth, targetHeight, true)
    if (cropped != source) cropped.recycle()
    return scaled
}

// 갤러리 저장 로직
private fun saveBitmapToGallery(context: Context, bitmap: Bitmap) {
    val filename = "DualCamera_${System.currentTimeMillis()}.jpg"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/DualCamera")
        }
    }

    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    uri?.let {
        context.contentResolver.openOutputStream(it).use { stream ->
            if (stream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            }
        }
    }
}

// ImageProxy를 Bitmap으로 변환하는 확장 함수
fun ImageProxy.toBitmap(): Bitmap {
    val buffer = planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

// 회전 각도 보정 확장 함수
fun Bitmap.rotateBitmap(degrees: Int): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees.toFloat()) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.flipBitmap(): Bitmap {
    val matrix = Matrix().apply { postScale(-1f, 1f, width / 2f, height / 2f) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

// dp를 px로 변환하기 위한 유틸 확장 함수
fun Float.dpToPx(context: Context): Float =
    this * context.resources.displayMetrics.density

fun androidx.compose.ui.unit.Dp.toPx(context: Context): Float =
    this.value.dpToPx(context)