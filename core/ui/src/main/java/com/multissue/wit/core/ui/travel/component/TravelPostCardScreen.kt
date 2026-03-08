package com.multissue.wit.core.ui.travel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.core.ui.R
import com.multissue.wit.core.ui.travel.state.TravelItemState
import com.multissue.wit.designsystem.R as DesignR
import com.multissue.wit.designsystem.component.topbar.WitCenterAlignedTopAppBar
import com.multissue.wit.designsystem.theme.WitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelPostCardScreen(
    travelItem: TravelItemState,
    onBack: () -> Unit,
    onMoreClick: () -> Unit = {},
    mapPreview: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WitTheme.colors.gradientBackground)
            .verticalScroll(rememberScrollState()),
    ) {
        WitCenterAlignedTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = travelItem.activityType,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = WitTheme.colors.text,
                navigationIconContentColor = WitTheme.colors.iconTint,
                actionIconContentColor = WitTheme.colors.iconTint,
            ),
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(DesignR.drawable.icon_back),
                        contentDescription = "뒤로 가기",
                        tint = WitTheme.colors.iconTint,
                    )
                }
            },
            actions = {
                IconButton(onClick = onMoreClick) {
                    Icon(
                        painter = painterResource(R.drawable.icon_more),
                        contentDescription = "더보기",
                        tint = WitTheme.colors.iconTint,
                    )
                }
            },
        )

        TravelPostCard(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            title = travelItem.title,
            dayDiff = travelItem.dayDiff,
            participantsText = stringResource(
                R.string.travel_participant,
                travelItem.currentParticipants,
                travelItem.maxParticipants
            ),
            meetingDateText = travelItem.meetingDate,
            locationName = travelItem.location,
            badges = buildList {
                if (travelItem.ageCondition.isNotEmpty()) add(travelItem.ageCondition)
                if (travelItem.genderCondition.isNotEmpty()) add(travelItem.genderCondition)
                if (travelItem.maxParticipants > 0) add("${travelItem.maxParticipants}명 이내")
            },
            authorName = travelItem.authorName,
            authorImageUrl = travelItem.authorAvatarUrl.ifEmpty { null },
            content = travelItem.content,
            locationAddress = travelItem.address.ifEmpty { stringResource(R.string.post_confirm_section_location_undecide) },
            mapPreview = mapPreview,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelPostCardScreenPreview() {
    WitTheme {
        TravelPostCardScreen(
            travelItem = TravelItemState(
                id = 1,
                title = "루브르 미술관 투어",
                activityType = "전시/미술관",
                dayDiff = 0,
                currentParticipants = 1,
                maxParticipants = 3,
                meetingDate = "3월 25일 · 06:00 PM",
                location = "루브르 미술관",
                content = "루브르 미술관 함께 투어할 동행 구해요",
                authorName = "작성자",
                ageCondition = "20대",
                genderCondition = "무관",
            ),
            onBack = {},
        )
    }
}
