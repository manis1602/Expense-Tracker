package com.manikandan.expensetracker.domain.usecases.update_transaction

import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.domain.model.UserTransactionApiResponse
import com.manikandan.expensetracker.domain.repository.Repository
import com.manikandan.expensetracker.utils.NetworkResult

class UpdateTransactionUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(
        userId: String,
        singleTransaction: SingleTransaction
    ): NetworkResult<UserTransactionApiResponse> {
        return repository.updateUserTransaction(userId = userId, newTransaction = singleTransaction)
    }
}