package com.manikandan.expensetracker.presentation.screens.home


sealed class HomeEvent {
    data class GetUsersAllTransactions(val userId: String): HomeEvent()
    data class DeleteTransaction(val deleteTransactionDetail: Map<String, String>): HomeEvent()
    data class UpdateSelectedYear(val year: Int): HomeEvent()
}
