package com.multissue.wit.designsystem.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.R
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun WitButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = WitTheme.colors.primaryDark,
        contentColor = WitTheme.colors.buttonText,
        disabledContainerColor = WitTheme.colors.disabledButton,
        disabledContentColor = WitTheme.colors.background
    ),
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    title: String,
    textStyle: TextStyle = WitTheme.typography.titleM,
    enabled: Boolean = true,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        contentPadding = contentPadding,
        onClick = onClick
    ) {
        leadingIcon()
        Text(
            text = title,
            style = textStyle
        )
        trailingIcon()
    }
}

@Preview
@Composable
fun PreviewWitButtonPreview() {
    WitTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WitButton(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(56.dp),
                onClick = {},
                title = "Button Title",
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.icon_profile),
                        contentDescription = "",
                        tint = WitTheme.colors.buttonText
                    )
                }
            )
            WitButton(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(56.dp),
                onClick = {},
                title = "Button Title"
            )
            WitButton(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(56.dp),
                onClick = {},
                enabled = false,
                title = "Button Title Disabled"
            )
        }
    }
}
