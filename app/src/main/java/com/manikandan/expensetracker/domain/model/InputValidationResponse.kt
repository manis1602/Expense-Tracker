package com.manikandan.expensetracker.domain.model

data class InputValidationResponse(
    val success: Boolean,
    val errorMessage: String = "No Error!"
)