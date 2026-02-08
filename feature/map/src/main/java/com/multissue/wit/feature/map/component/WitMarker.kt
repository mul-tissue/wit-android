package com.multissue.wit.feature.map.component

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.util.marker.rememberVectorMarker

@Composable
fun WitMarker(
    position: LatLng,
    title: String,
    snippet: String,
    @DrawableRes iconResourceId: Int,
) {
    val markerIcon = rememberVectorMarker(
        iconResourceId = R.drawable.icon_marker
    )

    Marker(
        state = MarkerState(position = position),
        title = title,
        snippet = snippet,
        icon = markerIcon
    )
}