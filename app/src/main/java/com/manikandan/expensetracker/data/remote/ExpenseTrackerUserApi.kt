package com.manikandan.expensetracker.data.remote

import com.manikandan.expensetracker.domain.model.AuthenticationResponse
import com.manikandan.expensetracker.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ExpenseTrackerUserApi {

    @POST("/user/register")
    suspend fun registerUser(@Body user: User): AuthenticationResponse

    @GET("/user/login")
    suspend fun loginUser(@QueryMap userCredentials: Map<String, String>): AuthenticationResponse

    @GET("/user/resetPassword")
    suspend fun resetUserPassword(@QueryMap userCredentials: Map<String, String>): AuthenticationResponse

}