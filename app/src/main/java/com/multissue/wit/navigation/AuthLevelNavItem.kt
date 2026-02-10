/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.multissue.wit.navigation

import com.multissue.wit.feature.login.navigation.LoginNavKey
import com.multissue.wit.feature.onboarding.navigation.OnboardingNavKey
import com.multissue.wit.feature.signup.navigation.SignupNavKey
import com.multissue.wit.navigation.auth.AuthNavKey
import com.multissue.wit.navigation.main.MainNavKey

data object AuthLevelNavItem

val ONBOARDING = AuthLevelNavItem
val LOGIN = AuthLevelNavItem
val SIGNUP = AuthLevelNavItem

val Auth_LEVEL_NAV_ITEMS = mapOf(
    OnboardingNavKey to ONBOARDING,
    LoginNavKey to LOGIN,
    SignupNavKey to SIGNUP
)