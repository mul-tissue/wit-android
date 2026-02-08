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

import com.multissue.wit.feature.chat.navigation.ChatNavKey
import com.multissue.wit.feature.home.navigation.HomeNavKey
import com.multissue.wit.feature.map.navigation.MapNavKey
import com.multissue.wit.feature.mypage.navigation.MyPageNavKey
import com.multissue.wit.feature.upload.navigation.UploadNavKey

data class TopLevelNavItem(
    val selectedIconId: Int,
    val unselectedIconId: Int,
)

val HOME = TopLevelNavItem(
    selectedIconId = com.multissue.wit.designsystem.R.drawable.icon_nav_home_selected,
    unselectedIconId = com.multissue.wit.designsystem.R.drawable.icon_nav_home,
)

val CHAT = TopLevelNavItem(
    selectedIconId = com.multissue.wit.designsystem.R.drawable.icon_nav_chat_selected,
    unselectedIconId = com.multissue.wit.designsystem.R.drawable.icon_nav_chat,
)

val UPLOAD = TopLevelNavItem(
    selectedIconId = com.multissue.wit.designsystem.R.drawable.icon_plus,
    unselectedIconId = com.multissue.wit.designsystem.R.drawable.icon_plus,
)

val MYPAGE = TopLevelNavItem(
    selectedIconId = com.multissue.wit.designsystem.R.drawable.icon_user,
    unselectedIconId = com.multissue.wit.designsystem.R.drawable.icon_user,
)

val MAP = TopLevelNavItem(
    selectedIconId = com.multissue.wit.designsystem.R.drawable.icon_nav_home,
    unselectedIconId = com.multissue.wit.designsystem.R.drawable.icon_nav_home,
)
val NOTIFICATION = TopLevelNavItem(
    selectedIconId = com.multissue.wit.designsystem.R.drawable.icon_notification,
    unselectedIconId = com.multissue.wit.designsystem.R.drawable.icon_notification,
)

val TOP_LEVEL_NAV_ITEMS = mapOf(
    HomeNavKey to HOME,
    ChatNavKey to CHAT,
    UploadNavKey to UPLOAD,
    MyPageNavKey to MYPAGE,
    MapNavKey to MAP,
    //TODO Notification?
)
