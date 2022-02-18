package com.manikandan.expensetracker.domain.usecases.delete_transaction

import com.manikandan.expensetracker.domain.model.UserTransactionApiResponse
import com.manikandan.expensetracker.domain.repository.Repository
import com.manikandan.expensetracker.utils.NetworkResult

class DeleteTransactionUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(deleteTransactionDetail: Map<String, String>): NetworkResult<UserTransactionApiResponse> {
        return repository.deleteUserTransaction(deleteTransactionDetail = deleteTransactionDetail)
    }
}