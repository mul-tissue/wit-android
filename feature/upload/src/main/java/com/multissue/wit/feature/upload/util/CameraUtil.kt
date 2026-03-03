package com.multissue.wit.feature.upload.util

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.CaptureRequest
import android.os.Build
import android.view.Surface
import android.view.SurfaceHolder
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.R)
class DualCameraManager(private val context: Context) {
    private val manager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var backCamera: CameraDevice? = null
    private var frontCamera: CameraDevice? = null

    @SuppressLint("MissingPermission")
    fun openDualCamera(backHolder: SurfaceHolder, frontHolder: SurfaceHolder) {
        val concurrentIds = manager.concurrentCameraIds
        if (concurrentIds.isEmpty()) return // 지원하지 않는 기기

        // 보통 첫 번째 세트에 전/후면 ID가 포함됨
        val ids = concurrentIds.first()

        ids.forEach { id ->
            manager.openCamera(id, object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) {
                    val holder = if (id == ids.first()) backHolder else frontHolder
                    if (id == ids.first()) backCamera = camera else frontCamera = camera
                    startPreview(camera, holder.surface)
                }
                override fun onDisconnected(camera: CameraDevice) { camera.close() }
                override fun onError(camera: CameraDevice, error: Int) { camera.close() }
            }, null)
        }
    }

    private fun startPreview(camera: CameraDevice, surface: Surface) {
        val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        builder.addTarget(surface)

        // Android 13 이상에서는 createCaptureSession(SessionConfiguration) 권장
        camera.createCaptureSession(listOf(surface), object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession) {
                builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
                session.setRepeatingRequest(builder.build(), null, null)
            }
            override fun onConfigureFailed(session: CameraCaptureSession) {}
        }, null)
    }

    fun closeCameras() {
        backCamera?.close()
        frontCamera?.close()
    }
}