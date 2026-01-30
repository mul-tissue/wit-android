package com.multissue.wit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.multissue.wit.core.navigation.Navigator
import com.multissue.wit.navigation.TOP_LEVEL_NAV_ITEMS

@Composable
fun WitNavigationSuiteScaffold(
    appState: WitAppState,
    content: @Composable ColumnScope.() -> Unit,
) {
    val navigator = remember { Navigator(appState.navigationState) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .navigationBarsPadding(),
        ) {
            content()
            WitNavigationRail(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(color = Color.Black), //TODO
                navItems = {
                    TOP_LEVEL_NAV_ITEMS.forEach { (navKey, navItem) ->
//                        val hasUnread = unreadNavKeys.contains(navKey)
                        val selected = navKey == appState.navigationState.currentTopLevelKey
                        WitNavItem(
                            modifier = Modifier,
//                                .testTag("NiaNavItem")
//                                .then(if (hasUnread) Modifier.notificationDot() else Modifier),
                            selected = selected,
                            onClick = { navigator.navigate(navKey) },
                            icon = {
                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    imageVector = navItem.unselectedIcon,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            },
                            selectedIcon = {
                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    imageVector = navItem.selectedIcon,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            },
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun WitNavigationRail(
    modifier: Modifier = Modifier,
    navItems: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        navItems()
    }
}

@Composable
fun WitNavItem(
    modifier: Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            },
//            .selectable(
//                selected = selected,
//                onClick = onClick,
//                role = Role.Tab,
//                interactionSource = interactionSource,
//                indication = null
//            ),
        contentAlignment = Alignment.Center
    ) {
        if (selected) selectedIcon()
        else icon()
    }
}

object WitNavigationDefaults {
    @Composable
    fun navigationContentColor() = Color(0xFF74B1FF)

    @Composable
    fun navigationSelectedItemColor() = Color(0xFF74B1FF)

    @Composable
    fun navigationIndicatorColor() = Color(0x00000000)
}
