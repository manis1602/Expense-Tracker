package com.manikandan.expensetracker.data.repository

import com.manikandan.expensetracker.data.remote.ExpenseTrackerUserApi
import com.manikandan.expensetracker.data.remote.ExpenseTrackerUserTransactionApi
import com.manikandan.expensetracker.domain.model.AuthenticationResponse
import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.domain.model.UserTransactionApiResponse
import com.manikandan.expensetracker.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImpl(
    private val expenseTrackerUserApi: ExpenseTrackerUserApi,
    private val expenseTrackerUserTransactionApi: ExpenseTrackerUserTransactionApi
) : RemoteDataSource {
    override suspend fun registerUser(user: User): AuthenticationResponse {
        return expenseTrackerUserApi.registerUser(user = user)
    }

    override suspend fun loginUser(userCredentials: Map<String, String>): AuthenticationResponse {
        return expenseTrackerUserApi.loginUser(userCredentials = userCredentials)
    }

    override suspend fun resetUserPassword(userCredentials: Map<String, String>): AuthenticationResponse {
        return expenseTrackerUserApi.resetUserPassword(userCredentials = userCredentials)
    }

    override suspend fun addUserTransaction(
        userId: String,
        singleTransaction: SingleTransaction
    ): UserTransactionApiResponse {
        return expenseTrackerUserTransactionApi.addUserTransaction(
            userId = userId,
            singleTransaction = singleTransaction
        )
    }

    override suspend fun updateUserTransaction(
        userId: String,
        newTransaction: SingleTransaction
    ): UserTransactionApiResponse {
        return expenseTrackerUserTransactionApi.updateUserTransaction(
            userId = userId,
            newTransaction = newTransaction
        )
    }

    override suspend fun deleteUserTransaction(deleteTransactionDetail: Map<String, String>): UserTransactionApiResponse {
        return expenseTrackerUserTransactionApi.deleteUserTransaction(
            deleteTransactionDetail = deleteTransactionDetail
        )
    }

    override suspend fun getUserAllTransaction(userId: String): List<SingleTransaction> {
        val response = expenseTrackerUserTransactionApi.getUserAllTransaction(userId = userId)
        return response.allTransactions
    }
}