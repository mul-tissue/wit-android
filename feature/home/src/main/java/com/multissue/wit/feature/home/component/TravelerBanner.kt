package com.multissue.wit.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.home.R

@Composable
fun TravelerBanner(
    modifier: Modifier = Modifier,
    travelerCount: Int,
) {
    Box(
        modifier = modifier
            .background(
                color = WitTheme.colors.white100.copy(alpha = 0.85f),
                shape = RoundedCornerShape(50),
            )
            .padding(horizontal = 16.dp, vertical = 6.dp),
    ) {
        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.home_banner_prefix))
                append(" ")
                withStyle(
                    SpanStyle(
                        color = WitTheme.colors.primaryDark,
                        fontWeight = FontWeight.Bold,
                    ),
                ) {
                    append(stringResource(R.string.home_banner_highlight, travelerCount))
                }
                append(stringResource(R.string.home_banner_suffix))
            },
            style = WitTheme.typography.bodyM,
            color = WitTheme.colors.text,
        )
    }
}
