package com.manikandan.expensetracker.data.local

import androidx.room.*
import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

@Dao
interface UserTransactionsDao {

    @Query("SELECT * FROM USER_TRANSACTIONS_TABLE ORDER BY transactionDate DESC")
    fun getAllTransactions(): Flow<List<SingleTransaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(singleTransactions: List<SingleTransaction>)

    @Update
    suspend fun updateTransaction(newSingleTransaction: SingleTransaction)

    @Query("DELETE FROM USER_TRANSACTIONS_TABLE WHERE transactionId = :transactionId")
    suspend fun deleteTransaction(transactionId: String)

    @Query("DELETE FROM USER_TRANSACTIONS_TABLE")
    suspend fun deleteAllTransactions()
}