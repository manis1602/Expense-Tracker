package com.manikandan.expensetracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserTransactionApiResponse(
    val success: Boolean,
    val message: String = "Ok",
    val userId: String = "",
    val transactionId: String = "",
    val allTransactions: List<SingleTransaction> = emptyList()
)
