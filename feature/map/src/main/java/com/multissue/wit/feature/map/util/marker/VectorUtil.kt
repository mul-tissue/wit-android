package com.multissue.wit.feature.map.util.marker

import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@Composable
fun rememberVectorMarker(
    @DrawableRes iconResourceId: Int,
    tint: Color = Color.Unspecified,
    sizeDp: DpSize = DpSize(width = 32.dp, height = 40.dp)
): BitmapDescriptor {
    val context = LocalContext.current
    val density = LocalDensity.current

    return remember(iconResourceId, tint, sizeDp) {
        val sizeWidthPx = with(density) { sizeDp.width.roundToPx() }
        val sizeHeightPx = with(density) { sizeDp.height.roundToPx() }

        val drawable = ContextCompat.getDrawable(context, iconResourceId)
        drawable?.setBounds(0, 0, sizeWidthPx, sizeHeightPx)
        val bitmap = createBitmap(sizeWidthPx, sizeHeightPx)

        val canvas = Canvas(bitmap)
        drawable?.draw(canvas)
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}