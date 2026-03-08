package com.multissue.wit.feature.mypage.component.profileedit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme
import com.multissue.wit.designsystem.util.noRippleClickable
import com.multissue.wit.feature.mypage.component.SpH

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePhotoBottomSheet(
    visible: Boolean,
    onSelectFromAlbum: () -> Unit,
    onDeleteProfilePhoto: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (!visible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = WitTheme.colors.background,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        contentWindowInsets = { BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom) },
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(28.dp)
                    .height(2.dp)
                    .background(WitTheme.colors.disabledText),
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BottomSheetButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                text = "앨범에서 선택",
                textColor = WitTheme.colors.subText,
                onClick = onSelectFromAlbum
            )
            SpH(4.dp)
            BottomSheetButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                text = "프로필 사진 삭제하기",
                textColor = WitTheme.colors.error,
                onClick = onDeleteProfilePhoto
            )
        }
    }
}
