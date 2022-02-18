package com.manikandan.expensetracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: String = "",
    val userName: String,
    var password: String,
    val emailAddress: String,
    val gender: String
)
