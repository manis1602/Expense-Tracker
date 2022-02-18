package com.manikandan.expensetracker.presentation.screens.home

import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.presentation.screens.home.util.TransactionType

sealed class AddUpdateTransactionEvent {
    data class AddTransaction(val userId: String, val singleTransaction: SingleTransaction): AddUpdateTransactionEvent()
    data class UpdateTransaction(val userId: String, val singleTransaction: SingleTransaction): AddUpdateTransactionEvent()
    data class UpdateTextFieldValue(val textField: String, val value: String): AddUpdateTransactionEvent()
    data class UpdateTransactionType(val transactionType: TransactionType): AddUpdateTransactionEvent()
    data class GetCategoriesList(val transactionType: TransactionType): AddUpdateTransactionEvent()
    object ClearAllTextFieldValues: AddUpdateTransactionEvent()
    object ResetAddUpdateTransactionForm: AddUpdateTransactionEvent()
}