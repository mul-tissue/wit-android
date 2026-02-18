package com.multissue.wit.feature.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.feed.component.MapTopAppBar
import com.multissue.wit.feature.feed.component.ReactionButtonRow
import com.multissue.wit.feature.feed.state.FeedState
import com.multissue.wit.feature.feed.state.ReactionState
import com.multissue.wit.feature.feed.state.ReactionType
import com.multissue.wit.feature.feed.state.UserState

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val feedUiState by viewModel.uiState.collectAsStateWithLifecycle()

    FeedScreen(
        userState = feedUiState.user,
        reactionState = feedUiState.reactionState,
        feedState = feedUiState.feedState,
        onReactionItemClicked = viewModel::onReactionItemClick
    )
}

@Composable
internal fun FeedScreen(
    modifier: Modifier = Modifier,
    userState: UserState,
    reactionState: ReactionState,
    feedState: FeedState,
    onReactionItemClicked: (ReactionType) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            MapTopAppBar(
                username = userState.username,
                userThumbnail = userState.userThumbnailUrl,
                onBackButtonClicked = {  },
                onMoreButtonClicked = {  },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(WitTheme.background.color)
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() + 24.dp,
                    start = 26.dp,
                    end = 26.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3/4f)
                    .clip(RoundedCornerShape(16.dp)),
                model = feedState.imageUrl,
                contentDescription = "피드 이미지"
            )
            Column(
                modifier = Modifier
                    .padding(top = 18.dp)
                    .fillMaxWidth()
            ) {
                Column (
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 14.dp),
                thickness = 1.dp,
                color = WitTheme.colors.gray200
            )
            ReactionButtonRow(
                modifier = Modifier.fillMaxWidth(),
                onReactionClicked = onReactionItemClicked,
                reactionState = reactionState,
            )
        }
    }
}