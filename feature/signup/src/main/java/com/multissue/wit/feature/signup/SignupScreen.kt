package com.multissue.wit.feature.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = hiltViewModel()
) {

}

@Composable
internal fun SignupScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "SignupScreen"
        )
    }
}