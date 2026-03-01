package com.multissue.wit.feature.mypage.component.profileedit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.multissue.wit.designsystem.component.button.WitButton
import com.multissue.wit.designsystem.component.textfield.WitUnderlinedTextField
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.mypage.ProfileEditViewModel
import com.multissue.wit.feature.mypage.R
import com.multissue.wit.feature.mypage.component.SpH
import com.multissue.wit.feature.mypage.component.SpW
import com.multissue.wit.feature.mypage.state.profileedit.NicknameCheckState
import com.multissue.wit.feature.mypage.state.profileedit.ProfileEditUiIntent

@Composable
fun ProfileEditContent(
    modifier: Modifier = Modifier,
    viewModel: ProfileEditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileEditContent(
        modifier = modifier,
        profileImageUrl = uiState.profileImageUrl,
        nickname = uiState.nickname,
        nicknameCheckState = uiState.nicknameCheckState,
        showPhotoBottomSheet = uiState.showPhotoBottomSheet,
        onIntent = viewModel::onIntent,
    )
}

@Composable
internal fun ProfileEditContent(
    modifier: Modifier = Modifier,
    profileImageUrl: String,
    nickname: String,
    nicknameCheckState: NicknameCheckState,
    showPhotoBottomSheet: Boolean,
    onIntent: (ProfileEditUiIntent) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(WitTheme.background.color),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SpH(32.dp)

            // 프로필 이미지 + 카메라 뱃지
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .noRippleClickable { onIntent(ProfileEditUiIntent.ClickProfileImage) },
                contentAlignment = Alignment.BottomEnd,
            ) {
                if (profileImageUrl.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(WitTheme.colors.gray200),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(com.multissue.wit.designsystem.R.drawable.icon_user),
                            contentDescription = null,
                            tint = WitTheme.colors.disabledText,
                        )
                    }
                } else {
                    AsyncImage(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape),
                        model = profileImageUrl,
                        contentDescription = "프로필 이미지",
                        contentScale = ContentScale.Crop,
                    )
                }
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(WitTheme.colors.gray1000),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.icon_camera),
                        contentDescription = "사진 변경",
                        tint = WitTheme.colors.white100,
                    )
                }
            }

            SpH(32.dp)

            // 닉네임 입력
            WitUnderlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = nickname,
                onValueChange = { onIntent(ProfileEditUiIntent.UpdateNickname(it)) },
                hint = "닉네임 입력 (2-12자)",
                trailingIcon = {
                    WitButton(
                        modifier = Modifier
                            .width(72.dp)
                            .height(34.dp),
                        title = "중복 확인",
                        shape = RoundedCornerShape(8.dp),
                        enabled = nickname.isNotEmpty(),
                        onClick = { onIntent(ProfileEditUiIntent.ClickNicknameCheck) },
                    )
                },
            )

            SpH(8.dp)

            // 닉네임 상태 메시지
            when (nicknameCheckState) {
                NicknameCheckState.AVAILABLE -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "사용 가능한 닉네임입니다",
                        style = WitTheme.typography.bodyM,
                        color = WitTheme.colors.success,
                    )
                }
                NicknameCheckState.UNAVAILABLE -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "사용할 수 없는 닉네임입니다",
                        style = WitTheme.typography.bodyM,
                        color = WitTheme.colors.error,
                    )
                }
                NicknameCheckState.NONE -> SpH(0.dp)
            }
        }

        // 저장 버튼 (하단 고정)
        WitButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 20.dp)
                .height(52.dp)
                .align(Alignment.BottomCenter),
            title = "저장",
            enabled = nicknameCheckState == NicknameCheckState.AVAILABLE,
            onClick = { onIntent(ProfileEditUiIntent.ClickSave) },
        )
    }

    ProfilePhotoBottomSheet(
        visible = showPhotoBottomSheet,
        onSelectFromAlbum = { onIntent(ProfileEditUiIntent.ClickSelectFromAlbum) },
        onDeleteProfilePhoto = { onIntent(ProfileEditUiIntent.ClickDeleteProfilePhoto) },
        onDismiss = { onIntent(ProfileEditUiIntent.DismissPhotoBottomSheet) },
    )
}
