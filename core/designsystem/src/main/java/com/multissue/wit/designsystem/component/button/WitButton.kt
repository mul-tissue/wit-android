package com.multissue.wit.designsystem.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multissue.wit.designsystem.theme.WitTheme

@Composable
fun WitButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = WitTheme.colors.primaryDark,
        contentColor = WitTheme.colors.buttonText,
        disabledContainerColor = WitTheme.colors.disabledButton,
        disabledContentColor = WitTheme.colors.disabledText
    ),
    shape: Shape = RoundedCornerShape(12.dp),
    title: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        onClick = onClick
    ) {
        Text(
            text = title,
            style = WitTheme.typography.titleM
        )
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
