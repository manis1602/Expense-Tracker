package com.manikandan.expensetracker.presentation.screens.home.util

import java.text.SimpleDateFormat
import java.util.*

fun dateFormatter(date: Long?, pattern: String): String? {
    date?.let {
        val formatter = SimpleDateFormat(pattern, Locale.US)
        return formatter.format(date)
    }
    return null
}