package com.multissue.wit.feature.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.core.domain.model.terms.TermItem
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.signup.R

@Composable
fun AgreementContent(
    termItems: List<TermItem>,
    agreedTermIds: Set<String>,
    isAllAgreed: Boolean,
    isRequiredAgreed: Boolean,
    onToggleTerm: (String) -> Unit,
    onToggleAll: () -> Unit,
    onShowTermsContent: (String) -> Unit,
    onConfirm: () -> Unit,
) {
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
            checked = isAllAgreed,
            onCheckedChange = { onToggleAll() }
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = WitTheme.colors.divider
        )

        Spacer(Modifier.height(20.dp))

        termItems.forEach { term ->
            val label = if (term.required) {
                "${term.title} (${stringResource(R.string.agreement_required)})"
            } else {
                "${term.title} (${stringResource(R.string.agreement_optional)})"
            }

            AgreementItem(
                modifier = Modifier.padding(vertical = 8.dp),
                title = label,
                checked = term.id in agreedTermIds,
                onShowAgreementDescription = { term.contentUrl?.let(onShowTermsContent) },
                onCheckedChange = { onToggleTerm(term.id) }
            )
        }

        Spacer(Modifier.height(36.dp))

        SignupBottomButton(
            enabled = isRequiredAgreed,
            onClick = onConfirm
        )

        Spacer(Modifier.height(8.dp))
    }
}
