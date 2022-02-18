package com.manikandan.expensetracker.domain.model

data class OverallStats(
    val totalIncome: Float = 0f,
    val totalExpense: Float = 0f,
    val fractionalTotalIncome: Float = 0f,
    val fractionalTotalExpense: Float = 0f,
    val profitLossPercent: Float = 0f
)
