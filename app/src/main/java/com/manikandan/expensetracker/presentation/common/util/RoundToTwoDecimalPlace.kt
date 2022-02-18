package com.manikandan.expensetracker.presentation.common.util

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Float.roundToTwoDecimalPlace(): Float {
    val decimalFormatter = DecimalFormat("#.##", DecimalFormatSymbols(Locale.ENGLISH)).apply {
        roundingMode = RoundingMode.HALF_UP
    }
    return decimalFormatter.format(this).toFloat()
}