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

import androidx.annotation.StringRes
import com.multissue.wit.R.string
import com.multissue.wit.feature.chat.navigation.ChatNavKey
import com.multissue.wit.feature.home.navigation.HomeNavKey

data class MainLevelNavItem(
    val selectedIconId: Int,
    val unselectedIconId: Int,
    @StringRes val titleTextId: Int,
)

val HOME = MainLevelNavItem(
    selectedIconId = com.multissue.wit.designsystem.R.drawable.icon_nav_home_selected,
    unselectedIconId = com.multissue.wit.designsystem.R.drawable.icon_nav_home,
    titleTextId = string.home_title,
)

val CHAT = MainLevelNavItem(
    selectedIconId = com.multissue.wit.designsystem.R.drawable.icon_nav_chat_selected,
    unselectedIconId = com.multissue.wit.designsystem.R.drawable.icon_nav_chat,
    titleTextId = string.chat_title,
)

val MAIN_LEVEL_NAV_ITEMS = mapOf(
    HomeNavKey to HOME,
    ChatNavKey to CHAT
)