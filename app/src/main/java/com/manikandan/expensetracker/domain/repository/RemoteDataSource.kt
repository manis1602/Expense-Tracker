package com.manikandan.expensetracker.domain.repository

import com.manikandan.expensetracker.domain.model.AuthenticationResponse
import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.domain.model.UserTransactionApiResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun registerUser(user: User): AuthenticationResponse
    suspend fun loginUser(userCredentials: Map<String, String>): AuthenticationResponse
    suspend fun resetUserPassword(userCredentials: Map<String, String>): AuthenticationResponse

    suspend fun addUserTransaction(
        userId: String,
        singleTransaction: SingleTransaction
    ): UserTransactionApiResponse

    suspend fun updateUserTransaction(
        userId: String,
        newTransaction: SingleTransaction
    ): UserTransactionApiResponse

    suspend fun deleteUserTransaction(
        deleteTransactionDetail: Map<String, String>
    ): UserTransactionApiResponse

    suspend fun getUserAllTransaction(userId: String): List<SingleTransaction>
}