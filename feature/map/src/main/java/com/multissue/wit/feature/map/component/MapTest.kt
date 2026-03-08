package com.multissue.wit.feature.map.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.multissue.wit.core.ui.map.PlaceMarker
import com.multissue.wit.core.ui.map.WitMarker
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.map.R
import com.multissue.wit.feature.map.dummy.deoksu
import com.multissue.wit.core.ui.map.util.distanceMeters
import com.multissue.wit.core.ui.map.util.getCurrentLocation
import kotlinx.coroutines.launch

const val initialZoomLevel = 12f
@Composable
fun MapTest(
    modifier: Modifier = Modifier,
    initialLat: Double? = null,
    initialLng: Double? = null,
    locationButtonPadding: Dp,
) {
    val context = LocalContext.current
    var myLocation by remember { mutableStateOf<LatLng?>(null) }
    var showButton by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        if (initialLat != null && initialLng != null) {
            cameraPositionState.position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                LatLng(initialLat, initialLng),
                initialZoomLevel
            )
        }
        
        getCurrentLocation(
            context = context,
            onLocation = { location ->
                myLocation = location
                // 초기 위치가 지정되지 않은 경우에만 현재 위치로 애니메이션
                if (initialLat == null || initialLng == null) {
                    scope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(
                                location,
                                initialZoomLevel
                            )
                        )
                    }
                }
            }
        )
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving && myLocation != null) {
            val cameraCenter = cameraPositionState.position.target
            val distance = distanceMeters(myLocation!!, cameraCenter)

            // 📌 예: 50m 이상 벗어나면 버튼 표시
            showButton = distance > 50
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false,
            )
        ) {
            myLocation?.let { location ->
                WitMarker(location)
            }

            PlaceMarker(
                position = deoksu.location,
                imageUrl = deoksu.imageUrl
            )
        }

        if (showButton) {
            IconButton(
                modifier = Modifier
                    .padding(end = 16.dp, bottom = locationButtonPadding + 16.dp)
                    .size(40.dp)
                    .align(Alignment.BottomEnd)
                    .shadow(
                        elevation = 2.dp,
                        shape = CircleShape
                    )
                    .background(
                        color = WitTheme.colors.white100,
                        shape = CircleShape
                    ),
                onClick = {
                    getCurrentLocation(
                        context = context,
                        onLocation = {
                            myLocation = it
                            scope.launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        it,
                                        initialZoomLevel
                                    )
                                )
                            }
                        }
                    )
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_current_location),
                    contentDescription = "",
                    tint = WitTheme.colors.subText
                )
            }
        }
    }
}