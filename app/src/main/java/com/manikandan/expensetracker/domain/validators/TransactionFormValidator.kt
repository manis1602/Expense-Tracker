package com.manikandan.expensetracker.domain.validators

import com.manikandan.expensetracker.domain.model.InputValidationResponse
import com.manikandan.expensetracker.domain.model.SingleTransaction

object TransactionFormValidator {
    fun addTransactionFormValidation(singleTransaction: SingleTransaction): InputValidationResponse {
        val errorMessage = when {
            singleTransaction.transactionTitle.isEmpty() || singleTransaction.transactionCategory.isEmpty() ||
                    singleTransaction.transactionMode.isEmpty() -> "Some fields are empty!"
            singleTransaction.transactionDate == 0L -> "Please pick a date!"
            singleTransaction.transactionAmount == 0 -> "Amount can not be empty or 0 !"
            else -> ""

        }
        return if (errorMessage == ""){
            InputValidationResponse(
                success = true
            )
        }else{
            InputValidationResponse(success = false, errorMessage = errorMessage)
        }
    }
}