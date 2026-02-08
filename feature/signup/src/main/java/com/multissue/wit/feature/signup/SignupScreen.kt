package com.multissue.wit.feature.signup

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.background.WitGradientBackground
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.signup.component.AgreementPage
import com.multissue.wit.feature.signup.component.BirthAndGenderPage
import com.multissue.wit.feature.signup.component.NicknamePage
import com.multissue.wit.feature.signup.state.SignUpStep
import kotlinx.coroutines.launch

@Composable
fun SignupRoute(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    SignupScreen(
        modifier = modifier,
        navigateToMain = navigateToHome,
        navigateToLogin = navigateToLogin
    )
}
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { SignUpStep.entries.size }
    )
    val scope = rememberCoroutineScope()

    WitGradientBackground {
        Column(
            modifier = modifier
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.size(44.dp),
                    onClick = {
                        if (pagerState.currentPage == 0) {
                            navigateToLogin()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage - 1,
                                    animationSpec = tween(
                                        durationMillis = 450,
                                        easing = FastOutSlowInEasing
                                    )
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.icon_back),
                        contentDescription = "뒤로가기",
                        tint = WitTheme.colors.iconTint
                    )
                }
            }
            Column(
                modifier = modifier
                    .weight(1f),
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth(),
                    userScrollEnabled = false,
                ) { page ->
                    when (SignUpStep.entries[page]) {
                        SignUpStep.NICKNAME -> {
                            NicknamePage()
                        }
                        SignUpStep.BIRTH_GENDER -> {
                            BirthAndGenderPage()
                        }
                        SignUpStep.AGREEMENT -> {
                            AgreementPage()
                        }
                    }
                }
            }
        }
    }
}