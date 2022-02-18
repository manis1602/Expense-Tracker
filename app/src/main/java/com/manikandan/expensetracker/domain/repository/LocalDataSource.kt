package com.manikandan.expensetracker.domain.repository

import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getAllTransactions(): Flow<List<SingleTransaction>>

    suspend fun addTransaction(singleTransactions: List<SingleTransaction>)

    suspend fun updateTransaction(newSingleTransaction: SingleTransaction)

    suspend fun deleteTransaction(transactionId: String)

    suspend fun deleteAllTransactions()
}