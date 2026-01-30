package com.multissue.wit.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val blue = Color(0xFF74B1FF)
val blueDarker = Color(0xFF539FFF)
val blueLighter = Color(0xFFD5E8FF)
val gradientColor2 = Color(0xFFF7FBFF)
val gradientColor3 = Color(0xFFEFF6FF)
val mint = Color(0xFF6BDDD2)
val gray100 = Color(0xFFF7F8FE)
val gray200 = Color(0xFFe6e8ec)
val gray300 = Color(0xFFE0E1E6)
val gray600 = Color(0xFF999999)
val gray1000 = Color(0xFF777777)
val gray1200 = Color(0xFF474747)
val black100 = Color(0xFF111111)
val white100 = Color(0xFFFFFFFF)
val white200 = Color(0xFFF8F8F8)
val white300 = Color(0xFFeeeeee)
val white400 = Color(0xFFe9e9e9)
val white500 = Color(0xFFcacaca)
val yellow200 = Color(0xFFFFD21E)
val green200 = Color(0xFF22C55E)
val red100 = Color(0xFFFF5e5e)
val red200 = Color(0xFFEF4444)
val red300 = Color (0xFFF04248)
val darkGray200 = Color(0xFF303746)
val darkGray300 = Color(0xFF242c32)

@Stable
class WitColors(
    primary: Color,
    primaryDark: Color,
    primaryLighter: Color,
    gradientColor2: Color,
    gradientColor3: Color,
    pointColor: Color,
    background: Color,
    containerColor: Color,
    iconTint: Color,
    divider: Color,
    border: Color,
    text: Color,
    grayText: Color,
    buttonText: Color,
    subText: Color,
    disabledText: Color,
    disabledButton: Color,
    disableButtonText: Color,
    error: Color,
    warning: Color,
    success: Color,
    snackBarBackground: Color,
    gray100: Color,
    gray200: Color,
    gray600: Color,
    gray1000: Color,
    gray1200: Color,
    black100: Color,
    white100: Color,
    white200: Color,
    white300: Color,
    white400: Color,
    white500: Color,
    red100: Color,
    red200: Color,
    red300: Color,
    darkGray200: Color,
    darkGray300: Color
) {
    var primary by mutableStateOf(primary)
        private set
    var primaryDark by mutableStateOf(primaryDark)
        private set
    var primaryLighter by mutableStateOf(primaryLighter)
        private set
    var gradientColor2 by mutableStateOf(gradientColor2)
        private set
    var gradientColor3 by mutableStateOf(gradientColor3)
        private set
    var background by mutableStateOf(background)
        private set
    var pointColor by mutableStateOf(pointColor)
        private set
    var containerColor by mutableStateOf(containerColor)
        private set
    var iconTint by mutableStateOf(iconTint)
        private set
    var divider by mutableStateOf(divider)
        private set
    var border by mutableStateOf(border)
        private set
    var text by mutableStateOf(text)
        private set
    var grayText by mutableStateOf(grayText)
        private set
    var buttonText by mutableStateOf(buttonText)
        private set
    var subText by mutableStateOf(subText)
        private set
    var disabledText by mutableStateOf(disabledText)
        private set
    var disabledButton by mutableStateOf(disabledButton)
        private set
    var disableButtonText by mutableStateOf(disableButtonText)
        private set
    var error by mutableStateOf(error)
        private set
    var warning by mutableStateOf(warning)
        private set
    var success by mutableStateOf(success)
        private set
    var snackBarBackground by mutableStateOf(snackBarBackground)
        private set
    var gray100 by mutableStateOf(gray100)
        private set
    var gray200 by mutableStateOf(gray200)
        private set
    var gray600 by mutableStateOf(gray600)
        private set
    var gray1000 by mutableStateOf(gray1000)
        private set
    var gray1200 by mutableStateOf(gray1200)
        private set
    var black100 by mutableStateOf(black100)
        private set
    var white100 by mutableStateOf(white100)
        private set
    var white200 by mutableStateOf(white200)
        private set
    var white300 by mutableStateOf(white300)
        private set
    var white400 by mutableStateOf(white400)
        private set
    var white500 by mutableStateOf(white500)
        private set
    var red100 by mutableStateOf(red100)
        private set
    var red200 by mutableStateOf(red200)
        private set
    var red300 by mutableStateOf(red300)
        private set
    var darkGray200 by mutableStateOf(darkGray200)
        private set
    var darkGray300 by mutableStateOf(darkGray300)
        private set

    fun copy(): WitColors = WitColors(
        primary = primary,
        primaryDark = primaryDark,
        primaryLighter = primaryLighter,
        gradientColor2 = gradientColor2,
        gradientColor3 = gradientColor3,
        pointColor = pointColor,
        background = background,
        containerColor = containerColor,
        iconTint = iconTint,
        divider = divider,
        border = border,
        text = text,
        grayText = grayText,
        buttonText = buttonText,
        subText = subText,
        disabledText = disabledText,
        disabledButton = disabledButton,
        disableButtonText = disableButtonText,
        error = error,
        warning = warning,
        success = success,
        snackBarBackground = snackBarBackground,
        gray100 = gray100,
        gray200 = gray200,
        gray600 = gray600,
        gray1000 = gray1000,
        gray1200 = gray1200,
        black100 = black100,
        white100 = white100,
        white200 = white200,
        white300 = white300,
        white400 = white400,
        white500 = white500,
        red100 = red100,
        red200 = red200,
        red300 = red300,
        darkGray200 = darkGray200,
        darkGray300 = darkGray300
    )

    fun update(other: WitColors) {
        primary = other.primary
        primaryDark = other.primaryDark
        primaryLighter = other.primaryLighter
        gradientColor2 = other.gradientColor2
        gradientColor3 = other.gradientColor3
        pointColor = other.pointColor
        background = other.background
        containerColor = other.containerColor
        iconTint = other.iconTint
        divider = other.divider
        border = other.border
        text = other.text
        grayText = other.grayText
        buttonText = other.buttonText
        subText = other.subText
        disabledText = other.disabledText
        disabledButton = other.disabledButton
        disableButtonText = other.disableButtonText
        error = other.error
        warning = other.warning
        success = other.success
        snackBarBackground = other.snackBarBackground
        gray100 = other.gray100
        gray200 = other.gray200
        gray600 = other.gray600
        gray1000 = other.gray1000
        gray1200 = other.gray1200
        black100 = other.black100
        white100 = other.white100
        white200 = other.white200
        white300 = other.white300
        white400 = other.white400
        white500 = other.white500
        red100 = other.red100
        red200 = other.red200
        red300 = other.red300
        darkGray200 = other.darkGray200
        darkGray300 = other.darkGray300
    }
}

fun lightColorScheme(): WitColors = WitColors(
    primary = blue,
    primaryDark = blueDarker,
    primaryLighter = blueLighter,
    gradientColor2 = gradientColor2,
    gradientColor3 = gradientColor3,
    pointColor = mint,
    background = white100,
    containerColor = white100,
    iconTint = black100,
    divider = gray200,
    border = gray200,
    text = black100,
    grayText = gray1000,
    buttonText = white100,
    subText = gray1000,
    disabledText = gray600,
    disabledButton = gray300,
    disableButtonText = white100,
    error = red200,
    warning = yellow200,
    success = green200,
    snackBarBackground = white100,
    gray100 = gray100,
    gray200 = gray200,
    gray600 = gray600,
    gray1000 = gray1000,
    gray1200 = gray1200,
    black100 = black100,
    white100 = white100,
    white200 = white200,
    white300 = white300,
    white400 = white400,
    white500 = white500,
    red100 = red100,
    red200 = red200,
    red300 = red300,
    darkGray200 = darkGray200,
    darkGray300 = darkGray300
)

/**
 * Dark Theme Color 지원 시 변경
 */
fun darkColorScheme(): WitColors = WitColors(
    primary = blue,
    primaryDark = blueDarker,
    primaryLighter = blueLighter,
    gradientColor2 = gradientColor2,
    gradientColor3 = gradientColor3,
    pointColor = mint,
    background = white100,
    containerColor = white100,
    iconTint = black100,
    divider = gray200,
    border = gray200,
    text = black100,
    grayText = gray1000,
    buttonText = white100,
    subText = gray1000,
    disabledText = gray600,
    disabledButton = gray300,
    disableButtonText = black100,
    error = red200,
    warning = yellow200,
    success = green200,
    snackBarBackground = white100,
    gray100 = gray100,
    gray200 = gray200,
    gray600 = gray600,
    gray1000 = gray1000,
    gray1200 = gray1200,
    black100 = black100,
    white100 = white100,
    white200 = white200,
    white300 = white300,
    white400 = white400,
    white500 = white500,
    red100 = red100,
    red200 = red200,
    red300 = red300,
    darkGray200 = darkGray200,
    darkGray300 = darkGray300
)

