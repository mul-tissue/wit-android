package com.multissue.wit.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.multissue.wit.designsystem.R

val pretendardFontFamily = FontFamily(
    Font(R.font.pretendard_thin, FontWeight.Thin),
    Font(R.font.pretendard_extralight, FontWeight.ExtraLight),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_extrabold, FontWeight.ExtraBold),
    Font(R.font.pretendard_black, FontWeight.Black),
)

@Immutable
data class WitTypography(
    val titleXXL: TextStyle,
    val titleXL: TextStyle,
    val titleL: TextStyle,
    val titleM: TextStyle,
    val titleS: TextStyle,
    val bodyL: TextStyle,
    val bodyM: TextStyle,
    val bodyS: TextStyle,
    val bodyXS: TextStyle,
) {
    companion object {
        fun defaultTypography(): WitTypography {
            return WitTypography(
                titleXXL = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontSize = 25.sp,
                    lineHeight = (25 * 1.32).sp,
                    letterSpacing = (-0.25).sp,
                    fontWeight = FontWeight.SemiBold
                ),
                titleXL = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontSize = 20.sp,
                    lineHeight = (20 * 1.32).sp,
                    letterSpacing = (-0.25).sp,
                    fontWeight = FontWeight.Medium
                ),
                titleL = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontSize = 18.sp,
                    lineHeight = (18 * 1.32).sp,
                    letterSpacing = (-0.25).sp,
                    fontWeight = FontWeight.SemiBold
                ),
                titleM = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontSize = 16.sp,
                    lineHeight = (16 * 1.32).sp,
                    letterSpacing = (-0.25).sp,
                    fontWeight = FontWeight.SemiBold
                ),
                titleS = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontSize = 14.sp,
                    lineHeight = (14 * 1.32).sp,
                    letterSpacing = (-0.25).sp,
                    fontWeight = FontWeight.Medium,
                ),
                bodyL = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontSize = 15.sp,
                    lineHeight = (15 * 1.32).sp,
                    letterSpacing = (-0.25).sp,
                    fontWeight = FontWeight.SemiBold
                ),
                bodyM = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontSize = 12.sp,
                    lineHeight = (12 * 1.32).sp,
                    letterSpacing = (-0.25).sp,
                    fontWeight = FontWeight.Normal
                ),
                bodyS = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontSize = 13.sp,
                    lineHeight = (13 * 1.65).sp,
                    letterSpacing = (-0.25).sp,
                    fontWeight = FontWeight.Normal
                ),
                bodyXS = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontSize = 12.sp,
                    lineHeight = (12 * 1.32).sp,
                    letterSpacing = (-0.25).sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}