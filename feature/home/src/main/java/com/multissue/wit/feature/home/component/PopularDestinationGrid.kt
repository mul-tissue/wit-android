package com.multissue.wit.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.feature.home.state.PopularDestinationState

@Composable
fun PopularDestinationGrid(
    modifier: Modifier = Modifier,
    destinations: List<PopularDestinationState>,
    onDestinationClick: (PopularDestinationState) -> Unit,
) {
    val chunked = destinations.chunked(3)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        chunked.forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                rowItems.forEach { destination ->
                    PopularDestinationChip(
                        modifier = Modifier.weight(1f),
                        destination = destination,
                        onClick = { onDestinationClick(destination) },
                    )
                }
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}