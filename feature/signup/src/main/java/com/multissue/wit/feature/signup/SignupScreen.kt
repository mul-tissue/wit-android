package com.multissue.wit.feature.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multissue.wit.designsystem.component.background.WitGradientBackground
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.signup.component.BirthAndGenderPage
import com.multissue.wit.feature.signup.component.CompletePage
import com.multissue.wit.feature.signup.component.NicknamePage
import com.multissue.wit.feature.signup.component.SignupAgreementBottomSheet
import com.multissue.wit.feature.signup.state.SignUpStep
import com.multissue.wit.feature.signup.state.SignupUiIntent
import com.multissue.wit.feature.signup.state.SignupUiState
import kotlinx.coroutines.launch

@Composable
fun SignupRoute(
    modifier: Modifier = Modifier,
    signupViewModel: SignupViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by signupViewModel.uiState.collectAsStateWithLifecycle()

    SignupScreen(
        modifier = modifier,
        signupUiState = uiState,
        onIntent = signupViewModel::onIntent,
        navigateToMain = navigateToHome,
        navigateToLogin = navigateToLogin
    )
}

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    signupUiState: SignupUiState,
    onIntent: (SignupUiIntent) -> Unit,
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
            AnimatedVisibility(
                visible = signupUiState.signupComplete,
                enter = slideIn {
                    IntOffset(it.width, 0)
                }
            ) {
                CompletePage(
                    modifier = Modifier.weight(1f),
                    navigateToMain = navigateToMain
                )
            }
            if (!signupUiState.signupComplete) {
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
                                NicknamePage(
                                    modifier = Modifier.weight(1f),
                                    pagerState = pagerState,
                                    isCheckedNickname = signupUiState.isCheckedNickname,
                                    nickName = signupUiState.nickname,
                                    isNickNameDuplicated = signupUiState.isNickNameDuplicated,
                                    onNickNameChange = { onIntent(SignupUiIntent.SetNickname(it)) },
                                    onCheckNickNameDuplicate = { onIntent(SignupUiIntent.CheckNickNameDuplicate) }
                                )
                            }

                            SignUpStep.BIRTH_GENDER -> {
                                BirthAndGenderPage(
                                    modifier = Modifier.weight(1f),
                                    gender = signupUiState.gender,
                                    birth = signupUiState.birth,
                                    onSelectBirth = { onIntent(SignupUiIntent.ShowBirthSelectDialog) },
                                    onGenderChange = { onIntent(SignupUiIntent.SetGender(it)) },
                                    onShowAgreementBottomSheet = { onIntent(SignupUiIntent.ShowAgreementBottomSheet) }
                                )
                            }
                        }
                    }
                }
                SignupAgreementBottomSheet(
                    state = signupUiState.agreementState,
                    onStateChange = { type, checked ->
                        onIntent(SignupUiIntent.CheckAgreement(type, checked))
                    },
                    onConfirm = {
                        onIntent(SignupUiIntent.SignupComplete)
                    },
                    onDismiss = { onIntent(SignupUiIntent.HideAgreementBottomSheet) },
                    visible = signupUiState.showAgreementBottomSheet
                )
            }
            }
    }
}