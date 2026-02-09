package com.multissue.wit.feature.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.signup.R
import com.multissue.wit.feature.signup.state.SignupUiState
import com.multissue.wit.feature.signup.state.agreement.AgreementType

@Composable
fun AgreementContent(
    state: SignupUiState.AgreementState,
    onStateChange: (AgreementType, Boolean) -> Unit,
    onShowTermsDialog: () -> Unit,
    onConfirm: () -> Unit
) {
    val terms by rememberUpdatedState(state.terms)
    val location by rememberUpdatedState(state.location)
    val marketing by rememberUpdatedState(state.marketing)

    val isAllChecked by remember {
        derivedStateOf {
            terms && location && marketing
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp)
    ) {
        Text(
            text = stringResource(R.string.agreement_bottom_sheet_title),
            style = WitTheme.typography.titleXL
        )

        Spacer(Modifier.height(40.dp))

        AgreementAllItem(
            modifier = Modifier.padding(vertical = 12.dp),
            title = stringResource(R.string.agreement_all_title),
            checked = isAllChecked,
            onCheckedChange = { onStateChange(AgreementType.ALL, it) }
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = WitTheme.colors.divider
        )

        Spacer(Modifier.height(20.dp))

        AgreementItem(
            modifier = Modifier.padding(vertical = 8.dp),
            title = stringResource(R.string.agreement_terms_title),
            checked = state.terms,
            onShowAgreementDescription = {
                // TODO("API 연결 시 약관 Type에 따라 분기")
                onShowTermsDialog()
            },
            onCheckedChange = { onStateChange(AgreementType.TERMS, it) }
        )

        AgreementItem(
            modifier = Modifier.padding(vertical = 8.dp),
            title = stringResource(R.string.agreement_location_title),
            checked = state.location,
            onShowAgreementDescription = {
                // TODO("API 연결 시 약관 Type에 따라 분기")
                onShowTermsDialog()
            },
            onCheckedChange = { onStateChange(AgreementType.LOCATION, it) }
        )

        AgreementItem(
            modifier = Modifier.padding(vertical = 8.dp),
            title = stringResource(R.string.agreement_marketing_title),
            checked = state.marketing,
            onShowAgreementDescription = {
                // TODO("API 연결 시 약관 Type에 따라 분기")
                onShowTermsDialog()
            },
            onCheckedChange = { onStateChange(AgreementType.MARKETING, it) }
        )

        Spacer(Modifier.height(36.dp))

        SignupBottomButton(
            enabled = state.isRequiredAccepted,
            onClick = onConfirm
        )

        Spacer(Modifier.height(8.dp))
    }
}