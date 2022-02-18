package com.manikandan.expensetracker.domain.model

data class DataStoreUserCredentials(
    val userId: String = "",
    val userEmail: String = "",
    val userPassword: String = "",
    val userGender: String = ""
)
