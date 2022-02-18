package com.manikandan.expensetracker.presentation.screens.register

import com.manikandan.expensetracker.domain.model.User

sealed class RegisterEvent {
    data class RegisterUser(val user: User): RegisterEvent()
}
