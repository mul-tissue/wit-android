package com.multissue.wit.designsystem.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
interface WitDialogScope {
    val title: String?
    val message: String?

    val bodyContent: @Composable () -> Unit

    val leftButtonText: String
    val rightButtonText: String
    val rightButtonColor: Color
    val onLeftButtonClick: () -> Unit
    val onRightButtonClick: () -> Unit
}

class DefaultWitDialogScope(
    override val title: String?,
    override val message: String?,
    override val bodyContent: @Composable () -> Unit = {},
    override val leftButtonText: String,
    override val rightButtonText: String,
    override val rightButtonColor: Color,
    override val onLeftButtonClick: () -> Unit,
    override val onRightButtonClick: () -> Unit,
): WitDialogScope