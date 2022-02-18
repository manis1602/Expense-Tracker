package com.manikandan.expensetracker.data.remote

import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.domain.model.UserTransactionApiResponse
import retrofit2.http.*

interface ExpenseTrackerUserTransactionApi {

    @POST("/user/transaction/add")
    suspend fun addUserTransaction(
        @Query("user_id") userId: String,
        @Body singleTransaction: SingleTransaction
    ): UserTransactionApiResponse

    @POST("/user/transaction/update")
    suspend fun updateUserTransaction(
        @Query("user_id") userId: String,
        @Body newTransaction: SingleTransaction
    ): UserTransactionApiResponse

    @GET("/user/transaction/delete")
    suspend fun deleteUserTransaction(
        @QueryMap deleteTransactionDetail: Map<String, String>
    ): UserTransactionApiResponse

    @GET("/user/transaction/all")
    suspend fun getUserAllTransaction(@Query("user_id") userId: String): UserTransactionApiResponse
}