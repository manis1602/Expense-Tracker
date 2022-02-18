package com.manikandan.expensetracker.presentation.screens.login

sealed class LoginEvent{
    data class LoginUser(val emailAddress: String, val password: String): LoginEvent()
}
