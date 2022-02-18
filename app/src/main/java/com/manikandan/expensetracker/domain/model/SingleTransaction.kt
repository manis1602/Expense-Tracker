package com.manikandan.expensetracker.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manikandan.expensetracker.utils.Constants.USER_TRANSACTIONS_TABLE_NAME
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Entity(tableName = USER_TRANSACTIONS_TABLE_NAME)
data class SingleTransaction(
    @PrimaryKey(autoGenerate = false)
    val transactionId: String,
    var transactionTitle: String,
    var transactionCategory: String,
    var transactionIsIncome: Boolean = true,
    var transactionAmount: Int,
    var transactionDate: Long,
    var transactionMode: String
)
