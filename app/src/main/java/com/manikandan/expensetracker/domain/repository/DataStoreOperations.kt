package com.manikandan.expensetracker.domain.repository

import com.manikandan.expensetracker.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    // OnBoarding
    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean>

    // Login Status
    suspend fun saveLoginState(isLoginCompleted: Boolean)
    fun readLoginState(): Flow<Boolean>

    //User Credentials
    suspend fun saveUserCredentials(user: User)
    suspend fun readUserCredentials(): User
}