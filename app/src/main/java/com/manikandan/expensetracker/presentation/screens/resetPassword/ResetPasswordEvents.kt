package com.manikandan.expensetracker.presentation.screens.resetPassword

sealed class ResetPasswordEvents{
    data class ResetPassword(val emailAddress: String, val password: String, val confirmPassword: String): ResetPasswordEvents()
}
