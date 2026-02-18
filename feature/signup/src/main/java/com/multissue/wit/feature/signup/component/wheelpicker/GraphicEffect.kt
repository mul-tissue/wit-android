package com.multissue.wit.feature.signup.component.wheelpicker

import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.ui.graphics.GraphicsLayerScope
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sin

internal const val curveRate = 1.0f
internal const val viewportCurveRate = 0.653f
internal const val InfiniteItemsCount = Int.MAX_VALUE

internal fun GraphicsLayerScope.graphic3DEffect(
    layoutInfo: LazyListLayoutInfo,
    index: Int
) {
    val itemInfo = layoutInfo.visibleItemsInfo
        .find { item -> item.index == index } ?: return

    val itemCenter = itemInfo.offset.toFloat() + itemInfo.size / 2f

    val itemCenterY = itemCenter + layoutInfo.beforeContentPadding
    val viewportCenterY = layoutInfo.viewportSize.height / 2F

    val offsetFraction = (itemCenterY - viewportCenterY) / viewportCenterY
    val scale = 1 - (offsetFraction.absoluteValue).pow(2) * 0.11f
    scaleX = scale

    val alphaValue =
        (1f - offsetFraction.absoluteValue.pow(0.4f))
            .coerceIn(0f, 1f)

    this.alpha = alphaValue

    rotationX = -90 * offsetFraction

    val r = (2f * viewportCenterY / Math.PI).toFloat()

    translationY = if (offsetFraction == 0f) {
        0f
    } else {
        val h = (sin(Math.toRadians(offsetFraction.absoluteValue * 90.0)) * r).toFloat()

        val diffY = if (offsetFraction < 0) {
            (viewportCenterY - h.absoluteValue) - itemCenterY.absoluteValue
        } else {
            (viewportCenterY + h.absoluteValue) - itemCenterY.absoluteValue
        }
        diffY
    }
    this.cameraDistance = layoutInfo.viewportSize.height.toFloat() / 25f
}