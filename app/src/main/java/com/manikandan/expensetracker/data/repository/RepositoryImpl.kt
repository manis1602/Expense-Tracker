package com.manikandan.expensetracker.data.repository

import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.*
import com.manikandan.expensetracker.domain.repository.DataStoreOperations
import com.manikandan.expensetracker.domain.repository.LocalDataSource
import com.manikandan.expensetracker.domain.repository.RemoteDataSource
import com.manikandan.expensetracker.domain.repository.Repository
import com.manikandan.expensetracker.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dataStoreOperations: DataStoreOperations
) : Repository {

    override suspend fun registerUser(user: User): NetworkResult<AuthenticationResponse> {
        val response = try {
            remoteDataSource.registerUser(user = user)
        } catch (exception: Exception) {
            val errorData = parseException(exception = exception)
            return NetworkResult.Error(errorData = errorData)
        }
        return NetworkResult.Success(response)
    }

    override suspend fun loginUser(userCredentials: Map<String, String>): NetworkResult<AuthenticationResponse> {
        val response = try {
            remoteDataSource.loginUser(userCredentials = userCredentials)
        } catch (exception: Exception) {
            val errorData = parseException(exception = exception)
            return NetworkResult.Error(errorData = errorData)
        }
        return NetworkResult.Success(response)
    }

    override suspend fun resetUserPassword(userCredentials: Map<String, String>): NetworkResult<AuthenticationResponse> {
        val response = try {
            remoteDataSource.resetUserPassword(userCredentials = userCredentials)
        } catch (exception: Exception) {
            val errorData = parseException(exception = exception)
            return NetworkResult.Error(errorData = errorData)
        }
        return NetworkResult.Success(response)
    }

    override suspend fun addUserTransaction(
        userId: String,
        singleTransaction: SingleTransaction
    ): NetworkResult<UserTransactionApiResponse> {
        val response = try {
            localDataSource.addTransaction(singleTransactions = listOf(singleTransaction))
            remoteDataSource.addUserTransaction(
                userId = userId,
                singleTransaction = singleTransaction
            )
        } catch (exception: Exception) {
            val errorData = parseException(exception = exception)
            return NetworkResult.Error(errorData = errorData)
        }
        return NetworkResult.Success(response)
    }

    override suspend fun updateUserTransaction(
        userId: String,
        newTransaction: SingleTransaction
    ): NetworkResult<UserTransactionApiResponse> {
        val response = try {
            localDataSource.updateTransaction(newSingleTransaction = newTransaction)
            remoteDataSource.updateUserTransaction(
                userId = userId,
                newTransaction = newTransaction
            )
        } catch (exception: Exception) {
            val errorData = parseException(exception = exception)
            return NetworkResult.Error(errorData = errorData)
        }
        return NetworkResult.Success(response)
    }

    override suspend fun deleteUserTransaction(deleteTransactionDetail: Map<String, String>): NetworkResult<UserTransactionApiResponse> {
        val response = try {
            localDataSource.deleteTransaction(transactionId = deleteTransactionDetail["transaction_id"]!!)
            remoteDataSource.deleteUserTransaction(
                deleteTransactionDetail = deleteTransactionDetail
            )
        } catch (exception: Exception) {
            val errorData = parseException(exception = exception)
            return NetworkResult.Error(errorData = errorData)
        }
        return NetworkResult.Success(response)
    }

    private suspend fun deleteAllUsersTransactions() {
        localDataSource.deleteAllTransactions()
    }

    override suspend fun getUserAllTransaction(userId: String): NetworkResult<Flow<List<SingleTransaction>>> {
        val allTransactions = try {
            deleteAllUsersTransactions()
            remoteDataSource.getUserAllTransaction(userId = userId)
        } catch (exception: Exception) {
            val errorData = parseException(exception = exception)
            return NetworkResult.Error(errorData = errorData)

        }
        localDataSource.addTransaction(singleTransactions = allTransactions)
        val response = localDataSource.getAllTransactions()
        return NetworkResult.Success(response)
    }

    // Data Store operations

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStoreOperations.saveOnBoardingState(completed = completed)
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStoreOperations.readOnBoardingState()
    }

    override suspend fun saveUserCredentials(
        user: User
    ) {
        dataStoreOperations.saveUserCredentials(
            user = user
        )
    }

    override suspend fun readUserCredentials(): User {
        return dataStoreOperations.readUserCredentials()
    }

    override fun readLoginState(): Flow<Boolean> {
        return dataStoreOperations.readLoginState()
    }

    override suspend fun saveLoginState(isLoginCompleted: Boolean) {
        dataStoreOperations.saveLoginState(isLoginCompleted = isLoginCompleted)
    }

    private fun parseException(exception: Exception): ErrorData {
        return when (exception) {
            is UnknownHostException -> {
                ErrorData(
                    errorImage = R.drawable.ic_no_internet,
                    errorTitle = "No Internet Available!!",
                    solutionText = "Check your internet connection."
                )
            }
            is SocketTimeoutException -> {
                ErrorData(
                    errorImage = R.drawable.ic_server_unavailable,
                    errorTitle = "Server Unavailable !!",
                    solutionText = "Try again after sometime."
                )
            }
            else -> {
                ErrorData(
                    errorImage = R.drawable.ic_error,
                    errorTitle = "Unknown Error !!",
                    solutionText = "Close and relaunch app"
                )
            }
        }
    }
}