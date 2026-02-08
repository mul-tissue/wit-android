package com.multissue.wit.feature.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.signup.R
import com.multissue.wit.feature.signup.state.GenderType

@Composable
fun BirthAndGenderPage(
    modifier: Modifier = Modifier,
    gender: GenderType,
    birth: String,
    onSelectBirth: () -> Unit,
    onGenderChange: (GenderType) -> Unit,
    onShowAgreementBottomSheet: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 26.dp)
    ) {
        Text(
            text = stringResource(R.string.year_of_birth_and_gender_title),
            style = WitTheme.typography.titleXXL
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.year_of_birth_and_gender_description),
            style = WitTheme.typography.titleM,
            color = WitTheme.colors.subText
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.year_of_birth_title),
            style = WitTheme.typography.titleS,
            color = WitTheme.colors.disabledText
        )

        Spacer(modifier = Modifier.height(6.dp))

        BirthSelectableField(
            modifier = Modifier.fillMaxWidth(),
            birthDate = birth,
            onSelectBirth = onSelectBirth
        )

        Spacer(modifier = Modifier.height(40.dp))

        SelectGender(
            modifier = Modifier.fillMaxWidth(),
            gender = gender,
            onGenderSelected = onGenderChange
        )

        Spacer(modifier = Modifier.weight(1f))

        SignupBottomButton(
            enabled = gender != GenderType.NONE && birth.isNotBlank(),
            onClick = onShowAgreementBottomSheet
        )
    }
}