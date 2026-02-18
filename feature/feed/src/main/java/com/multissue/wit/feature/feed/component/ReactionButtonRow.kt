package com.multissue.wit.feature.feed.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.feed.state.ReactionState
import com.multissue.wit.feature.feed.state.ReactionType

@Composable
fun ReactionButtonRow(
    modifier: Modifier = Modifier,
    onReactionClicked: (ReactionType) -> Unit,
    reactionState: ReactionState
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "반응",
            style = WitTheme.typography.bodyM,
            color = WitTheme.colors.subText
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ReactionItem(
                emoji = "❤\uFE0F",
                count = reactionState.heartReactionCount.toString(),
                selected = reactionState.selectedReaction == ReactionType.HEART,
                onClick = { onReactionClicked(ReactionType.HEART) }
            )
            ReactionItem(
                emoji = "\uD83D\uDC4D",
                count = reactionState.greatReactionCount.toString(),
                selected = reactionState.selectedReaction == ReactionType.GREAT,
                onClick = { onReactionClicked(ReactionType.GREAT) }
            )
            ReactionItem(
                emoji = "\uD83D\uDE06",
                count = reactionState.awesomeReactionCount.toString(),
                selected = reactionState.selectedReaction == ReactionType.AWESOME,
                onClick = { onReactionClicked(ReactionType.AWESOME) }
            )
            ReactionItem(
                emoji = "\uD83D\uDE0D",
                count = reactionState.likeReactionCount.toString(),
                selected = reactionState.selectedReaction == ReactionType.LIKE,
                onClick = { onReactionClicked(ReactionType.LIKE) }
            )
            ReactionItem(
                emoji = "\uD83D\uDD25",
                count = reactionState.fireReactionCount.toString(),
                selected = reactionState.selectedReaction == ReactionType.FIRE,
                onClick = { onReactionClicked(ReactionType.FIRE) }
            )
        }
    }
}