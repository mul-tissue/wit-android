package com.multissue.wit.feature.map.state

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MarkerItemState(
    val name: String,
    val mSnippet: String = "",
    val location: LatLng,
    val mZIndex: Float,
    val imageUrl: String,
): ClusterItem {
    override fun getPosition(): LatLng = this.location
    override fun getTitle(): String? = this.name
    override fun getSnippet(): String? = this.mSnippet
    override fun getZIndex(): Float? = this.mZIndex
}