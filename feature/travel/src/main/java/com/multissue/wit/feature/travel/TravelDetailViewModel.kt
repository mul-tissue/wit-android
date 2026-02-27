package com.multissue.wit.feature.travel

import androidx.lifecycle.ViewModel
import com.multissue.wit.feature.travel.navigation.TravelNavKey
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = TravelDetailViewModel.Factory::class)
class TravelDetailViewModel @AssistedInject constructor(
    @Assisted private val navKey: TravelNavKey,
) : ViewModel() {

    val travelId: Int = navKey.travelId

    @AssistedFactory
    interface Factory {
        fun create(navKey: TravelNavKey): TravelDetailViewModel
    }
}
