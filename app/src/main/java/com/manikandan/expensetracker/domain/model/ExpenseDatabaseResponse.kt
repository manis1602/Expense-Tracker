package com.manikandan.expensetracker.domain.model

data class ExpenseDatabaseResponse(
    val success:Boolean,
    val message: String = "OK"
)
