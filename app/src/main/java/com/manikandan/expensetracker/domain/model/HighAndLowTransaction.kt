package com.manikandan.expensetracker.domain.model

import com.manikandan.expensetracker.presentation.screens.home.util.TransactionType

data class HighAndLowTransaction(
    val transactionType: TransactionType,
    val high: SingleTransaction? = null,
    val low: SingleTransaction? = null
)
