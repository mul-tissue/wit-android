package com.multissue.wit.feature.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.signup.state.SignupUiState
import com.multissue.wit.feature.signup.state.agreement.AgreementType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupAgreementBottomSheet(
    state: SignupUiState.AgreementState,
    visible: Boolean,
    onStateChange: (AgreementType, Boolean) -> Unit,
    onShowTermsDialog: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (!visible) return

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        sheetGesturesEnabled = false,
        containerColor = WitTheme.colors.background,
        contentColor = WitTheme.colors.text,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        contentWindowInsets = { BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom) },
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(28.dp)
                    .height(2.dp)
                    .background(WitTheme.colors.disabledText),
            )
        }
    ) {
        AgreementContent(
            state = state,
            onStateChange = onStateChange,
            onShowTermsDialog = onShowTermsDialog,
            onConfirm = onConfirm
        )
    }
}