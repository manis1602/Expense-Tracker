package com.manikandan.expensetracker.domain.usecases.read_on_boarding_state

import com.manikandan.expensetracker.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingStateUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Boolean>{
        return repository.readOnBoardingState()
    }
}