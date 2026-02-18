package com.multissue.wit.feature.signup.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until

object DateUtils {

    fun today(): LocalDate {
        val instant = Clock.System.now()
        val zone = TimeZone.currentSystemDefault()
        return instant.toLocalDateTime(zone).date
    }

    fun currentYear() = today().year
    fun currentMonth() = today().monthNumber
    fun currentDay() = today().dayOfMonth

    fun daysInMonth(year: Int, month: Int): Int {
        val start = LocalDate(year, month, 1)
        val end = start.plus(1, DateTimeUnit.MONTH)
        return start.until(end, DateTimeUnit.DAY)
    }
}