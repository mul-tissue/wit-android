package com.multissue.wit.core.ui.travel.state

enum class AmPm { AM, PM }

enum class PickerHour(
    val value: Int
) {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
    SEVEN(7), EIGHT(8), NINE(9), TEN(10), ELEVEN(11), TWELVE(12);

    val displayText: String
        get() = value.toString().padStart(2, '0')
}

enum class PickerMinute(
    val value: Int
) {
    ZERO(0),
    THIRTY(30);

    val displayText: String
        get() = value.toString().padStart(2, '0')
}
