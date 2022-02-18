package com.manikandan.expensetracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationResponse(
    val success: Boolean,
    val message: String = "Ok",
    val user: User?
)
