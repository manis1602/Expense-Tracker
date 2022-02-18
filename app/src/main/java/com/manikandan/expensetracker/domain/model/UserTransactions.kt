package com.manikandan.expensetracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserTransactions(
    val userId: String,
    val transactions: MutableList<SingleTransaction>
)
