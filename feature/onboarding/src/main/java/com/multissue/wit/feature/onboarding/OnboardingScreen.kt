package com.multissue.wit.feature.onboarding

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.onboarding.component.OnboardingPage
import com.multissue.wit.feature.onboarding.component.PagerIndicator
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit,
//    viewModel: OnboardingViewModel = hiltViewModel<OnboardingViewModel>()
) {
    OnboardingScreen(
        navigateToLogin = navigateToLogin
    )
}

@Composable
private fun OnboardingScreen(
//    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 3 }
    )

    Column(
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .background(Color(0xFFF7F8FE))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
            ) { page ->
                OnboardingPage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    page = page
                )
            }
            PagerIndicator(
                pageCount = pagerState.pageCount,
                currentPage = pagerState.currentPage
            )
        }

        WitButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                when(pagerState.currentPage) {
                    2 -> {
                        navigateToLogin()
                    }
                    else -> {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1,
                                animationSpec = tween(
                                    durationMillis = 400
                                )
                            )
                        }
                    }
                }
            },
            title = when(pagerState.currentPage) {
                2 -> stringResource(R.string.signup_button)
                else -> stringResource(R.string.next_button)
            }
        )
    }

}