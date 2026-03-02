package com.multissue.wit.feature.mypage.map.util.marker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

suspend fun loadMarkerBitmap(
    context: Context,
    imageUrl: String,
    size: Int = 130,
    borderSize: Int = 2,
    shadowSize: Int = 6,
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

    val cx = finalSize / 2f
    val cy = finalSize / 2f
    val borderRadius = avatarBitmap.width / 2f + borderSize

    // 그림자
    val shadowPaint = Paint().apply {
        isAntiAlias = true
        shader = RadialGradient(
            cx, cy + 6f,
            borderRadius + shadowSize,
            intArrayOf("#55000000".toColorInt(), Color.TRANSPARENT),
            floatArrayOf(0.6f, 1f),
            Shader.TileMode.CLAMP
        )
    }
    canvas.drawCircle(cx, cy + 6f, borderRadius + shadowSize, shadowPaint)

    // 흰 테두리
    val borderPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }
    canvas.drawCircle(cx, cy, borderRadius, borderPaint)

    // 썸네일
    val left = cx - avatarBitmap.width / 2f
    val top = cy - avatarBitmap.height / 2f
    canvas.drawBitmap(avatarBitmap, left, top, null)

    return output
}
