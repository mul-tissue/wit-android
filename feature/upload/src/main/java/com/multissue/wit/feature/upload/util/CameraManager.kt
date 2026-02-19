package com.multissue.wit.feature.upload.util

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.hardware.camera2.*
import android.hardware.camera2.CameraManager
import android.hardware.camera2.params.StreamConfigurationMap
import android.media.ImageReader
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import android.view.Surface
import androidx.annotation.RequiresApi
import java.io.OutputStream
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.R)
class CameraManager(private val context: Context) {
    private val manager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    private var backCamera: CameraDevice? = null
    private var frontCamera: CameraDevice? = null

    private var backSession: CameraCaptureSession? = null
    private var frontSession: CameraCaptureSession? = null

    private var backReader: ImageReader? = null
    private var frontReader: ImageReader? = null

    // 1. 3:4 비율에 가장 가까운 해상도 찾기
    private fun getBest34Size(cameraId: String): Size {
        val characteristics = manager.getCameraCharacteristics(cameraId)
        val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!
        val sizes = map.getOutputSizes(ImageFormat.JPEG)

        return sizes.filter {
            val ratio = it.width.toFloat() / it.height.toFloat()
            abs(ratio - 0.75f) < 0.01f || abs(ratio - 1.33f) < 0.01f
        }.maxByOrNull { it.width * it.height } ?: sizes[0]
    }

    @SuppressLint("MissingPermission")
    fun openDualCamera(backSurface: Surface, frontSurface: Surface) {
        val concurrentIds = manager.concurrentCameraIds
        if (concurrentIds.isEmpty()) return

        val ids = concurrentIds.first()
        val backId = ids.first()
        val frontId = ids.last()

        val backSize = getBest34Size(backId)
        val frontSize = getBest34Size(frontId)

        backReader = ImageReader.newInstance(backSize.width, backSize.height, ImageFormat.JPEG, 2)
        frontReader = ImageReader.newInstance(frontSize.width, frontSize.height, ImageFormat.JPEG, 2)

        // 후면 카메라 오픈
        manager.openCamera(backId, object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                backCamera = camera
                startSession(camera, backSurface, backReader!!.surface, true)
            }
            override fun onDisconnected(c: CameraDevice) { c.close() }
            override fun onError(c: CameraDevice, e: Int) { c.close() }
        }, null)

        // 전면 카메라 오픈
        manager.openCamera(frontId, object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                frontCamera = camera
                startSession(camera, frontSurface, frontReader!!.surface, false)
            }
            override fun onDisconnected(c: CameraDevice) { c.close() }
            override fun onError(c: CameraDevice, e: Int) { c.close() }
        }, null)
    }

    // 2. startSession 함수 구현 (생략 없음)
    private fun startSession(camera: CameraDevice, previewSurface: Surface, imageSurface: Surface, isBack: Boolean) {
        val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        builder.addTarget(previewSurface)

        camera.createCaptureSession(listOf(previewSurface, imageSurface), object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession) {
                if (isBack) backSession = session else frontSession = session
                try {
                    session.setRepeatingRequest(builder.build(), null, null)
                } catch (e: Exception) { e.printStackTrace() }
            }
            override fun onConfigureFailed(s: CameraCaptureSession) {}
        }, null)
    }

    // 3. 사진 촬영 및 비트맵 합성
    fun takePhoto(onComplete: (Bitmap) -> Unit) {
        var backBmp: Bitmap? = null
        var frontBmp: Bitmap? = null

        fun checkAndMerge() {
            if (backBmp != null && frontBmp != null) {
                val merged = mergeBitmaps(backBmp!!, frontBmp!!)
                saveBitmapToGallery(merged)
                onComplete(merged)
            }
        }

        // 후면 캡처
        backReader?.setOnImageAvailableListener({ reader ->
            val image = reader.acquireLatestImage()
            backBmp = imageToBitmap(image)
            image.close()
            checkAndMerge()
        }, null)

        val backRequest = backCamera?.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        backRequest?.addTarget(backReader!!.surface)
        backSession?.capture(backRequest!!.build(), null, null)

        // 전면 캡처
        frontReader?.setOnImageAvailableListener({ reader ->
            val image = reader.acquireLatestImage()
            frontBmp = imageToBitmap(image)
            image.close()
            checkAndMerge()
        }, null)

        val frontRequest = frontCamera?.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        frontRequest?.addTarget(frontReader!!.surface)
        frontSession?.capture(frontRequest!!.build(), null, null)
    }

    private fun imageToBitmap(image: android.media.Image): Bitmap {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun mergeBitmaps(back: Bitmap, front: Bitmap): Bitmap {
        val result = Bitmap.createBitmap(back.width, back.height, back.config!!)
        val canvas = Canvas(result)
        canvas.drawBitmap(back, 0f, 0f, null)

        // 전면 사진을 우측 상단에 1/4 크기로 합성 (Wheeler f 오타 수정)
        val smallFront = Bitmap.createScaledBitmap(front, back.width / 4, back.height / 4, true)
        canvas.drawBitmap(smallFront, (back.width - smallFront.width - 50f), 50f, null)
        return result
    }

    private fun saveBitmapToGallery(bitmap: Bitmap) {
        val filename = "Dual_${System.currentTimeMillis()}.jpg"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Camera")
            }
        }
        val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        uri?.let {
            val out: OutputStream? = context.contentResolver.openOutputStream(it)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out!!)
            out.close()
        }
    }
}