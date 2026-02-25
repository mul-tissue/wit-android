package com.multissue.wit.feature.upload

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.component.snackbar.WitSnackBarHost
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.addFocusCleaner
import com.multissue.wit.feature.upload.component.DualCameraScreen
import com.multissue.wit.feature.upload.component.NoticeBottomSheet
import com.multissue.wit.feature.upload.component.SpH
import com.multissue.wit.feature.upload.component.UploadContentColumn
import com.multissue.wit.feature.upload.component.UploadTopAppBar
import com.multissue.wit.feature.upload.permission.CameraPermission
import com.multissue.wit.feature.upload.state.PageType
import com.multissue.wit.feature.upload.state.UploadUiIntent
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun UploadScreen(
    modifier: Modifier = Modifier,
    viewModel: UploadViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler(enabled = uiState.pageType == PageType.WRITE) {
        viewModel.onIntent(UploadUiIntent.NavigateCamera)
    }

    UploadScreen(
        pageTypeState = uiState.pageType,
        capturedImageUri = uiState.capturedImageUri,
        capturedLocation = uiState.capturedLocation,
        capturedAt = uiState.capturedAt,
        contentText = uiState.contentText,
        noticeBottomSheetState = uiState.noticeBottomSheetState,
        snackbarHostState = snackbarHostState,
        onIntent = viewModel::onIntent
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun UploadScreen(
    modifier: Modifier = Modifier,
    pageTypeState: PageType,
    capturedImageUri: Uri?,
    capturedLocation: String?,
    capturedAt: Long?,
    contentText: String,
    noticeBottomSheetState: Boolean,
    snackbarHostState: SnackbarHostState,
    onIntent: (UploadUiIntent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager),
        topBar = {
            if (pageTypeState == PageType.WRITE) {
                UploadTopAppBar(
                    onBackButtonClicked = { onIntent(UploadUiIntent.NavigateCamera) }
                )
            }
        },
        snackbarHost = {
            WitSnackBarHost(
                hostState = snackbarHostState,
            )
        }
    ) { paddingValues ->
        when (pageTypeState) {
            PageType.WRITE -> {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .background(WitTheme.background.color)
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding() + 24.dp,
                            start = 26.dp,
                            end = 26.dp,
                            bottom = 24.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(3 / 4f)
                                .clip(RoundedCornerShape(16.dp)),
                            model = capturedImageUri,
                            contentDescription = "피드 이미지"
                        )
                        SpH(18.dp)
                        UploadContentColumn(
                            modifier = Modifier.fillMaxWidth(),
                            location = capturedLocation ?: "",
                            date = capturedAt?.let { timestamp ->
                                val date = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(Date(timestamp))
                                val time = SimpleDateFormat("h:mm a", Locale.ENGLISH).format(Date(timestamp))
                                "$date · $time"
                            } ?: "",
                            content = contentText,
                            onContentValueChanged = { onIntent(UploadUiIntent.ContentTextChange(it)) },
                        )
                    }
                    SpH(16.dp)
                    WitButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = {
                            focusManager.clearFocus()
                            onIntent(UploadUiIntent.DoneButtonClick)
                        },
                        title = "작성 완료",
                        enabled = contentText.isNotEmpty()
                    )
                }
            }
            PageType.CAMERA -> {
                Column(
                    modifier = Modifier
                        .background(color = WitTheme.colors.black100)
                        .padding(top = 110.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    CameraPermission {
                        DualCameraScreen(
                            onCaptureFinished = { uri, location, capturedAt ->
                                onIntent(UploadUiIntent.CapturePhoto(uri, location, capturedAt))
                            }
                        )
                    }
                }
            }
        }
        NoticeBottomSheet(
            visible = noticeBottomSheetState,
            onConfirmButtonClicked = { onIntent(UploadUiIntent.SheetConfirmButtonClick) },
            onDismiss = { onIntent(UploadUiIntent.SheetCloseButtonClick) },
        )
    }
}
