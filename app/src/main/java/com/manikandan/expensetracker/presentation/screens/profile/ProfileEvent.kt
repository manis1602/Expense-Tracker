package com.manikandan.expensetracker.presentation.screens.profile

sealed class ProfileEvent{
    data class GetUsersAllTransactions(val userId: String) : ProfileEvent()
    object LogoutUser: ProfileEvent()
}
