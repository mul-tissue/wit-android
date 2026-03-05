package com.multissue.wit.feature.travel.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.multissue.wit.designsystem.component.dialog.WitDialog
import com.multissue.wit.designsystem.component.dialog.WitDialogDefaultLayout
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.feature.travel.R
import com.multissue.wit.feature.travel.state.ReportType

@Composable
fun TravelReportDialog(
    showDialog: Boolean,
    selectedReportType: ReportType?,
    onSelectReportType: (ReportType) -> Unit,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    WitDialog(
        showDialog = showDialog,
        title = stringResource(R.string.travel_detail_reported_title),
        leftButtonText = stringResource(R.string.travel_detail_report_cancel),
        rightButtonText = stringResource(R.string.travel_detail_report_submit),
        rightButtonColor = WitTheme.colors.error,
        onLeftButtonClick = onDismiss,
        onRightButtonClick = onSubmit,
        bodyContent = {
            TravelReportSelectColumn(
                modifier = Modifier.fillMaxWidth(),
                selectedType = selectedReportType,
                onItemClicked = onSelectReportType
            )
        }
    ) {
        WitDialogDefaultLayout()
    }
}

@Preview
@Composable
private fun TravelReportDialogPreview() {
    WitTheme {
        TravelReportDialog(
            showDialog = true,
            selectedReportType = null,
            onSelectReportType = {},
            onDismiss = {},
            onSubmit = {}
        )
    }
}
