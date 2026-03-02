package com.multissue.wit.designsystem.component.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.theme.WitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WitCenterAlignedTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = title, style = WitTheme.typography.titleXL) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = WitTheme.colors.containerColor,
            titleContentColor = WitTheme.colors.text,
            navigationIconContentColor = WitTheme.colors.iconTint,
            actionIconContentColor = WitTheme.colors.iconTint,
        ),
        navigationIcon = navigationIcon,
        actions = actions,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WitContentTopAppBar(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = content,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = WitTheme.colors.containerColor,
            titleContentColor = WitTheme.colors.text,
            navigationIconContentColor = WitTheme.colors.iconTint,
            actionIconContentColor = WitTheme.colors.iconTint,
        ),
        navigationIcon = navigationIcon,
        actions = actions,
    )
}

@Preview
@Composable
private fun WitCenterAlignedTopAppBarPreview() {
    WitTheme {
        WitCenterAlignedTopAppBar(
            title = "Top App Bar",
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.icon_back),
                        contentDescription = "navigationIconContentDescription"
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.icon_profile),
                        contentDescription = ""
                    )
                }
            }
        )
    }
}