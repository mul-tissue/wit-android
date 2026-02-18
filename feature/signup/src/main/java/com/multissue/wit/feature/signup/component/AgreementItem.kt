package com.multissue.wit.feature.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.signup.R

@Composable
fun AgreementItem(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean,
    onShowAgreementDescription: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .noRippleClickable { onShowAgreementDescription() },
            text = title,
            style = WitTheme.typography.bodyL,
            textDecoration = TextDecoration.Underline
        )

        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    if (checked) WitTheme.colors.primaryDark else WitTheme.colors.disabledButton
                )
                .noRippleClickable {
                    onCheckedChange(!checked)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_checked),
                contentDescription = "체크",
                tint = WitTheme.colors.iconTintConverse,
            )
        }
    }
}