package com.multissue.wit.feature.travel

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun TravelDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: TravelDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    TravelDetailScreen(
        modifier = modifier,
    )
}

@Composable
internal fun TravelDetailScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = "TravelDetailScreen")
    }
}
