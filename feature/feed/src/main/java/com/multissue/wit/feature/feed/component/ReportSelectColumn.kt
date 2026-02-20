package com.multissue.wit.feature.feed.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.feature.feed.state.ReportType

@Composable
fun ReportSelectColumn(
    modifier: Modifier = Modifier,
    selectedType: ReportType?,
    onItemClicked: (ReportType) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectableRow(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth(),
            selected = selectedType == ReportType.SEXUAL_CONTENT,
            text = ReportType.SEXUAL_CONTENT.text,
            onClick = { onItemClicked(ReportType.SEXUAL_CONTENT) },
        )
        SelectableRow(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth(),
            selected = selectedType == ReportType.VIOLENT_OR_HATEFUL_CONTENT,
            text = ReportType.VIOLENT_OR_HATEFUL_CONTENT.text,
            onClick = { onItemClicked(ReportType.VIOLENT_OR_HATEFUL_CONTENT) },
        )
        SelectableRow(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth(),
            selected = selectedType == ReportType.HATE_OR_ABUSIVE_CONTENT,
            text = ReportType.HATE_OR_ABUSIVE_CONTENT.text,
            onClick = { onItemClicked(ReportType.HATE_OR_ABUSIVE_CONTENT) },
        )
        SelectableRow(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth(),
            selected = selectedType == ReportType.HARMFUL_OR_DANGEROUS_ACTS,
            text = ReportType.HARMFUL_OR_DANGEROUS_ACTS.text,
            onClick = { onItemClicked(ReportType.HARMFUL_OR_DANGEROUS_ACTS) },
        )
        SelectableRow(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth(),
            selected = selectedType == ReportType.SPAM_OR_MISLEADING,
            text = ReportType.SPAM_OR_MISLEADING.text,
            onClick = { onItemClicked(ReportType.SPAM_OR_MISLEADING) },
        )
    }
}