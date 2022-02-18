package com.manikandan.expensetracker.domain.usecases.read_login_state

import com.manikandan.expensetracker.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadLoginStateUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Boolean>{
        return repository.readLoginState()
    }
}