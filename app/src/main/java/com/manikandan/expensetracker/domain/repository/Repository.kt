package com.manikandan.expensetracker.domain.repository

import com.manikandan.expensetracker.domain.model.AuthenticationResponse
import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.domain.model.UserTransactionApiResponse
import com.manikandan.expensetracker.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun registerUser(user: User): NetworkResult<AuthenticationResponse>
    suspend fun loginUser(userCredentials: Map<String, String>): NetworkResult<AuthenticationResponse>
    suspend fun resetUserPassword(userCredentials: Map<String, String>): NetworkResult<AuthenticationResponse>
    suspend fun addUserTransaction(
        userId: String,
        singleTransaction: SingleTransaction
    ): NetworkResult<UserTransactionApiResponse>

    suspend fun updateUserTransaction(
        userId: String,
        newTransaction: SingleTransaction
    ): NetworkResult<UserTransactionApiResponse>

    suspend fun deleteUserTransaction(deleteTransactionDetail: Map<String, String>): NetworkResult<UserTransactionApiResponse>
    suspend fun getUserAllTransaction(userId: String): NetworkResult<Flow<List<SingleTransaction>>>
    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
    suspend fun saveUserCredentials(
        user: User
    )
    suspend fun readUserCredentials(): User
    fun readLoginState(): Flow<Boolean>
    suspend fun saveLoginState(isLoginCompleted: Boolean)
}