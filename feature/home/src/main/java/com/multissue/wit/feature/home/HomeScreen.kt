package com.multissue.wit.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.multissue.wit.designsystem.component.button.WitButton

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToMap: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "HomeScreen"
        )
        WitButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentWidth()
                .height(56.dp),
            onClick = navigateToMap,
            title = "맵으로 가기"
        )
    }
}