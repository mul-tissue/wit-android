package com.multissue.wit.feature.mypage.component.map.feed

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
import com.multissue.wit.feature.mypage.state.map.feed.MapFeedItem
import com.multissue.wit.feature.mypage.util.loadMarkerBitmap

@Composable
fun MapFeedMarker(
    item: MapFeedItem,
    onClick: () -> Unit = {},
) {
    val context = LocalContext.current
    var bitmapDescriptor by remember { mutableStateOf<BitmapDescriptor?>(null) }

    LaunchedEffect(item.thumbnailUrl) {
        val bitmap = loadMarkerBitmap(context, item.thumbnailUrl)
        bitmap?.let { bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(it) }
    }

    Marker(
        state = MarkerState(position = item.latLng),
        icon = bitmapDescriptor,
        onClick = { onClick(); false },
    )
}
