package com.multissue.wit.feature.map.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.multissue.wit.feature.map.util.marker.loadBitmapFromUrl

@Composable
fun PlaceMarker(
    position: LatLng,
    imageUrl: String,
) {
    val context = LocalContext.current
    var bitmapDescriptor by remember { mutableStateOf<BitmapDescriptor?>(null) }

    LaunchedEffect(imageUrl) {
        val bitmap = loadBitmapFromUrl(context, imageUrl)
        bitmap?.let {
            bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(it)
        }
    }

    if (bitmapDescriptor != null) {
        Marker(
            state = MarkerState(position = position),
            icon = bitmapDescriptor
        )
    }
}