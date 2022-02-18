package com.manikandan.expensetracker.data.repository

import com.manikandan.expensetracker.data.local.ExpenseTrackerDatabase
import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.domain.repository.LocalDataSource
import com.manikandan.expensetracker.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    expenseTrackerDatabase: ExpenseTrackerDatabase
) : LocalDataSource {

    private val userTransactionsDao = expenseTrackerDatabase.userTransactionsDao()

    override fun getAllTransactions(): Flow<List<SingleTransaction>> {
        return userTransactionsDao.getAllTransactions()
    }

    override suspend fun addTransaction(singleTransactions: List<SingleTransaction>) {
        userTransactionsDao.addTransaction(singleTransactions = singleTransactions)
    }

    override suspend fun updateTransaction(newSingleTransaction: SingleTransaction) {
        userTransactionsDao.updateTransaction(newSingleTransaction = newSingleTransaction)
    }

    override suspend fun deleteTransaction(transactionId: String) {
        userTransactionsDao.deleteTransaction(transactionId = transactionId)
    }

    override suspend fun deleteAllTransactions() {
        userTransactionsDao.deleteAllTransactions()
    }

}