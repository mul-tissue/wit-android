package com.multissue.wit.core.ui.marker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt

suspend fun loadBitmapFromUrl(
    context: Context,
    imageUrl: String,
    size: Int = 130,
    borderSize: Int = 2,
    shadowSize: Int = 6
): Bitmap? {
    val loader = ImageLoader(context)

    val avatarSize = size - borderSize * 2

    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .size(avatarSize)
        .transformations(CircleCropTransformation())
        .allowHardware(false)
        .build()

    val result = loader.execute(request)
    val drawable = result.drawable ?: return null
    val avatarBitmap = (drawable as BitmapDrawable).bitmap

    val finalSize = size + shadowSize
    val output = createBitmap(finalSize, finalSize)
    val canvas = Canvas(output)

    val centerX = finalSize / 2f
    val centerY = finalSize / 2f

    val borderRadius = avatarBitmap.width / 2f + borderSize

    // 🔥 1️⃣ Shadow (Radial Gradient)
    val shadowPaint = Paint().apply {
        isAntiAlias = true
        shader = RadialGradient(
            centerX,
            centerY + 6f,
            borderRadius + shadowSize,
            intArrayOf(
                "#55000000".toColorInt(),
                Color.TRANSPARENT
            ),
            floatArrayOf(0.6f, 1f),
            Shader.TileMode.CLAMP
        )
    }

    canvas.drawCircle(
        centerX,
        centerY + 6f,
        borderRadius + shadowSize,
        shadowPaint
    )

    // 🔹 2️⃣ White Border
    val borderPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    canvas.drawCircle(centerX, centerY, borderRadius, borderPaint)

    // 🔹 3️⃣ Avatar (진짜 중앙 정렬)
    val left = centerX - avatarBitmap.width / 2f
    val top = centerY - avatarBitmap.height / 2f

    canvas.drawBitmap(
        avatarBitmap,
        left,
        top,
        null
    )

    return output
}
