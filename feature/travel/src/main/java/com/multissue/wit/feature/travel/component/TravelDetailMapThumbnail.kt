package com.multissue.wit.feature.travel.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

private const val MAP_THUMBNAIL_ZOOM = 15f

@Composable
fun TravelDetailMapThumbnail(
    modifier: Modifier = Modifier,
    lat: Double,
    lng: Double,
) {
    val latLng = remember(lat, lng) { LatLng(lat, lng) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, MAP_THUMBNAIL_ZOOM)
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            scrollGesturesEnabled = false,
            zoomGesturesEnabled = false,
            tiltGesturesEnabled = false,
            rotationGesturesEnabled = false,
            zoomControlsEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false,
            compassEnabled = false,
            indoorLevelPickerEnabled = false,
            scrollGesturesEnabledDuringRotateOrZoom = false,
        ),
        properties = MapProperties(
            isMyLocationEnabled = false,
        ),
    ) {
        Marker(
            state = rememberMarkerState(position = latLng),
        )
    }
}
