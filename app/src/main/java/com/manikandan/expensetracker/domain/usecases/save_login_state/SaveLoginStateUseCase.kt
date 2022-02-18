package com.manikandan.expensetracker.domain.usecases.save_login_state

import com.manikandan.expensetracker.domain.repository.Repository

class SaveLoginStateUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(isLoginCompleted: Boolean){
        repository.saveLoginState(isLoginCompleted = isLoginCompleted)
    }
}