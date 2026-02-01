package com.multissue.wit.feature.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.onboarding.R

@Composable
fun OnboardingPage(
    modifier: Modifier = Modifier,
    page: Int,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(310.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = when (page) {
                    0 -> painterResource(R.drawable.image_onboarding_1)
                    1 -> painterResource(R.drawable.image_onboarding_2)
                    else -> painterResource(R.drawable.image_onboarding_3)
                },
                contentDescription = "온보딩 이미지",
                contentScale = ContentScale.FillWidth
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = when (page) {
                    0 -> stringResource(R.string.onboarding_page_1_title)
                    1 -> stringResource(R.string.onboarding_page_2_title)
                    else -> stringResource(R.string.onboarding_page_3_title)
                },
                style = WitTheme.typography.titleXL,
                color = WitTheme.colors.text
            )
            Text(
                text = when (page) {
                    0 -> stringResource(R.string.onboarding_page_1_content)
                    1 -> stringResource(R.string.onboarding_page_2_content)
                    else -> stringResource(R.string.onboarding_page_3_content)
                },
                style = WitTheme.typography.titleS,
                color = WitTheme.colors.disabledText
            )
        }
    }
}