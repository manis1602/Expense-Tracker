package com.manikandan.expensetracker.domain.usecases.get_users_all_transactions

import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.domain.repository.Repository
import com.manikandan.expensetracker.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class GetUsersAllTransactionsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(userId: String): NetworkResult<Flow<List<SingleTransaction>>> {
        return repository.getUserAllTransaction(userId = userId)
    }
}