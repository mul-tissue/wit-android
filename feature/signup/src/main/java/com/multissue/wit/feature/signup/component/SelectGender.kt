package com.multissue.wit.feature.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.selectable.OutlinedWitSelectableBox
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.signup.R
import com.multissue.wit.feature.signup.state.GenderType

@Composable
fun SelectGender(
    modifier: Modifier = Modifier,
    gender: GenderType,
    onGenderSelected: (GenderType) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.user_gender_title),
            style = WitTheme.typography.titleS,
            color = WitTheme.colors.disabledText
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedWitSelectableBox(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp),
                title = stringResource(R.string.user_gender_female),
                isSelected = gender == GenderType.FEMALE,
                onSelectChanged = { onGenderSelected(GenderType.FEMALE) }
            )

            OutlinedWitSelectableBox(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp),
                title = stringResource(R.string.user_gender_male),
                isSelected = gender == GenderType.MALE,
                onSelectChanged = { onGenderSelected(GenderType.MALE) }
            )
        }
    }
}