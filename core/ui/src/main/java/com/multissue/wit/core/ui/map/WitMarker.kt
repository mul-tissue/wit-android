package com.multissue.wit.core.ui.map

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.multissue.wit.core.ui.R
import com.multissue.wit.core.ui.marker.rememberVectorMarker


@Composable
fun WitMarker(
    position: LatLng,
) {
    val markerIcon = rememberVectorMarker(
        iconResourceId = R.drawable.icon_marker
    )

    Marker(
        state = MarkerState(position = position),
        icon = markerIcon
    )
}