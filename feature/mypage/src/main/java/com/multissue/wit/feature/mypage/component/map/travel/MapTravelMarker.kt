package com.multissue.wit.feature.mypage.component.map.travel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.multissue.wit.feature.mypage.state.map.travel.MapTravelItem
import com.multissue.wit.feature.mypage.util.loadMarkerBitmap

@Composable
fun MapTravelMarker(
    item: MapTravelItem,
    onClick: () -> Unit = {},
) {
    val context = LocalContext.current
    var bitmapDescriptor by remember { mutableStateOf<BitmapDescriptor?>(null) }

    val thumbnailUrl = item.companionThumbnails.firstOrNull() ?: ""

    LaunchedEffect(thumbnailUrl) {
        if (thumbnailUrl.isNotEmpty()) {
            val bitmap = loadMarkerBitmap(context, thumbnailUrl)
            bitmap?.let { bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(it) }
        }
    }

    Marker(
        state = MarkerState(position = item.latLng),
        icon = bitmapDescriptor,
        onClick = { onClick(); false },
    )
}
