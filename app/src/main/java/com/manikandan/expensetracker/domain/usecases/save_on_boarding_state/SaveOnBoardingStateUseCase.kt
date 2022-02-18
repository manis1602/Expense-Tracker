package com.manikandan.expensetracker.domain.usecases.save_on_boarding_state

import com.manikandan.expensetracker.domain.repository.Repository

class SaveOnBoardingStateUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(completed: Boolean){
        repository.saveOnBoardingState(completed = completed)
    }
}