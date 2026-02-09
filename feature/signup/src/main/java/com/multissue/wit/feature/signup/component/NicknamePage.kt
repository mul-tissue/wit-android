package com.multissue.wit.feature.signup.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.component.textfield.WitUnderlinedTextField
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.signup.R
import kotlinx.coroutines.launch

@Composable
fun NicknamePage(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    isCheckedNickname: Boolean,
    isNickNameDuplicated: Boolean?,
    nickName: String,
    onNickNameChange: (String) -> Unit,
    onCheckNickNameDuplicate: () -> Unit
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .padding(horizontal = 26.dp)
    ) {
        Text(
            text = stringResource(R.string.nickname_title),
            style = WitTheme.typography.titleXXL
        )

        Spacer(modifier = Modifier.height(80.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WitUnderlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                hint = stringResource(R.string.nickname_placeholder),
                value = nickName,
                onValueChange = onNickNameChange,
                trailingIcon = {
                    WitButton(
                        modifier = Modifier
                            .width(62.dp)
                            .height(32.dp),
                        enabled = nickName.isNotBlank(),
                        title = stringResource(R.string.check_duplicate),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(8.dp),
                        textStyle = WitTheme.typography.bodyXS
                    ) {
                        onCheckNickNameDuplicate()
                    }
                }
            )

            NicknameDuplicateMessage(
                showMessage = isNickNameDuplicated != null,
                color = if (isNickNameDuplicated == true) WitTheme.colors.error else WitTheme.colors.success,
                message = stringResource(if (isNickNameDuplicated == true) R.string.nickname_duplicated else R.string.nickname_available)
            )

            Spacer(modifier = Modifier.weight(1f))

            SignupBottomButton(
                modifier = Modifier.padding(bottom = 22.dp),
                enabled = isCheckedNickname && nickName.isNotBlank(),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1,
                            animationSpec = tween(
                                durationMillis = 450,
                                easing = FastOutSlowInEasing
                            )
                        )
                    }
                }
            )
        }
    }
}