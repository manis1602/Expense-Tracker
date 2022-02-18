package com.manikandan.expensetracker.domain.model

import androidx.annotation.DrawableRes

data class ErrorData(
    @DrawableRes val errorImage: Int,
    val errorTitle: String,
    val solutionText: String
)
