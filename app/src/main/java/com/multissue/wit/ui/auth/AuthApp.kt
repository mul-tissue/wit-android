package com.multissue.wit.ui.auth

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.core.navigation.toEntries
import com.multissue.wit.feature.login.navigation.LoginNavKey
import com.multissue.wit.feature.login.navigation.loginEntry
import com.multissue.wit.feature.onboarding.navigation.onboardingEntry
import com.multissue.wit.feature.signup.navigation.SignupNavKey
import com.multissue.wit.feature.signup.navigation.signupEntry
import com.multissue.wit.navigation.main.MainNavKey

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AuthApp(
    authState: AuthAppState,
    modifier: Modifier = Modifier,
) {
    val navigator = remember { Navigator(authState.navigationState) }
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    val entryProvider = entryProvider {
        onboardingEntry(navigator)
        loginEntry(
            navigateToSignUp = { navigator.navigate(SignupNavKey) }
        )
        signupEntry(
            navigateToLogin = { navigator.navigate(LoginNavKey) },
            navigateToMain = { navigator.navigate(MainNavKey) },
        )
    }

    NavDisplay(
        entries = authState.navigationState.toEntries(entryProvider),
        sceneStrategy = listDetailStrategy,
        onBack = { navigator.goBack() },
    )
}